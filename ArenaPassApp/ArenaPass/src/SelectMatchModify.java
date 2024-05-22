import javax.swing.*;

public class SelectMatchModify extends JFrame{
    private JPanel panel1;
    private JPanel SelectMatch;
    private JComboBox AdminMenuDropBox;
    private JTextField textField1;
    private JButton logoutBtn;
    private JButton ModifyBtn;


    public SelectMatchModify(){

        setupFrame();
    }

    private void setupFrame() {
        add(SelectMatch);
        setTitle("ArenaPass ModifyMatchPage");
        setSize(1920, 1080);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


}
