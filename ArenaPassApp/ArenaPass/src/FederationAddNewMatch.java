import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.TimePicker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.sql.*;
import java.time.LocalDate;

public class FederationAddNewMatch extends JFrame {
    private JPanel FederationAddNewMatchForm;
    private JComboBox FedAdminMenuDropDown;
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
    private DatePicker datePicker;
    private TimePicker timePicker;
    private final LocalDate today = LocalDate.now();

    public FederationAddNewMatch(){
        setupTeams();
        setupStadium();
        setupRestrictions();
        setupFrame();
        setUpActions();
    }

    private void enableRestrictions(){
        homeCapacity.enable();
        awayCapacity.enable();
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
        homeCapacity.setName("HOME TEAM CAPACITY");
        awayCapacity.setName("AWAY TEAM CAPACITY");
        DatePickerSettings dateSettings = new DatePickerSettings();
        datePicker.setSettings(dateSettings);
        datePicker.setDateToToday();
        timePicker.setTimeToNow();
        dateSettings.setDateRangeLimits(today.minusDays(0),today.plusDays(20));
        add(FederationAddNewMatchForm);
        setTitle("ArenaPass Add Match");
        setSize(1920, 1080);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private boolean isNotEmpty(int htCapacity, int atCapacity){
        int restrictionsState=getRestrctionsState();
        switch (restrictionsState){
            case 1:
                if(htCapacity>=0 && atCapacity>=0)
                    return true;
            case 2:
                if(htCapacity>=0)
                    return true;
            case 3:
                return true;
            default:
                return false;
        }

    }

    private int getRestrctionsState(){
        if(matchRestrictions.getSelectedItem()=="NO FANS")
            return 3;
        else if(matchRestrictions.getSelectedItem()=="NO AWAY FANS")
            return 2;
        return 1;
    }

    private void setUpActions() {
        GlobalMenus globalMenus = new GlobalMenus(this);
        globalMenus.dropDownFedAdmin(FedAdminMenuDropDown);
        FedAdminMenuDropDown.addActionListener(this::switchPanel);
        logoutBtn.addActionListener(this::logout);
        addMatchBttn.addActionListener(this::addBttn);
        matchRestrictions.addItemListener(this::setupRestrictionsState);
    }

    private void setupRestrictionsState(ItemEvent itemEvent){
            enableRestrictions();
            if(matchRestrictions.getSelectedItem()=="NO FANS"){
                homeCapacity.disable();
                awayCapacity.disable();
            }
            else if(matchRestrictions.getSelectedItem()=="NO AWAY FANS")
                awayCapacity.disable();
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

    private int toNumber(JTextField textfield){
        try {
            int num=Integer.parseInt(textfield.getText());
            return num;
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(textfield, textfield.getName()+" NOT NUMBER");
        }
        return -1;
    }

    private void addBttn(ActionEvent actionEvent){
        int homeID=getTeamID(homeTeamBox.getSelectedItem().toString());
        int awayID=getTeamID(awayTeamBox.getSelectedItem().toString());
        int stadiumID=getStadiumID(stadiumBox.getSelectedItem().toString());
        int htCapacity=0;
        int atCapacity=0;
        if(getRestrctionsState()==1)  { htCapacity=toNumber(homeCapacity); atCapacity=toNumber(awayCapacity);}
        else if(getRestrctionsState()==2)  { htCapacity=toNumber(homeCapacity); atCapacity=0; }
        else if(getRestrctionsState()==3) { htCapacity=0; atCapacity=0; }

        Date selectedDate= Date.valueOf(datePicker.getDate());
        Time selectedTime = Time.valueOf(timePicker.getTime());

        if(isNotEmpty(htCapacity,atCapacity))
            insertIntoTable(homeID,awayID,stadiumID,htCapacity,atCapacity,selectedDate,selectedTime,getRestrctionsState());


        System.out.println(homeID+"\n"+awayID+"\n"+stadiumID+"\n"+selectedDate+"\n"+selectedTime+"\n"+htCapacity+"\n"+atCapacity);
    }

    private void insertIntoTable(int homeID, int awayID, int stadiumID, int htCapacity, int atCapacity, Date selectedDate, Time selectedTime,int restriction) {

        Connection connection = null;
        try {
            connection = ConnectDB.createConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO matches (match_home_team,match_away_team,match_stadium_id,match_ht_max_capacity,match_at_max_capacity,match_date,match_time,match_restrictions) VALUES (?,?,?,?,?,?,?,?)");
            ps.setInt(1,homeID);
            ps.setInt(2,awayID);
            ps.setInt(3,stadiumID);
            ps.setInt(4,htCapacity);
            ps.setInt(5,atCapacity);
            ps.setDate(6,selectedDate);
            ps.setTime(7,selectedTime);
            ps.setInt(8,restriction);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null,"Match succesfully added");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
