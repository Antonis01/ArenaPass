import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.*;


public class selectSection extends JFrame{

    private JPanel SelectTeamForm1;
    private JPanel selectTeamForm;
    private JComboBox mainMenuDropDown;
    private JLabel logo;
    private JTextField textField1;
    private JButton logoutBtn;
    private JButton button1;
    private JLabel sectionLabel;
    private JLabel teamLogo;
    private JComboBox viewSections;
    private Match match;
    private String side;

    public selectSection(Match match, Image logo, int clicked) {
        this.match = match;
        teamLogo.setIcon(new ImageIcon(logo));
        if(clicked == 0)   {
            teamLogo.setText(match.getHomeTeam());
            this.side="HT";
        }
        else {
            teamLogo.setText(match.getAwayTeam());
            this.side="AT";
        }
        setupFrame();
        setUpActions();
    }


    public selectSection(){
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
        mainMenuDropDown.addActionListener(this::switchPanel);
        logoutBtn.addActionListener(this::logout);
        button1.addActionListener(this::seatSelect);
        fillComboBox(viewSections);
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
            new seatSelect(match,section).setVisible(true);
        }
    }

    private void switchPanel(ActionEvent actionEvent) {
        JOptionPane.showMessageDialog(mainMenuDropDown, mainMenuDropDown.getSelectedItem());

        switch (mainMenuDropDown.getSelectedIndex()){
            case 0:
                setVisible(false);
                dispose();
                new MainPage().setVisible(true);
                break;
            case 1:
                setVisible(false);
                dispose();
                try {
                    new BuyTicket().setVisible(true);
                }catch (SQLException sqle){
                    sqle.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }                break;
            case 2:
                // new BuySeasonTicket().setVisible(true);
                break;
            case 3:
                // cancel reservation
                break;
            case 4:
                // ticket history
                break;
            case 5:
                // Leaderboards
                break;
            case 6:
                // Busses
                break;
            case 7:
                // Feedback
                break;
            case 8:
                // contact us
                break;
        }
    }

    private void logout(ActionEvent actionEvent) {
        setVisible(false);
        dispose();
        new LoginUI().setVisible(true);
    }
}
