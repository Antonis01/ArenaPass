import javax.swing.*;
import java.awt.event.ActionEvent;

public class LoginUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel LoginForm;
    private JButton registerButton;

    public LoginUI() {
        setupFrame();
        setUpActions();
        setEnterKeyAction();
    }

    private void setupFrame() {
        setContentPane(LoginForm);
        setTitle("ArenaPass Login");
        setSize(1920, 1080);
        setVisible(true);
    }

    private void setUpActions() {
        loginButton.addActionListener(this::login);
        registerButton.addActionListener(this::openRegisterUI);
    }

    private void setEnterKeyAction() {
        getRootPane().setDefaultButton(loginButton);
    }

    private void login(ActionEvent actionEvent) {
        if (ConnectDB.Connector(usernameField.getText(), new String(passwordField.getPassword()))) {
            openMainPage();
        } else {
            JOptionPane.showMessageDialog(null, "Login failed!");
        }
    }

    private void openRegisterUI(ActionEvent actionEvent) {
        LoginForm.setVisible(false);
        dispose();
        new RegisterUI().setVisible(true);
    }

    private void openMainPage() {
        LoginForm.setVisible(false);
        dispose();
        new MainPage().setVisible(true);
    }

    private void openAdminPage() {
        LoginForm.setVisible(false);
        dispose();
        new FederationAdminMainPage().setVisible(true);
    }

    private void openAppAdminPage(){
        LoginForm.setVisible(false);
        dispose();
        new AppAdminMainPage().setVisible(true);
    }
}
