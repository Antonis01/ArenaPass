import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;


public class RegisterUI extends JFrame {
    private static final FileNameExtensionFilter IMAGE_FILE_FILTER = new FileNameExtensionFilter("Image files", "jpg", "png", "jpeg");

    private JPanel RegisterForm;
    private JLabel titleRegister;
    private JTextField inNameField;
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

    public RegisterUI() {
        setupFrame();
        setupActions();
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
        String name = inNameField.getText();
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
