import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;

public class seatSelect extends JFrame {

    private JPanel SelectTeamForm1;
    private JPanel selectTeamForm;
    private JComboBox mainMenuDropDown;
    private JLabel logo;
    private JTextField textField1;
    private JButton logoutBtn;
    private JButton checkoutButton;
    private JButton button1;
    private JLabel selectedSeat;
    private JLabel seatAvailability;
    private JLabel sectionNumber;
    private JPanel seatPanel;
    private  String section;
    private Match currMatch;
    private int seatNum;
    private int freeSeats;
    private int selectedSeatID;

    public seatSelect(Match currMatch, String section) {
        this.currMatch= currMatch;
        this.section=section;
        getTotalSeats();
        setUpActions();
        seatPanel.setLayout(new GridLayout(10, 10));
        createSeatButtons();
        setupFrame();
    }

    private void getTotalSeats() {
        try{
            Connection conn = ConnectDB.createConnection();
            String sql = "SELECT COUNT(*) FROM seats WHERE seat_section = ? AND seat_stadium_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, section);
            ps.setInt(2, currMatch.getStadiumID());
            ResultSet rs = ps.executeQuery();
            rs.next();
            this.seatNum=rs.getInt(1);
        }catch (SQLException e){
            e.printStackTrace();
        }

        this.freeSeats=seatNum;
    }

    private void reserveSeats(int[] seat) {
        int i;
        for(i=0;i<seatNum;i++)
            seat[i]=1;
        i=0;
        try {
            Connection conn = ConnectDB.createConnection();
            String sql = "SELECT reservations.reservation_seat_id from reservations,seats where reservations.reservation_type=1 AND reservations.reservation_match_id = ? AND seats.seat_section = ? AND reservations.reservation_seat_id = seats.seat_id;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, currMatch.getMatchID());
            ps.setString(2, section);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                seat[i]=0;
                i++;
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

    private void setupSeatID(int[] seat_id){
        int i=0;
        try {
            Connection conn = ConnectDB.createConnection();
            String sql = "SELECT reservations.reservation_seat_id from reservations,seats where reservations.reservation_match_id = ? AND seats.seat_section = ? AND reservations.reservation_seat_id = seats.seat_id;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, currMatch.getMatchID());
            ps.setString(2, section);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                seat_id[i]= rs.getInt(1);
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createSeatButtons() {
        //this.seatNum=getTotalSeats();
        int[] seatID = new int[seatNum];
        int[] isFree = new int[seatNum];
        JButton[] jButton = new JButton[seatNum];
        reserveSeats(isFree);
        setupSeatID(seatID);

        for(int i=0;i<seatNum;i++)
            System.out.println(seatID[i]);

        for (int i = 0; i < seatNum; i++) {
            jButton[i] = new JButton(Integer.toString(i + 1));
            jButton[i].setPreferredSize(new Dimension(50, 50));
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
                        selectedSeatID=seatID[finalI];
                        System.out.println(selectedSeatID);
                        selectedSeat.setText("Selected Seat: " + String.valueOf(selectedSeatID+1));
                        selectedSeat.setVisible(true);
                        checkoutButton.setVisible(true);
                    }
                }
            });
        }

    }

    private void setupFrame() {
        add(SelectTeamForm1);
        setTitle("Select Seat");
        setSize(1920, 1080);
        sectionNumber.setText("Section "+section);
        seatAvailability.setText("Available seats: "+freeSeats);
        selectedSeat.setVisible(false);
        checkoutButton.setVisible(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setUpActions() {
        GlobalMenus globalMenus = new GlobalMenus(this);
        mainMenuDropDown.addActionListener(globalMenus::switchPanel);
        logoutBtn.addActionListener(globalMenus::logout);
        checkoutButton.addActionListener(this::checkout);
    }

    private void checkout(ActionEvent actionEvent) {
        int fanID = LoginUI.getFanPassID();
        String query = "INSERT INTO tickets (ticket_seat_id,ticket_match_id,ticket_fan_pass_id) VALUES (?,?,?)";
        try {
            Connection connection = ConnectDB.createConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1,selectedSeatID);
            ps.setInt(2,currMatch.getMatchID());
            ps.setInt(3,LoginUI.getFanPassID());
            ps.executeUpdate();
            String temp2 = Integer.toString(fanID) + Integer.toString(getFanDataForQR());
            Process p = Runtime.getRuntime().exec("python src/qrcode_generator.py " + temp2);
            p.waitFor(); // wait for the process to finish
            setVisible(false);
            dispose();
            JOptionPane.showMessageDialog(null, "Transaction Completed");
            new viewTicketDetails().setVisible(true);
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }
}
