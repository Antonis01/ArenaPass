import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
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

    private void submitForm(ActionEvent actionEvent){
        String query="";
        try {
            Connection connection = ConnectDB.createConnectionFeedback();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,categoryField.getText());
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

