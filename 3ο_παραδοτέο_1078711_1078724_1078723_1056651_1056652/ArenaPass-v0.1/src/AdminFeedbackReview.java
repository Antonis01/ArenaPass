import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.*;

public class AdminFeedbackReview extends JFrame{
    private JComboBox AppAdminMenuDropDown;
    private JTextField textField1;
    private JButton logoutBtn;
    private JPanel contentPanel;
    private JTable table;
    private JButton resolveBttn;
    private JPanel AdminFeedbackReviewForm;
    private JButton fullDescriptionButton;

    public AdminFeedbackReview() {
        setupFrame();
        setUpActions();
        try {
            loadApplications();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        table.addMouseListener(new MouseAdapter() {
        });
    }

    void loadApplications() throws SQLException {
        String query = "SELECT * FROM feedback_forms WHERE feedback_status=1";
        Connection connection = ConnectDB.createConnectionFeedback();
        ResultSet rs = null;
        PreparedStatement ps = connection.prepareStatement(query);
        rs=ps.executeQuery();

        DefaultTableModel model = new DefaultTableModel();
        clearTable(table);

        model.addColumn("ID");
        model.addColumn("Category");
        model.addColumn("Description");
        model.addColumn("Status");

        while(rs.next()){
            int feedbackID=rs.getInt(1);
            String feedbackCategory=rs.getString(2);
            String feedbackBody=rs.getString(3);
            String feedbackStatus=rs.getString(4);

            model.addRow(new Object[]{feedbackID,feedbackCategory,feedbackBody,feedbackStatus});
        }

        resolveBttn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object value = table.getValueAt(table.getSelectedRow(),0);

                try {
                    PreparedStatement ps = connection.prepareStatement("UPDATE feedback_forms SET feedback_status=2 WHERE feedback_id=?");
                    ps.setInt(1, Integer.parseInt(value.toString()));
                    ps.executeUpdate();
                    loadApplications();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        fullDescriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object value = table.getValueAt(table.getSelectedRow(),2);
                JOptionPane.showMessageDialog(null, value.toString());
            }
        });

        table.setModel(model);

    }

    private static void clearTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
    }


    private void setupFrame() {
        add(AdminFeedbackReviewForm);
        setTitle("Feedback Reviews");
        setSize(1920, 1080);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void setUpActions() {
        AppAdminMenuDropDown.addActionListener(this::switchPanel);
        logoutBtn.addActionListener(this::logout);
        //resolveBttn.addActionListener(this::resolveAction);
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
            case 2:
                setVisible(false);
                dispose();
                new BanUserChatRoom().setVisible(true);
                break;

        }
    }

    private void logout(ActionEvent actionEvent) {
        setVisible(false);
        dispose();
        new LoginUI().setVisible(true);
    }
}
