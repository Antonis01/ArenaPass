import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class LoginUI extends JFrame{
    private JTextField UsernameField;
    private JPasswordField PasswordField;
    private JButton LoginButton;
    private JButton RegisterButton;
    private JButton ForgotPasswordButton;
    public JPanel LoginForm;

    public LoginUI() {
        LoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                if (ConnectDB.Connector(UsernameField.getText(), new String(PasswordField.getPassword()))) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    System.out.println("Login successful!");
                } else {
                    JOptionPane.showMessageDialog(null, "Login failed!");
                }


            }
        });
    }
}