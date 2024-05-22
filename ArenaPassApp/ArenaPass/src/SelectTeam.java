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

    public static void setTeamNames(String team1Name, String team2Name) {
        SelectTeam.team1Name = team1Name;
        SelectTeam.team2Name = team2Name;
    }

    public static void setTeamLogos(String team1Logo, String team2Logo) {
        SelectTeam.team1Logo = team1Logo;
        SelectTeam.team2Logo = team2Logo;
    }


    private void setupFrame() {
        setContentPane(SelectTeamForm);
        setTitle("Select Team");
        setSize(1920, 1080);
        setVisible(true);
    }

    public SelectTeam(){
        setupFrame();
        //teamsPanel = new JPanel();
        teamsPanel.setLayout(new GridLayout(1,2));
        System.out.println(team1Name+" "+team2Name);
        System.out.println(team1Logo+" "+team2Logo);
        JButton team1 = new JButton();
        JButton team2 = new JButton();

        teamsPanel.add(team1);
        teamsPanel.add(team2);

        /*team1.setText(team1Name);
        team2.setText(team2Name);*/
        String path1;
        String path2;
        path1="/images/"+team1Logo;
        path2="/images/"+team2Logo;
        try {
            Image img = ImageIO.read(getClass().getResource(path1));
            team1.setIcon(new ImageIcon(img));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            Image img = ImageIO.read(getClass().getResource(path2));
            team2.setIcon(new ImageIcon(img));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }




        /*team1.setIcon(((Icon)new  ImageIcon(path1)));
        team2.setIcon(((Icon) new ImageIcon(path2)));*/

        team1.setOpaque(true);
        team2.setOpaque(true);

    }

}