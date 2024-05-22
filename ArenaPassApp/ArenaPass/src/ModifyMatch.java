import javax.swing.*;
import java.awt.event.ActionEvent;



public class ModifyMatch extends JFrame{
    private JPanel ModifyMatch;
    private JComboBox AdminMenuDropDown;
    private JTextField textField1;
    private JButton logoutBtn;
    private JTextField textField2;
    private JButton DateBtn;
    private JTextField textField3;
    private JButton TimeBtn;
    private JTextField textField4;
    private JButton ScoreBtn;
    private JButton UpdateBtn;

    public ModifyMatch(){
        setupFrame();
        setUpActions();

    }

    private void setupFrame() {
        add(ModifyMatch);
        setTitle("ArenaPass ModifyMatchPage");
        setSize(1920, 1080);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void setUpActions() {
        AdminMenuDropDown.addActionListener(this::switchPanel);
        logoutBtn.addActionListener(this::logout);
    }

    private void switchPanel(ActionEvent actionEvent) {
        JOptionPane.showMessageDialog(AdminMenuDropDown, AdminMenuDropDown.getSelectedItem());

        switch (AdminMenuDropDown.getSelectedIndex()){
            case 0:
                setVisible(false);
                dispose();
                new AdminMainPage().setVisible(true);
                break;
            case 1:
                setVisible(false);
                dispose();
                new SelectMatchModify().setVisible(true);
                break;
            case 2:
                setVisible(false);
                dispose();
                new ModifyMatch().setVisible(true);
                break;

        }
    }

    private void logout(ActionEvent actionEvent) {
        setVisible(false);
        dispose();
        new LoginUI().setVisible(true);
    }

}


