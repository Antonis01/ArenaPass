import javax.swing.*;
import java.awt.event.ActionEvent;

public class AppAdminMainPage extends JFrame {
    private JPanel AppAdminMainPageForm;
    private JComboBox AppAdminMenuDropDown;
    private JTextField textField1;
    private JButton logoutBtn;

public AppAdminMainPage() {
    setupFrame();
    setUpActions();
}

private void setupFrame() {
    add(AppAdminMainPageForm);
    setTitle("ArenaPass AppAdminMainPage");
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

