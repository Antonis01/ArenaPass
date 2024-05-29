import javax.swing.*;

public class MainPage extends JFrame {
    private JButton showFanIDButton;
    private JButton buySeasonTicketButton;
    private JButton buyTicketButton;
    private JPanel MainPageForm;
    private JComboBox mainMenuDropDown;
    private JTextField textField1;
    private JButton logoutBtn;
    private JLabel imageLabel;

    public MainPage() {
        setupFrame();
        setUpActions();
    }

    private void setupFrame() {
        //GlobalMenus.dropDownFan(mainMenuDropDown);
        add(MainPageForm);
        setTitle("ArenaPass MainPage");
        setSize(1920, 1080);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setUpActions() {
        GlobalMenus globalMenus = new GlobalMenus(this);
        mainMenuDropDown.addActionListener(globalMenus::switchPanel);
        logoutBtn.addActionListener(globalMenus::logout);
    }
}