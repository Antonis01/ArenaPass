import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.ArrayList;

public class BuyTicketNew extends JFrame {
    private JComboBox mainMenuDropDown;
    private JLabel logo;
    private JTextField textField1;
    private JButton logoutBtn;
    private JPanel matchesPanel;
    private JPanel BuyTicketNewForm;

    private int getRows(ResultSet rs){
        int size=0;
        try {
            rs.last();
            size = rs.getRow();
            rs.beforeFirst();
        } catch (SQLException ex) {
            return 0;
        }
        return size;
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

    public BuyTicketNew() throws SQLException {
        matchesPanel.setVisible(true);
        setupFrame();
        setUpActions();
        String query = "SELECT * FROM matches";
        Connection connection = ConnectDB.createConnection();
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = null;
        PreparedStatement ps = connection.prepareStatement(query);
        rs=statement.executeQuery(query);

        int rows=getRows(rs);
        matchesPanel.setLayout(new GridLayout(rows,1));
        System.out.println(rows);
        JPanel[] matchPanels = new JPanel[rows];
        ArrayList<Match> matches = new ArrayList<>(rows);
        int i=0;
            while(rs.next())
            {
                matchPanels[i]= new JPanel(new GridLayout(1,6));
                matchPanels[i].setPreferredSize(new Dimension(-1,100));

                //matchPanels[i].setBorder(new EmptyBorder(0,0,250,0);

                int stadiumID=rs.getInt(2);
                int homeTeamID=rs.getInt(3);
                int awayTeamID=rs.getInt(4);
                Date matchDate = rs.getDate(5);
                Time matchTime = rs.getTime(6);

                String teamLogo= null;
                Match currentMatch = new Match(getTeamName(homeTeamID),getTeamName(awayTeamID),stadiumID);
                matches.add(currentMatch);

                JLabel homeIcon = new JLabel(currentMatch.getHomeTeam());
                JLabel awayIcon = new JLabel(currentMatch.getAwayTeam());
                JLabel fixture = new JLabel(currentMatch.getHomeTeam() +" VS " +currentMatch.getAwayTeam());
                JLabel gameDate = new JLabel(matchDate.toString());
                JLabel gameTime = new JLabel(matchTime.toString());
                JButton buyBttn = new JButton("BUY");

                homeIcon.setForeground(Color.GREEN);
                awayIcon.setForeground(Color.BLUE);

                matchPanels[i].add(homeIcon);
                matchPanels[i].add(awayIcon);
                matchPanels[i].add(fixture);
                matchPanels[i].add(gameDate);
                matchPanels[i].add(gameTime);
                matchPanels[i].add(buyBttn);

                matchPanels[i].setVisible(true);

                matchesPanel.add(matchPanels[i],BorderLayout.LINE_START);

                i++;
            }


    }

    void setupMatches(){

    }

    private void setupFrame() {
        add(BuyTicketNewForm);
        setTitle("Buy Ticket");
        setSize(1920, 1080);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setUpActions() {
        mainMenuDropDown.addActionListener(this::switchPanel);
        logoutBtn.addActionListener(this::logout);

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
                new BuyTicket().setVisible(true);
                break;
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
