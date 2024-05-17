import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginUI extends JFrame{
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    JPanel LoginForm;
    private JButton registerButton;

    public LoginUI()   {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (ConnectDB.Connector(usernameField.getText(), new String(passwordField.getPassword()))) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    LoginForm.setVisible(false);
                    dispose();
                    System.out.println("Login successful!");
                    MainPage mp = new MainPage();
                    mp.setContentPane(mp.MainPageForm);
                    mp.setTitle("ArenaPass Login");
                    mp.setSize(1920, 1080);
                    mp.setVisible(true);
                    mp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } else {
                    JOptionPane.showMessageDialog(null, "Login failed!");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                LoginForm.setVisible(false);
                dispose();
                new RegisterUI().setVisible(true);


            }
        });
    }
}
