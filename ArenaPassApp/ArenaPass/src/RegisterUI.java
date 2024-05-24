import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.*;


public class RegisterUI extends JFrame {
    private static final FileNameExtensionFilter IMAGE_FILE_FILTER = new FileNameExtensionFilter("Image files", "jpg", "png", "jpeg");

    private JPanel RegisterForm;
    private JLabel titleRegister;
    private JTextField inFirstNameField;
    private JTextField inEmailField;
    private JButton uploadIDFrontBtn;
    private JTextField inBirthdate;
    private JTextField inPhoneNumber;
    private JTextField inHomeAddress;
    private JButton uploadIDBackBtn;
    private JTextField inCity;
    private JTextField inCitizenID;
    private JButton uploadPhotoBtn;
    private JButton registerBtn;
    private JPasswordField inPasswordField;
    private JTextField inLastNameField;
    private JTextField inUsernameField;
    private JTextField inIDExpDate;

    public RegisterUI() {
        setupFrame();
        setupActions();

        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isNotEmpty()) {
                    try {
                        String query = "INSERT INTO fans (`fan_username`, `fan_password`, `fan_legal_name`, `fan_legal_surname`, `fan_citizen_id_number`, `fan_citizen_id_expiration_date`, `fan_citizen_id_dob`, `fan_account_status`, `fan_phone`, `fan_email`) VALUES ( ?, ?, ?, ?, ?, ?, ?,3, ?, ?)";
                        Connection connection = ConnectDB.createConnection();
                        Statement statement = connection.createStatement();
                        PreparedStatement ps = connection.prepareStatement(query);
                        ps.setString(1, inUsernameField.getText());
                        ps.executeUpdate();

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }

    private Boolean isNotEmpty(){
        if(inFirstNameField.getText() == null) return false;
        if(inLastNameField.getText() == null) return false;
        if(inEmailField.getText() == null) return false;
        if(inBirthdate.getText() == null) return false;
        if(inPhoneNumber.getText() == null) return false;
        if(inHomeAddress.getText() == null) return false;
        if(inCity.getText() == null) return false;
        if(inCitizenID.getText() == null) return false;
        if(inUsernameField.getText() == null) return false;
        if(inPasswordField.getText() == null) return false;

        return true;
    }

    private void setupFrame() {
        add(RegisterForm);
        setTitle("ArenaPass Register");
        setSize(1920, 1080);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setupActions() {
        uploadIDFrontBtn.addActionListener(this::uploadFileAction);
        uploadIDBackBtn.addActionListener(this::uploadFileAction);
        uploadPhotoBtn.addActionListener(this::uploadFileAction);
        registerBtn.addActionListener(this::register);
    }

    private void uploadFileAction(ActionEvent actionEvent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(IMAGE_FILE_FILTER);

        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println(selectedFile.getName());
        }
    }

    private void register(ActionEvent actionEvent) {
        String name = inFirstNameField.getText();
        String email = inEmailField.getText();
        String birthdate = inBirthdate.getText();
        String phoneNumber = inPhoneNumber.getText();
        String homeAddress = inHomeAddress.getText();
        String city = inCity.getText();
        String citizenID = inCitizenID.getText();
        String password = new String(inPasswordField.getPassword());

        // insert data to the database

        JOptionPane.showMessageDialog(null, "Register successful!");
        RegisterForm.setVisible(false);
        dispose();
        new MainPage().setVisible(true);
    }
}
