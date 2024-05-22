import javax.swing.*;

public class SelectTeam extends JFrame{
    private JPanel teamsPanel;
    private JButton team1;
    private JButton team2;
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
        setTitle("ArenaPass Login");
        setSize(1920, 1080);
        setVisible(true);
    }

    public SelectTeam(){
        setupFrame();
        System.out.println(team1Name+" "+team2Name);
        System.out.println(team2Logo+" "+team2Logo);

        //team1.setText(team1Name);
        //team2.setText(team2Name);
        team1.setIcon(((Icon)new  ImageIcon("images/"+team1Logo)));
        team2.setIcon(((Icon) new ImageIcon("images/"+team2Logo)));
    }

}
