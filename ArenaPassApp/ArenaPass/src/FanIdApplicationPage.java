import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class FanIdApplicationPage extends JFrame {
    private JPanel FanIdApplicationPage;
    private JComboBox AppAdminMenuDropDown;
    private JButton logoutBtn;
    private JTextField textField1;
    private JButton approveBttn;
    private JButton rejectBttn;
    private JLabel appName;
    private JLabel appDOB;
    private JLabel appEmail;
    private JLabel appCitID;

    public FanIdApplicationPage(int userID) {
        try {
            getInfo(userID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        approveBttn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = "UPDATE fans SET fan_account_status = 1 WHERE fan_account_id = ? ";
                Connection connection = null;
                try {
                    connection = ConnectDB.createConnection();
                    Statement statement = connection.createStatement();
                    ResultSet rs = null;
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1, userID);
                    ps.executeUpdate();

                    new AdminFanApplicationList().setVisible(true);
                    setVisible(false);
                    dispose();
                } catch (SQLException exception) {
                    throw new RuntimeException(exception);
                }
            }
        });

        rejectBttn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = "UPDATE fans SET fan_account_status = 2 WHERE fan_account_id = ? ";
                Connection connection = null;
                try {
                    connection = ConnectDB.createConnection();
                    Statement statement = connection.createStatement();
                    ResultSet rs = null;
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1, userID);
                    ps.executeUpdate();

                    new AdminFanApplicationList().setVisible(true);
                    setVisible(false);
                    dispose();
                } catch (SQLException exception) {
                    throw new RuntimeException(exception);
                }
            }
        });

        setupFrame();
        setUpActions();



    }

    private void getInfo(int userID) throws SQLException {
        String query = "SELECT fan_legal_name, fan_legal_surname, fan_citizen_id_dob, fan_email, fan_citizen_id_number FROM fans WHERE fan_account_id = ?";
        Connection connection = ConnectDB.createConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = null;
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, userID);
        rs=ps.executeQuery();


        while(rs.next()){
            appName.setText(rs.getString(1)+" "+rs.getString(2));
            appDOB.setText(rs.getDate(3).toString());
            appEmail.setText(rs.getString(4));
            appCitID.setText(String.valueOf(rs.getInt(5)));
        }
    }

    private void setupFrame() {
        add(FanIdApplicationPage);
        setTitle("ArenaPass FanIdApplicationPage");
        setSize(1920, 1080);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setUpActions() {
        AppAdminMenuDropDown.addActionListener(this::switchPanel);
        logoutBtn.addActionListener(this::logout);
    }

    private void switchPanel(ActionEvent actionEvent) {
        JOptionPane.showMessageDialog(AppAdminMenuDropDown, AppAdminMenuDropDown.getSelectedItem());

        switch (AppAdminMenuDropDown.getSelectedIndex()) {
            case 0:
                setVisible(false);
                dispose();
                new AppAdminMainPage().setVisible(true);
                break;
            case 1:
                setVisible(false);
                dispose();
                try {
                    new AdminFanApplicationList().setVisible(true);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }

    private void logout(ActionEvent actionEvent) {
        setVisible(false);
        dispose();
        new LoginUI().setVisible(true);
    }

}


