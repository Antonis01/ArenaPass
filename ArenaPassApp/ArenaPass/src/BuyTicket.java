import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class BuyTicket extends JFrame {
    private JComboBox mainMenuDropDown;
    private JLabel logo;
    private JTextField textField1;
    private JButton logoutBtn;
    private JScrollPane scrollPanel;
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

    private String getLogo(int teamID) throws SQLException {
        String query = "SELECT team_logo_path FROM teams WHERE team_id = ?";
        Connection connection = ConnectDB.createConnection();
        Statement statement = connection.createStatement();
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1,teamID);
        try{
            ResultSet rs = ps.executeQuery();
            rs.next();
            return "/images/"+rs.getString(1);
        } catch (SQLException e) {
            return "FALSE ID";
        }
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

    private String getGameStadium(int teamID) throws SQLException {
        String query = "SELECT stadium_name FROM stadiums WHERE stadium_id = ?";
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

    public BuyTicket() throws SQLException, IOException {
        matchesPanel.setVisible(true);
        setupFrame();
        setUpActions();
        setupMatches();

    }

    private void setupMatches() throws SQLException, IOException {
        String query = "SELECT * FROM matches where match_date>CURRENT_DATE() order by match_date ASC,match_time ASC";
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
            matchPanels[i]= new JPanel(new GridLayout());
            matchPanels[i].setMaximumSize(new Dimension(0,100));

            //matchPanels[i].setBorder(new EmptyBorder(0,0,250,0));
            int matchID=rs.getInt(1);
            int stadiumID=rs.getInt(2);
            int homeTeamID=rs.getInt(3);
            int awayTeamID=rs.getInt(4);
            Date matchDate = rs.getDate(5);
            Time matchTime = rs.getTime(6);

            Match currentMatch = new Match(matchID,getTeamName(homeTeamID),getTeamName(awayTeamID),stadiumID, matchDate, matchTime);
            matches.add(currentMatch);

            JLabel homeIcon = new JLabel();
            JLabel awayIcon = new JLabel();
            JLabel fixture = new JLabel(currentMatch.getHomeTeam() +" VS " +currentMatch.getAwayTeam());
            JLabel gameDate = new JLabel(matchDate.toString());
            JLabel gameTime = new JLabel(matchTime.toString());
            JLabel gameStadium = new JLabel(getGameStadium(stadiumID));
            JButton buyBttn = new JButton("BUY");

            Image logoHome = ImageIO.read(getClass().getResource(getLogo(homeTeamID)));
            homeIcon.setIcon(new ImageIcon(logoHome));
            Image logoAway = ImageIO.read(getClass().getResource(getLogo(awayTeamID)));
            awayIcon.setIcon(new ImageIcon(logoAway));

            /*homeIcon.setForeground(Color.GREEN);
            awayIcon.setForeground(Color.BLUE);*/

            //buyBttn.setMaximumSize(new Dimension(0,100));
            buyBttn.setOpaque(true);

            matchPanels[i].add(homeIcon);
            matchPanels[i].add(awayIcon);
            matchPanels[i].add(fixture);
            matchPanels[i].add(gameDate);
            matchPanels[i].add(gameTime);
            matchPanels[i].add(gameStadium);
            matchPanels[i].add(buyBttn);

            buyBttn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                    dispose();
                    new SelectTeam(currentMatch,logoHome,logoAway );
                }
            });
            matchesPanel.add(matchPanels[i]);
            i++;
        }
    }

    private void setupFrame() {
        add(BuyTicketNewForm);
        setTitle("Buy Ticket");
        setSize(1920, 1080);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setUpActions() {
        GlobalMenus globalMenus = new GlobalMenus(this);
        mainMenuDropDown.addActionListener(globalMenus::switchPanel);
        logoutBtn.addActionListener(globalMenus::logout);
    }
}
