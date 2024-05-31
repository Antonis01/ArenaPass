import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class seatSelect extends JFrame {

    private JPanel SelectSectionForm1;
    private JPanel selectTeamForm;
    private JComboBox mainMenuDropDown;
    private JLabel logo;
    private JTextField textField1;
    private JButton logoutBtn;
    private JButton checkoutButton;
    private JButton checkoutWithManualInputButton;
    private JLabel selectedSeat;
    private JLabel seatAvailability;
    private JLabel sectionNumber;
    private JPanel seatPanel;
    private JRadioButton multipleTickets;
    private  String section;
    private Match currMatch;
    private int seatNum;
    private int freeSeats;
    private int stadiumID;
    private int teamID;
    private boolean isSeason;
    private int previousSelected=-1;
    private int[] seatID;
    private int[] isFree;
    private String[] seatName;
    ArrayList<Integer> selectedID;

    public seatSelect(Match currMatch, String section) {
        this.isSeason=false;
        this.currMatch= currMatch;
        this.stadiumID=currMatch.getStadiumID();
        this.section=section;
        getTotalSeats();
        setUpActions();
        createSeatButtons();
        setupFrame();
    }

    public seatSelect(String team,String section,int stadiumID){
        this.isSeason=true;
        this.section=section;
        this.stadiumID=stadiumID;
        this.teamID=getTeamID(team);
        getTotalSeats();
        checkoutWithManualInputButton.setVisible(false);
        checkoutButton.setText("Checkout");
        multipleTickets.setVisible(false);
        seatPanel.setLayout(new GridLayout(10, 10));
        createSeatButtons();
        setupFrame();
        setUpActions();
    }

    private int getTeamID(String teamName){
        ResultSet rs = null;
        try {
            Connection connection = ConnectDB.createConnection();
            PreparedStatement ps =connection.prepareStatement("SELECT team_id FROM teams WHERE team_name=?");
            ps.setString(1,teamName);
            rs=ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getTotalSeats() {
        try{
            Connection conn = ConnectDB.createConnection();
            String sql = "SELECT COUNT(*) FROM seats WHERE seat_section = ? AND seat_stadium_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, section);
            ps.setInt(2, stadiumID);
            ResultSet rs = ps.executeQuery();
            rs.next();
            this.seatNum=rs.getInt(1);
        }catch (SQLException e){
            e.printStackTrace();
        }

        this.freeSeats=seatNum;
    }

    private void reserveSeats(int[] seat, int[] seat_id) {
        int i;
        for(i=0;i<seatNum;i++)
            seat[i]=1;
        i=0;
        try {
            Connection conn = ConnectDB.createConnection();
            String sql = "SELECT reservations.reservation_seat_id from reservations,seats where reservations.reservation_type!=4 AND reservations.reservation_match_id = ? AND seats.seat_section = ? AND reservations.reservation_seat_id = seats.seat_id;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, currMatch.getMatchID());
            ps.setString(2, section);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                seat[getSeatIndex(rs.getInt(1),seat_id)]=0;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private int getSeatIndex(int seatID,int[] seat){
        for(int i=0;i<seatNum;i++)
            if(seat[i]==seatID)
                return i;
        return -1;
    }

    private void reserveSeatsSeason(int[] seat,int[] seat_id) {
        int i;
        for(i=0;i<seatNum;i++)
            seat[i]=1;
        i=0;
        try {
            Connection conn = ConnectDB.createConnection();
            String sql = "select season_tickets.season_ticket_seat_id from season_tickets,seats where season_tickets.season_ticket_team_id = ? and seats.seat_section=? and season_tickets.season_ticket_seat_id=seats.seat_id";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, teamID);
            ps.setString(2, section);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                seat[getSeatIndex(rs.getInt(1),seat_id)]=0;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private int getFanDataForQR(){
        try {
            Connection conn = ConnectDB.createConnection();
            String sql = "SELECT reservation_id FROM reservations WHERE reservation_fan_pass_id = ? ORDER BY reservation_id DESC;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, Integer.toString(LoginUI.getFanPassID()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getInt(1));
                return rs.getInt(1);
            } else {
                System.out.println("No reservations found for this fan pass id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private void setupSeatID(int[] seat_id,String[] seatName){
        int i=0;
        try {
            Connection conn = ConnectDB.createConnection();
            String sql = "SELECT reservations.reservation_seat_id,seats.seat_number from reservations,seats where reservations.reservation_match_id = ? AND seats.seat_section = ? AND reservations.reservation_seat_id = seats.seat_id;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, currMatch.getMatchID());
            ps.setString(2, section);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                seat_id[i]= rs.getInt(1);
                seatName[i]=rs.getString(2);
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupSeatIDSeason(int[] seat_id, String[] seatName){
        int i=0;
        try {
            Connection conn = ConnectDB.createConnection();
            String sql = "select seat_id,seat_number FROM seats where seat_section=? and seat_stadium_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, section);
            ps.setInt(2, stadiumID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                seat_id[i]= rs.getInt(1);
                seatName[i]=rs.getString(2);
                i++;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void setupPanel(JPanel panel,String[] seatNames){
        char A;
        int diff;
        int rows;
        int columns;
        A=seatNames[seatNum-1].charAt(0);
        diff=A-'A';
        rows=diff+1;
        columns=seatNum/rows;
        panel.setLayout(new GridLayout(rows,columns));
    }

    private void createSeatButtons() {
        this.seatID = new int[seatNum];
        this.isFree = new int[seatNum];
        this.seatName= new String[seatNum];
        JButton[] jButton = new JButton[seatNum];
        selectedID = new ArrayList<Integer>(seatNum);

        if(isSeason) { setupSeatIDSeason(seatID,seatName); reserveSeatsSeason(isFree,seatID); }
        else { setupSeatID(seatID,seatName); reserveSeats(isFree,seatID); }
        setupPanel(seatPanel,seatName);
        for(int i=0;i<seatNum;i++)
            System.out.println(seatID[i]);

        for (int i = 0; i < seatNum; i++) {
            jButton[i] = new JButton(seatName[i]);
            jButton[i].setPreferredSize(new Dimension(80, 50));
            seatPanel.add(jButton[i]);
            if (isFree[i]==0) {
                this.freeSeats--;
                jButton[i].setBackground(Color.RED);
            } else
                jButton[i].setBackground(Color.GREEN);

            jButton[i].setForeground(Color.BLACK);
            jButton[i].setOpaque(true);

            int finalI = i;
            jButton[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (isFree[finalI]==0)
                        JOptionPane.showMessageDialog(null, "Seat Taken");
                    else {
                        if(!selectedID.contains(seatID[finalI])){
                            jButton[finalI].setBackground(Color.CYAN);
                            if(!multipleTickets.isSelected()) {
                                if (previousSelected != -1){
                                    jButton[previousSelected].setBackground(Color.GREEN);
                                    selectedID.remove(Integer.valueOf(seatID[previousSelected]));
                                }
                                previousSelected = finalI;
                            }
                            else
                                jButton[finalI].setBackground(Color.CYAN);
                            selectedID.add(seatID[finalI]);
                        }
                        else{
                            jButton[finalI].setBackground(Color.GREEN);
                            selectedID.remove(Integer.valueOf(seatID[finalI]));
                        }
                    }
                }
            });

        }
    }


    private void setupFrame() {
        add(SelectSectionForm1);
        setTitle("Select Seat");
        setSize(1920, 1080);
        sectionNumber.setText("Section "+section);
        seatAvailability.setText("Available seats: "+freeSeats);
        selectedSeat.setVisible(false);
        //checkoutButton.setVisible(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setUpActions() {
        GlobalMenus globalMenus = new GlobalMenus(this);
        globalMenus.dropDownFan(mainMenuDropDown);
        globalMenus.switchPanel(mainMenuDropDown);
        logoutBtn.addActionListener(globalMenus::logout);
        checkoutButton.addActionListener(this::checkout);
        checkoutWithManualInputButton.addActionListener(this::checkoutManualInput);
    }

    private void checkout(ActionEvent actionEvent) {
        int fanID = LoginUI.getFanPassID();
        String query = null;
        PreparedStatement ps = null;
        Object[] options = {"Accept", "Decline"};
        int n = JOptionPane.showOptionDialog(null,
                "Accept terms and conditions to proceed",
                "Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,     //do not use a custom Icon
                options,  //the titles of buttons
                options[0]); //default button title

        if (n == JOptionPane.YES_OPTION) {
            if (selectedID.size() == 1) {
                try {
                    Connection connection = ConnectDB.createConnection();
                    if (!isSeason) {
                        query = "INSERT INTO tickets (ticket_seat_id,ticket_match_id,ticket_fan_pass_id) VALUES (?,?,?)";
                        ps = connection.prepareStatement(query);
                        ps.setInt(1, selectedID.get(0));
                        ps.setInt(2, currMatch.getMatchID());
                        ps.setInt(3, fanID);
                        ps.executeUpdate();
                        String temp2 = Integer.toString(fanID) + Integer.toString(getFanDataForQR());
                        Process p = Runtime.getRuntime().exec("python3 src/qrcode_generator.py " + temp2);
                        p.waitFor(); // wait for the process to finish

                        String outp = "ticket.pdf";
                        String fdqr = Integer.toString(getFanDataForQR());
                        String fname = LoginUI.currFan.getFirstName();
                        String lname = LoginUI.currFan.getLastName();
                        String id = Integer.toString(LoginUI.currFan.getID());
                        String mid = Integer.toString(currMatch.getMatchID());


                        String temp3 ="\"" + outp + "\" \"" + fdqr + "\" \"" + fname + "\" \"" + lname + "\" \"" + id + "\" \"" + mid + "\"";
                        System.out.println(temp3);
                        Process ticket = Runtime.getRuntime().exec("python3 src/print_pdf.py " + temp3);
                        ticket.waitFor();

                        setVisible(false);
                        dispose();
                        JOptionPane.showMessageDialog(null, "Transaction Completed");
                        new viewTicketDetails().setVisible(true);
                    } else {
                        query = "insert into season_tickets (season_ticket_seat_id,season_ticket_team_id,season_ticket_stadium_id,season_ticket_fan_pass_id) VALUES (?,?,?,?)";
                        ps = connection.prepareStatement(query);
                        ps.setInt(1, selectedID.get(0));
                        ps.setInt(2, teamID);
                        ps.setInt(3, stadiumID);
                        ps.setInt(4, fanID);
                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Transaction Completed");
                    }
                } catch (Exception ee) {
                    JOptionPane.showMessageDialog(null, ee.getMessage());
                }

            }
            else{
                JOptionPane.showMessageDialog(null,"Cannot autofill with multiple tickets");
            }
        } else if (n == JOptionPane.NO_OPTION) {
           setVisible(false);
           dispose();
           new MainPage().setVisible(true);
        }
    }

    private void checkoutManualInput(ActionEvent actionEvent){
        for(int i=0;i<selectedID.size();i++)
        {
            new BuyTicketCheckoutManual(seatName[i],selectedID.get(i),currMatch);
        }
        this.dispose();
        this.setVisible(false);
    }
}
