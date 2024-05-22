import javax.swing.*;
import java.awt.event.ActionEvent;

public class AdminMainPage extends JFrame {
    private JPanel AdminMainPageForm;
    private JTextField textField1;
    private JButton logoutBtn;
    private JComboBox AdminMenuDropDown;


    public AdminMainPage() {
        setupFrame();
        setUpActions();
    }

    private void setupFrame() {
        add(AdminMainPageForm);
        setTitle("ArenaPass MainPage");
        setSize(1920, 1080);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setUpActions() {
        AdminMenuDropDown.addActionListener(this::switchPanel);
        logoutBtn.addActionListener(this::logout);
    }

    private void switchPanel(ActionEvent actionEvent) {
        JOptionPane.showMessageDialog(AdminMenuDropDown, AdminMenuDropDown.getSelectedItem());

        switch (AdminMenuDropDown.getSelectedIndex()){
            case 0:
                setVisible(false);
                dispose();
                new AdminMainPage().setVisible(true);
                break;
            case 1:
                setVisible(false);
                dispose();
                new SelectMatchModify().setVisible(true);
                break;
            case 2:
                setVisible(false);
                dispose();
                new ModifyMatch().setVisible(true);
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
                setVisible(false);
                dispose();
                new Busses().setVisible(true);
                break;
            case 7:
                // Feedback
                setVisible(false);
                dispose();
                new FeedBack().setVisible(true);
                break;
            case 8:
                // contact us
                break;
            case 9:
                // Chatroom
                setVisible(false);
                dispose();
                new ChatRoom().setVisible(true);
                break;
        }
    }

    private void logout(ActionEvent actionEvent) {
        setVisible(false);
        dispose();
        new LoginUI().setVisible(true);
    }

}
