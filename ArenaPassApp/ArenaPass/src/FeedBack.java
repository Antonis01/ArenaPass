import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FeedBack extends JFrame {
    private JPanel FeedBackForm;
    private JComboBox mainMenuDropDown;
    private JTextField textField1;
    private JButton logoutBtn;
    private JTextField categoryField;
    private JTextField descriptionField;
    private JButton submitBtn;
    private JComboBox categoryBox;


    public FeedBack() {
        setupFrame();
        setUpActions();
    }
    private void setupFrame() {
        add(FeedBackForm);
        setTitle("ArenaPass FeedBack");
        setSize(1920, 1080);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void setUpActions() {
        GlobalMenus globalMenus = new GlobalMenus(this);
        mainMenuDropDown.addActionListener(globalMenus::switchPanel);
        logoutBtn.addActionListener(globalMenus::logout);
        submitBtn.addActionListener(this::submitForm);
    }

    int getCategoryState(){
        switch(categoryBox.getSelectedItem().toString()){
            case "BUG":
                return 1;
            case "TYPO ERROR":
                return 2;
            case "OTHER":
                return 3;
        }
        return 1;
    }

    private void submitForm(ActionEvent actionEvent){
        String query="INSERT INTO feedback_forms (feedback_category,feedback_body) VALUES (?,?)";
        int categoryState = getCategoryState();
        try {
            Connection connection = ConnectDB.createConnectionFeedback();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, categoryState);
            ps.setString(2,descriptionField.getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null,"Successfully Submitted");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        setVisible(false);
        dispose();
        new MainPage().setVisible(true);
    }
}

