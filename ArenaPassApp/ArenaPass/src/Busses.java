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

    private void setUpActions() {
        mainMenuDropDown.addActionListener(this::switchPanel);
        logoutBtn.addActionListener(this::logout);
        submitButton.addActionListener(this::submitButton);
    }

    private void switchPanel(ActionEvent actionEvent) {
        JOptionPane.showMessageDialog(mainMenuDropDown, mainMenuDropDown.getSelectedItem());
        switch (mainMenuDropDown.getSelectedIndex()) {
            case 0:
                setVisible(false);
                dispose();
                new MainPage().setVisible(true);
                break;
            case 1:
                setVisible(false);
                dispose();
                new BuyTicket().setVisible(true);
                break;
            case 2:
                // new BuySeasonTicket().setVisible(true);
                break;
            case 3:
                // cancel reservation
                break;
            case 4:
                // ticket history
                break;
            case 5:
                // Leaderboards
                break;
            case 6:
                setVisible(false);
                dispose();
                new Busses().setVisible(true);
                break;
            case 7:
                // Feedback
                break;
            case 8:
                // contact us
                break;
        }
    }

    private void logout(ActionEvent actionEvent) {
        setVisible(false);
        dispose();
        new LoginUI().setVisible(true);
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
        // Handle form submission logic
        JOptionPane.showMessageDialog(this, "Form submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        // You can add additional logic here to save the data or perform other actions
    }

}




