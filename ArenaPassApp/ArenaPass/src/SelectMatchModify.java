import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.ArrayList;

public class SelectMatchModify extends JFrame{
    private JPanel panel1;
    private JPanel SelectMatchModifyForm;
    private JComboBox FedAdminMenuDropDown;
    private JTextField textField1;
    private JButton logoutBtn;
    private JComboBox matchesComboBox;
    private JButton modifyButton;
    private ArrayList<Match> matches;
    private int matchCount;

    public SelectMatchModify(){
        setupFrame();
        setUpActions();
        this.matchCount=getRows();
        matches = new ArrayList<>(matchCount);
        setupMatchesBox();
    }

    private String getTeamName(int teamID) throws SQLException {
        String query = "SELECT team_name FROM teams WHERE team_id = ?";
        Connection connection = ConnectDB.createConnection();
        Statement statement = connection.createStatement();
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1,teamID);
        try{
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            return "FALSE ID";
        }
    }

    private void setupMatchesBox(){
        try {
            Connection connection = ConnectDB.createConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM matches where match_date>CURRENT_DATE()");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int matchID=rs.getInt(1);
                int stadiumID=rs.getInt(2);
                int homeTeamID=rs.getInt(3);
                int awayTeamID=rs.getInt(4);
                Date matchDate = rs.getDate(5);
                Time matchTime = rs.getTime(6);
                Match currMatch = new Match(matchID,getTeamName(homeTeamID),getTeamName(awayTeamID),stadiumID, matchDate, matchTime);
                matches.add(currMatch);
                matchesComboBox.addItem(currMatch.getHomeTeam()+" vs "+currMatch.getAwayTeam());

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setupFrame() {
        add(SelectMatchModifyForm);
        setTitle("ArenaPass SelectMatchModifyPage");
        setSize(1920, 1080);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private int getRows(){
        try {
            Connection connection = ConnectDB.createConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM matches where match_date>CURRENT_DATE()");
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setUpActions() {
        GlobalMenus globalMenus = new GlobalMenus(this);
        globalMenus.dropDownFedAdmin(FedAdminMenuDropDown);
        FedAdminMenuDropDown.addActionListener(this::switchPanel);
        logoutBtn.addActionListener(this::logout);
        modifyButton.addActionListener(this::modify);
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
    private void modify(ActionEvent actionEvent) {
        setVisible(false);
        dispose();
        new ModifyMatch(matches.get(matchesComboBox.getSelectedIndex())).setVisible(true);
    }
}





