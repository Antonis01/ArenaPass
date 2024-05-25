import com.sun.jdi.connect.Connector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel LoginForm;
    private JButton registerButton;
    private Fan currFan;

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
            ResultSet rs = null;
            try {
                Connection connection = ConnectDB.createConnection();
                PreparedStatement ps = connection.prepareStatement("select fan_account_id,fan_username,fan_legal_name,fan_legal_surname,fan_phone,fan_address,fan_city,fan_citizen_id_number,fan_email, fan_citizen_id_dob,fan_account_status from fans WHERE fan_username=?");
                ps.setString(1,usernameField.getText());
                rs=ps.executeQuery();
                int status=1;

                while(rs.next()){
                    switch(rs.getString(11)) {
                        case "VERIFIED":
                            status=1;
                            break;
                        case "BANNED":
                            status=2;
                            break;
                        case "PENDING":
                            status=3;
                            break;
                        default:
                            JOptionPane.showMessageDialog(null,"SOMETHING WENT WRONG");

                    }
                    currFan=new Fan(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getInt(8),rs.getString(9),rs.getDate(10),status);
                    System.out.println("FAN ADDED");
                }

                openMainPage();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Login failed!");
        }
    }

    private void openRegisterUI(ActionEvent actionEvent) {
        LoginForm.setVisible(false);
        dispose();
        new RegisterUI().setVisible(true);
    }

    public int getFanPassID(){
        return currFan.getFanPassID();
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
