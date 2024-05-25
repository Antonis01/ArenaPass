import com.mysql.cj.exceptions.ConnectionIsClosedException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

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

    /*public static void setSection(String text)
    {
        section = text;

    }*/

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

    private void createSeatButtons() {
        //this.seatNum=getTotalSeats();
        JButton[] jButton = new JButton[seatNum];
        int[] isFree = new int[seatNum];
        reserveSeats(isFree);

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
                        selectedSeat.setText("Selected Seat: " + String.valueOf(finalI + 1));
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
        mainMenuDropDown.addActionListener(this::switchPanel);
        logoutBtn.addActionListener(this::logout);
        checkoutButton.addActionListener(this::checkout);
    }

    private void checkout(ActionEvent actionEvent) {
        JOptionPane.showMessageDialog(null, "Transaction Completed");
        //new seatSelect().setVisible(false);
        //new MainPage().setVisible(true);
        try {
            Connection conn = ConnectDB.createConnection();
            String sql = "INSERT INTO reservations (reservation_type, reservation_match_id, reservation_seat_id, reservation_user_id) VALUES (1, ?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, currMatch.getMatchID());
            ps.setInt(2, Integer.parseInt(selectedSeat.getText().substring(15)));
            ps.setInt(3, LoginUI.getUserID());
            ps.executeUpdate();

            Process p = Runtime.getRuntime().exec("python3 src/qrcode_generator.py");
            p.waitFor(); // wait for the process to finish
            setVisible(false);
            dispose();
            new viewTicketDetails().setVisible(true);
        } catch (Exception ee) {
            ee.printStackTrace();
        }

    }

    private void switchPanel(ActionEvent actionEvent) {
        JOptionPane.showMessageDialog(mainMenuDropDown, mainMenuDropDown.getSelectedItem());

        switch (mainMenuDropDown.getSelectedIndex()) {
            case 0:
                setVisible(false);
                dispose();
                new MainPage().setVisible(true);
                break;
            case 1:
                setVisible(false);
                dispose();
                try {
                    new BuyTicket().setVisible(true);
                }catch (SQLException sqle){
                    sqle.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 2:
                // new BuySeasonTicket().setVisible(true);
                break;
            case 3:
                // cancel reservation
                break;
            case 4:
                // ticket history
                break;
            case 5:
                // Leaderboards
                break;
            case 6:
                // Busses
                break;
            case 7:
                // Feedback
                break;
            case 8:
                // contact us
                break;
        }
    }

    private void logout(ActionEvent actionEvent) {
        setVisible(false);
        dispose();
        new LoginUI().setVisible(true);
    }
}
