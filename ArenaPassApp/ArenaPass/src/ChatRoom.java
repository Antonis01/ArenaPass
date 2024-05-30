import javax.swing.*;
import java.awt.event.ActionEvent;

public class ChatRoom extends JFrame {
    private JPanel ChatRoomForm;
    private JComboBox mainMenuDropDown;
    private JTextField textField1;
    private JButton logoutBtn;
    private JTextField textField2;
    private JTextField textField3;
    private JButton submitBtn;

    public ChatRoom() {
        setupFrame();
        setUpActions();
    }

    private void setupFrame() {
        add(ChatRoomForm);
        setTitle("ArenaPass ChatRoom");
        setSize(1920, 1080);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setUpActions() {
        GlobalMenus globalMenus = new GlobalMenus(this);
        globalMenus.dropDownFan(mainMenuDropDown);
        globalMenus.switchPanel(mainMenuDropDown);
        logoutBtn.addActionListener(globalMenus::logout);
        submitBtn.addActionListener(this::submitForm);
    }

    private void submitForm(ActionEvent actionEvent){
        JOptionPane.showMessageDialog(submitBtn,"successfully submit");
        setVisible(false);
        dispose();
        new MainPage().setVisible(true);
    }
}