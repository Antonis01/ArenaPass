import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePicker;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;


public class ModifyMatch extends JFrame{
    private JPanel ModifyMatch;
    private JComboBox FedAdminMenuDropDown;
    private JTextField textField1;
    private JButton logoutBtn;
    private DatePicker changeDate;
    private JButton changeDateButton;
    private JButton changeTimeButton;
    private TimePicker changeTime;
    private JButton stadiumButton;
    private JLabel awayTeamLabel;
    private JLabel homeTeamLabel;
    private JComboBox stadiumBox;
    private final Match match;
    private int stadiumCount;
    private ArrayList<Stadium> stadiums;

    public ModifyMatch(Match match){
        this.match=match;
        setupFrame();
        setUpActions();
        setupStadiums();
    }

    private void setupFrame() {
        add(ModifyMatch);
        DatePickerSettings dateSettings = new DatePickerSettings();
        changeDate.setSettings(dateSettings);
        LocalDate today = LocalDate.now();
        dateSettings.setDateRangeLimits(today.minusDays(0),today.plusMonths(1));

        changeDate.setDateToToday();
        setTitle("ArenaPass Modify Match "+match.getHomeTeam()+" vs "+match.getAwayTeam());
        homeTeamLabel.setText(match.getHomeTeam());
        awayTeamLabel.setText(match.getAwayTeam());
        setSize(1920, 1080);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private int getStadiumCount(){
        try {
            Connection connection = ConnectDB.createConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM stadiums");
            ResultSet rs= ps.executeQuery();
            rs.next();
            return rs.getInt(1);
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setupStadiums(){
        try {
            Connection connection = ConnectDB.createConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT stadium_name,stadium_id FROM stadiums");
            ResultSet rs= ps.executeQuery();
            this.stadiumCount=getStadiumCount();
            this.stadiums = new ArrayList<>(stadiumCount);
            Stadium currStadium;
            while(rs.next()){
                currStadium= new Stadium(rs.getString(1),rs.getInt(2));
                stadiumBox.addItem(currStadium.getStadiumName());
                stadiums.add(currStadium);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void setUpActions() {
        GlobalMenus globalMenus = new GlobalMenus(this);
        GlobalMenus.dropDownFedAdmin(FedAdminMenuDropDown);
        FedAdminMenuDropDown.addActionListener(this::switchPanel);
        stadiumButton.addActionListener(this::setStadiumButton);
        changeDateButton.addActionListener(this::setDateButton);
        changeTimeButton.addActionListener(this::setTimeButton);
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

    private void setStadiumButton(ActionEvent actionEvent){
        try {
            String query = "UPDATE matches SET match_stadium_id=? WHERE match_id=?";
            Connection connection = ConnectDB.createConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1,stadiums.get(stadiumBox.getSelectedIndex()).getStadiumID());
            ps.setInt(2,match.getMatchID());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null,"Stadium has been updated to "+stadiumBox.getSelectedItem());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void setDateButton(ActionEvent actionEvent){
        try {
            String query = "UPDATE matches SET match_date=? WHERE match_id=?";
            Connection connection = ConnectDB.createConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setDate(1, Date.valueOf(changeDate.getDate()));
            ps.setInt(2,match.getMatchID());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null,"Date has been changed to "+changeDate.getDate().toString());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void setTimeButton(ActionEvent actionEvent){
        try {
            String query = "UPDATE matches SET match_time=? WHERE match_id=?";
            Connection connection = ConnectDB.createConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setTime(1, Time.valueOf(changeTime.getTime()));
            ps.setInt(2,match.getMatchID());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null,"Time has been changed to "+changeTime.getTime().toString());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
            throw new RuntimeException(e);
        }
    }

}


