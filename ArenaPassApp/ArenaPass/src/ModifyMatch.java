import javax.swing.*;


public class ModifyMatch extends JFrame{
    private JPanel ModifyMatch;
    private JComboBox AdminMenuDropBox;
    private JTextField textField1;
    private JButton logoutBtn;
    private JTextField textField2;
    private JButton DateBtn;
    private JTextField textField3;
    private JButton TimeBtn;
    private JTextField textField4;
    private JButton ScoreBtn;
    private JButton AwayStantBtn;
    private JButton HomeStantBtn;
    private JButton UpdateBtn;

    public ModifyMatch(){

        setupFrame();
    }

    private void setupFrame() {
        add(ModifyMatch);
        setTitle("ArenaPass ModifyMatchPage");
        setSize(1920, 1080);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
