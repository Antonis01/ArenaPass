import javax.swing.*;
import java.awt.event.ActionEvent;


public class ModifyMatch extends JFrame{
    private JPanel ModifyMatch;
    private JComboBox FedAdminMenuDropDown;
    private JTextField textField1;
    private JButton logoutBtn;
    private JTextField changeDate;
    private JButton DateBtn;
    private JTextField changeScore;
    private JButton TimeBtn;
    private JTextField changeTime;
    private JButton ScoreBtn;
    private JLabel awayTeamLabel;
    private JLabel homeTeamLabel;
    private Match match;


    public ModifyMatch(Match match){
        this.match=match;
        setupFrame();
        setUpActions();

    }

    private void setupFrame() {
        add(ModifyMatch);
        setTitle("ArenaPass ModifyMatchPage");
        homeTeamLabel.setText(match.getHomeTeam());
        awayTeamLabel.setText(match.getAwayTeam());
        setSize(1920, 1080);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void setUpActions() {
        GlobalMenus globalMenus = new GlobalMenus(this);
        globalMenus.dropDownFedAdmin(FedAdminMenuDropDown);
        FedAdminMenuDropDown.addActionListener(this::switchPanel);
        logoutBtn.addActionListener(this::logout);
    }

    private void switchPanel(ActionEvent actionEvent) {
        JOptionPane.showMessageDialog(FedAdminMenuDropDown, FedAdminMenuDropDown.getSelectedItem());

        switch (FedAdminMenuDropDown.getSelectedIndex()){
            case 0:
                setVisible(false);
                dispose();
                new FederationAdminMainPage().setVisible(true);
                break;
            case 1:
                setVisible(false);
                dispose();
                new FederationAddNewMatch().setVisible(true);
                break;
            case 2:
                setVisible(false);
                dispose();
                new SelectMatchModify().setVisible(true);
                break;
            case 3:
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


