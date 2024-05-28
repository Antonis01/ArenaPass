import javax.swing.*;
import java.awt.event.ActionEvent;

public class Busses extends JFrame {
    private JPanel BussesForm;
    private JButton logoutBtn;
    private JComboBox mainMenuDropDown;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JComboBox comboBox1;
    private JButton submitButton;

    public Busses() {
        setupFrame();
        setUpActions();
    }

    private void setupFrame() {
        add(BussesForm);
        setTitle("Bus Application Form");
        setSize(1920, 1080);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void submitButton(ActionEvent actionEvent) {

        String field2 = textField2.getText();
        String field3 = textField3.getText();
        String field4 = textField4.getText();
        String comboBoxValue = (String) comboBox1.getSelectedItem();

        // Perform validation
        if ( field2.isEmpty() || field3.isEmpty() || field4.isEmpty() || comboBoxValue == null) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "Form submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void setUpActions() {
        GlobalMenus globalMenus = new GlobalMenus(this);
        mainMenuDropDown.addActionListener(globalMenus::switchPanel);
        logoutBtn.addActionListener(globalMenus::logout);
        submitButton.addActionListener(this::submitButton);
    }
}