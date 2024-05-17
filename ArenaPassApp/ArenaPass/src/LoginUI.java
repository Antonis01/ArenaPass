import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI extends JFrame{
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    JPanel LoginForm;

    public LoginUI()   {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (ConnectDB.Connector(usernameField.getText(), new String(passwordField.getPassword()))) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    System.out.println("Login successful!");
                } else {
                    JOptionPane.showMessageDialog(null, "Login failed!");
                }
            }
        });
    }
}
