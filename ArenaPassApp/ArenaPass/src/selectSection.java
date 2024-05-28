import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;


public class selectSection extends JFrame{

    private JPanel SelectTeamForm1;
    private JPanel selectTeamForm;
    private JComboBox mainMenuDropDown;
    private JLabel logo;
    private JTextField textField1;
    private JButton logoutBtn;
    private JButton buttonTicket;
    private JLabel sectionLabel;
    private JLabel teamLogo;
    private JComboBox viewSections;
    private JButton buttonSeason;
    private Match match;
    private String side;
    String teamName;
    private int stadiumID;
    private boolean isSeason;

    public selectSection(Match match, Image logo, int clicked) {
        buttonSeason.setVisible(false);
        isSeason=false;
        this.match = match;
        this.stadiumID=match.getStadiumID();
        teamLogo.setIcon(new ImageIcon(logo));
        if(clicked == 0)   {
            teamLogo.setText(match.getHomeTeam());
            this.side="HT";
        }
        else {
            teamLogo.setText(match.getAwayTeam());
            this.side="AT";
        }
        fillComboBox(viewSections);
        setupFrame();
        setUpActions();
    }

    public selectSection(String team, Image logo,int stadiumID){
        System.out.println("SEASON");
        buttonTicket.setVisible(false);
        this.stadiumID=stadiumID;
        this.teamName=team;
        isSeason=true;
        teamLogo.setIcon(new ImageIcon(logo));
        teamLogo.setText(team);
        fillComboBoxSeason(viewSections,stadiumID);
        setupFrame();
        setUpActions();
    }

    private void setupFrame() {
        add(SelectTeamForm1);
        setTitle("Choose Match");
        setSize(1920, 1080);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setUpActions() {
        GlobalMenus globalMenus = new GlobalMenus(this);
        mainMenuDropDown.addActionListener(globalMenus::switchPanel);
        logoutBtn.addActionListener(globalMenus::logout);
        buttonTicket.addActionListener(this::seatSelect);
        buttonSeason.addActionListener(this::seatSelect);
    }

    private void fillComboBoxSeason(JComboBox addSections, int stadiumID){
        try {
            Connection connection = ConnectDB.createConnection();
            String sql = "SELECT DISTINCT seat_section FROM seats WHERE seat_stadium_id = ? AND seat_side = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Set the parameters for the placeholders
            preparedStatement.setInt(1, stadiumID);
            preparedStatement.setString(2, "HT");

            ResultSet resultSet = preparedStatement.executeQuery();

            addSections.removeAllItems();

            while (resultSet.next()) {
                String item = resultSet.getString(1); // replace with your column
                addSections.addItem(item);
            }
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }


    public void fillComboBox(JComboBox addSections) {
        try {
            Connection connection = ConnectDB.createConnection();
            String sql = "SELECT DISTINCT seat_section FROM seats WHERE seat_stadium_id = ? AND seat_side = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Set the parameters for the placeholders
            preparedStatement.setInt(1, match.getStadiumID());
            preparedStatement.setString(2, side);

            ResultSet resultSet = preparedStatement.executeQuery();

            addSections.removeAllItems();

            while (resultSet.next()) {
                String item = resultSet.getString(1); // replace with your column
                addSections.addItem(item);
            }
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }

    private void seatSelect(ActionEvent actionEvent) {
        String section = (String) viewSections.getSelectedItem();
        if(section == null || section.isEmpty())
            JOptionPane.showMessageDialog(null,"No section selected");
        else {
            //seatSelect.setSection(section);
            setVisible(false);
            dispose();
            if(isSeason)    {
                System.out.println("SEASON");
                new seatSelect(teamName,section,stadiumID).setVisible(true);
            }
            else
                new seatSelect(match,section).setVisible(true);
        }
    }
}
