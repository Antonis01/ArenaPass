import javax.swing.*;

public class AdminBussesForm extends JFrame{
    private JPanel thisPanel;
    private JComboBox mainMenuDropDown;
    private JLabel logo;
    private JTextField textField1;
    private JButton logoutBtn;
    private JTable table1;
    private JButton button1;

    public AdminBussesForm() {
        setupFrame();
    }

    private void setupFrame() {
       // add(AdminBussesForm);
        setTitle("Busses Forms");
        setSize(1920, 1080);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


}
