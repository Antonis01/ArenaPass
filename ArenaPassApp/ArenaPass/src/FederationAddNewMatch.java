import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FederationAddNewMatch extends JFrame {
    private JPanel FederationAddNewMatchForm;
    private JComboBox AppAdminMenuDropDown;
    private JButton logoutBtn;
    private JTextField textField1;
    private JComboBox stadiumBox;
    private JTextField matchDateField;
    private JComboBox homeTeamBox;
    private JLabel match_home_team;
    private JComboBox awayTeamBox;
    private JComboBox matchRestrictions;
    private JTextField matchTimeField;
    private JTextField homeCapacity;
    private JTextField awayCapacity;
    private JButton addMatchBttn;

    public FederationAddNewMatch(){
        setupTeams();
        setupStadium();
        setupRestrictions();
        setupFrame();
        setUpActions();
    }
    private void setupRestrictions(){
        matchRestrictions.addItem("NO RESTRICTIONS");
        matchRestrictions.addItem("NO AWAY FANS");
        matchRestrictions.addItem("NO FANS");
    }

    private void setupStadium(){
        String query = "SELECT stadium_name FROM stadiums";
        Connection connection = null;
        ResultSet rs = null;
        try {
            connection = ConnectDB.createConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next())
                stadiumBox.addItem(rs.getString(1));

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void setupTeams(){
        String query = "SELECT team_name FROM teams";
        Connection connection = null;
        ResultSet rs = null;
        try {
            connection = ConnectDB.createConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next()) {
                homeTeamBox.addItem(rs.getString(1));
                awayTeamBox.addItem(rs.getString(1));
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void setupFrame() {
        add(FederationAddNewMatchForm);
        setTitle("ArenaPass Add Match");
        setSize(1920, 1080);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setUpActions() {
        AppAdminMenuDropDown.addActionListener(this::switchPanel);
        logoutBtn.addActionListener(this::logout);
        addMatchBttn.addActionListener(this::addBttn);
    }

    private int getTeamID(String teamName){
        try {
            Connection connection = ConnectDB.createConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT team_id FROM teams WHERE team_name=?");
            ps.setString(1,teamName);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private int getStadiumID(String stadiumName){
        try {
            Connection connection = ConnectDB.createConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT stadium_id FROM stadiums WHERE stadium_name=?");
            ps.setString(1,stadiumName);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void addBttn(ActionEvent actionEvent){
        int homeID=getTeamID(homeTeamBox.getSelectedItem().toString());
        int awayID=getTeamID(awayTeamBox.getSelectedItem().toString());
        int stadiumID=getStadiumID(stadiumBox.getSelectedItem().toString());
        System.out.println(homeID+"\n"+awayID+"\n"+stadiumID+"\n");
    }

    private void switchPanel(ActionEvent actionEvent) {
        JOptionPane.showMessageDialog(AppAdminMenuDropDown, AppAdminMenuDropDown.getSelectedItem());

        switch (AppAdminMenuDropDown.getSelectedIndex()){
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
