import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;

public class AdminFanApplicationList extends JFrame {
    private JPanel thisForm;
    private JComboBox mainMenuDropDown;
    private JLabel logo;
    private JTextField textField1;
    private JButton logoutBtn;
    private JTable table;
    private JButton checkUserButton;

    public AdminFanApplicationList() throws SQLException {
        setupFrame();
        setUpActions();
        loadApplications();
    }

    void loadApplications() throws SQLException {
        String query = "SELECT fan_account_id, fan_username, fan_legal_name, fan_legal_surname FROM fans WHERE fan_account_status = 3";
        Connection connection = ConnectDB.createConnection();
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = null;
        PreparedStatement ps = connection.prepareStatement(query);
        rs=statement.executeQuery(query);

        DefaultTableModel model = new DefaultTableModel();
        clearTable(table);
        model.addColumn("Fan ID");
        model.addColumn("Username");
        model.addColumn("First Name");
        model.addColumn("Last Name");





        while(rs.next()){
            int id=rs.getInt(1);
            String username=rs.getString(2);
            String firstName=rs.getString(3);
            String lastName=rs.getString(4);

            model.addRow(new Object[]{id,username,firstName,lastName});
        }

        checkUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object value = table.getValueAt(table.getSelectedRow(),0);
                int selectedUserID=  Integer.parseInt(value.toString());
                System.out.println(selectedUserID);
                new FanIdApplicationPage(selectedUserID).setVisible(true);

                setVisible(false);
                dispose();
            }
        });

        table.setModel(model);

    }

    private static void clearTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
    }

    private void setupFrame() {
        add(thisForm);
        setTitle("Application List");
        setSize(1920, 1080);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setUpActions() {
        mainMenuDropDown.addActionListener(this::switchPanel);
        logoutBtn.addActionListener(this::logout);
    }

    private void switchPanel(ActionEvent actionEvent) {
        JOptionPane.showMessageDialog(mainMenuDropDown, mainMenuDropDown.getSelectedItem());

        switch (mainMenuDropDown.getSelectedIndex()) {
            case 0:
                setVisible(false);
                dispose();
                new AppAdminMainPage().setVisible(true);
                break;
            case 1:
                try {
                    new AdminFanApplicationList().setVisible(true);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                setVisible(false);
                dispose();
                break;
            case 2:
                setVisible(false);
                dispose();
                new BanUserChatRoom().setVisible(true);
                break;

                //new FanIdApplicationPage().setVisible(true);
        }
    }

    private void logout(ActionEvent actionEvent) {
        setVisible(false);
        dispose();
        new LoginUI().setVisible(true);
    }
}
