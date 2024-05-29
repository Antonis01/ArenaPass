import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;

public class BuySeasonTicket extends JFrame{
    private JPanel BuySeasonTicketForm;
    private JComboBox mainMenuDropDown;
    private JButton logoutBtn;
    private JTextField textField1;
    private JButton seasonBtn1;
    private JButton seasonBtn2;
    private JButton seasonBtn3;
    private JButton seasonBtn4;
    private JButton seasonBtn5;
    private JButton seasonBtn6;
    private JButton seasonBtn7;
    private JButton seasonBtn8;
    private JButton seasonBtn9;
    private JButton seasonBtn10;
    private JButton seasonBtn11;
    private JButton seasonBtn12;
    private JButton seasonBtn13;
    private JButton seasonBtn14;
    private JScrollBar scrollBar1;

    public BuySeasonTicket(){
        setupFrame();
        setUpActions();
        seasonBtn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
    }

    private void setupFrame(){
        dispose();
        add(BuySeasonTicketForm);
        setTitle("Buy Tickets");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1920,1080);
    }

    private void setUpActions() {
        GlobalMenus globalMenus = new GlobalMenus(this);
        globalMenus.dropDownFan(mainMenuDropDown);
        mainMenuDropDown.addActionListener(globalMenus::switchPanel);
        logoutBtn.addActionListener(globalMenus::logout);
        seasonBtn1.addActionListener(this::buySeasonTicket);
        seasonBtn2.addActionListener(this::buySeasonTicket);
        seasonBtn3.addActionListener(this::buySeasonTicket);
        seasonBtn4.addActionListener(this::buySeasonTicket);
        seasonBtn5.addActionListener(this::buySeasonTicket);
        seasonBtn6.addActionListener(this::buySeasonTicket);
        seasonBtn7.addActionListener(this::buySeasonTicket);
        seasonBtn8.addActionListener(this::buySeasonTicket);
        seasonBtn9.addActionListener(this::buySeasonTicket);
        seasonBtn10.addActionListener(this::buySeasonTicket);
        seasonBtn11.addActionListener(this::buySeasonTicket);
        seasonBtn12.addActionListener(this::buySeasonTicket);
        seasonBtn13.addActionListener(this::buySeasonTicket);
        seasonBtn14.addActionListener(this::buySeasonTicket);
    }

    private String getLogo(String teamName) throws SQLException {
        String query = "SELECT team_logo_path FROM teams WHERE team_name = ?";
        Connection connection = ConnectDB.createConnection();
        Statement statement = connection.createStatement();
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1,teamName);
        try{
            ResultSet rs = ps.executeQuery();
            rs.next();
            return "/images/"+rs.getString(1);
        } catch (SQLException e) {
            return "FALSE ID";
        }
    }

    public int getStadiumID(String teamName) throws SQLException{
        String query = "SELECT team_def_home_stadium_id FROM teams WHERE team_name = ?";
        Connection connection = ConnectDB.createConnection();
        Statement statement = connection.createStatement();
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1,teamName);
        try{
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e);
            return -1;
        }
    }

    private void buySeasonTicket(ActionEvent actionEvent) {
        JButton button = (JButton) actionEvent.getSource();
        String buttonText = button.getToolTipText();
        JOptionPane.showMessageDialog(null, "Season Ticket for " + buttonText);
        Image logo = null;
        try {
            logo = ImageIO.read(getClass().getResource(getLogo(buttonText)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        setVisible(false);
        dispose();
        try {
            new selectSection(buttonText,logo,getStadiumID(buttonText));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}