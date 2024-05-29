import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Busses extends JFrame {
    private JPanel BussesForm;
    private JButton logoutBtn;
    private JComboBox mainMenuDropDown;
    private JTextField textField1;
    private JTextField nameTextField;
    private JTextField phoneTextField;
    private JTextField seatNumTextField;
    private JComboBox viewTeamsComboBox;
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

        String field1 = nameTextField.getText();
        String field2 = phoneTextField.getText();
        String field3 = seatNumTextField.getText();
        String comboBoxValue = (String) viewTeamsComboBox.getSelectedItem();

        // Perform validation
        if ( field1.isEmpty() || field2.isEmpty() || field3.isEmpty() || comboBoxValue == null) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }else{
            try{
                Connection conn = ConnectDB.createConnectionBusses();
                String query = "INSERT INTO busses_res (name, phone, seat_num, team) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, field1);
                ps.setString(2, field2);
                ps.setString(3, field3);
                ps.setString(4, comboBoxValue);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Form submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }catch (SQLException e){
                System.err.println("Error: " + e);
            }

        }
    }

    private void showTeamList(JComboBox addTeams) {
        try {
            Connection conn = ConnectDB.createConnection();
            String query = "SELECT team_name FROM teams";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            addTeams.removeAllItems();
            while (rs.next()) {
                addTeams.addItem(rs.getString("team_name"));
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private void setUpActions() {
        GlobalMenus globalMenus = new GlobalMenus(this);
        globalMenus.dropDownFan(mainMenuDropDown);
        mainMenuDropDown.addActionListener(globalMenus::switchPanel);
        logoutBtn.addActionListener(globalMenus::logout);
        submitButton.addActionListener(this::submitButton);
        showTeamList(viewTeamsComboBox);
    }
}