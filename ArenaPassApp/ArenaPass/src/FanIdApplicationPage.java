import javax.swing.*;
import java.awt.event.ActionEvent;

public class FanIdApplicationPage extends JFrame {
    private JPanel FanIdApplicationPage;
    private JComboBox AppAdminMenuDropDown;
    private JButton logoutBtn;
    private JTextField textField1;
    private JButton APPROVEBtn;
    private JButton REJECTBtn;

    public FanIdApplicationPage() {
        setupFrame();
        setUpActions();
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
                new FanIdApplicationPage().setVisible(true);
                break;
        }
    }

    private void logout(ActionEvent actionEvent) {
        setVisible(false);
        dispose();
        new LoginUI().setVisible(true);
    }

}


