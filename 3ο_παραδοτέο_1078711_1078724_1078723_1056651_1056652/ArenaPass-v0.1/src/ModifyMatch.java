import javax.swing.*;
import java.awt.event.ActionEvent;


public class ModifyMatch extends JFrame{
    private JPanel ModifyMatch;
    private JComboBox AdminMenuDropDown;
    private JTextField textField1;
    private JButton logoutBtn;
    private JTextField changeDate;
    private JButton DateBtn;
    private JTextField changeScore;
    private JButton TimeBtn;
    private JTextField changeTime;
    private JButton ScoreBtn;

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
                new FederationAdminMainPage().setVisible(true);
                break;
            case 1:
                setVisible(false);
                dispose();
                new SelectMatchModify().setVisible(true);
                break;
            case 2:
                setVisible(false);
                dispose();
                new ModifyTickets().setVisible(true);
                break;

        }
    }

    private void logout(ActionEvent actionEvent) {
        setVisible(false);
        dispose();
        new LoginUI().setVisible(true);
    }

}


