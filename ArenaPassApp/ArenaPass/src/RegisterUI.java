import javax.swing.*;


public class RegisterUI extends JFrame {
    private JPanel RegisterForm;
    private JLabel titleRegister;
    private JTextField inNameField;
    private JTextField inEmailField;
    private JTextField inPasswordField;
    private JButton UPLOADButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton UPLOADButton1;
    private JTextField textField4;
    private JTextField textField5;
    private JButton UPLOADButton2;
    private JButton SIGNUPButton;

    public RegisterUI() {
        add(RegisterForm);
        setTitle("ArenaPass Register");
        setSize(1920, 1080);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}