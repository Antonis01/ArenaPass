import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class SelectTeam extends JFrame{
    private JPanel teamsPanel;
    private JPanel SelectTeamForm;
    private static String team1Name;
    private static String team2Name;
    private static String team1Logo;
    private static String team2Logo;


    private void setupFrame() {
        setContentPane(SelectTeamForm);
        setTitle("Select Team");
        setSize(1920, 1080);
        setVisible(true);
    }

    public SelectTeam(String homeTeamName,String awayTeamName,Image homeTeamLogo, Image awayTeamLogo){
        setupFrame();
        //teamsPanel = new JPanel();
        teamsPanel.setLayout(new GridBagLayout());
        JButton team1 = new JButton();
        JButton team2 = new JButton();

        teamsPanel.add(team1);
        teamsPanel.add(team2);

        team1.setIcon(new ImageIcon(homeTeamLogo));
        team2.setIcon(new ImageIcon(awayTeamLogo));

        team1.setOpaque(true);
        team2.setOpaque(true);

    }

}