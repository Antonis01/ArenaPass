import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectTeam extends JFrame{
    private JPanel teamsPanel;
    private JPanel SelectTeamForm;


    private void setupFrame() {
        setContentPane(SelectTeamForm);
        setTitle("Select Team");
        setSize(1920, 1080);
        setVisible(true);
    }

    public SelectTeam(Match match,Image homeTeamLogo, Image awayTeamLogo){
        setupFrame();
        //teamsPanel = new JPanel();
        teamsPanel.setLayout(new GridBagLayout());
        JButton team1 = new JButton();
        JButton team2 = new JButton();

        teamsPanel.add(team1);
        teamsPanel.add(team2);

        team1.setIcon(new ImageIcon(homeTeamLogo));
        team1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
                new selectSection(match,homeTeamLogo,0);
            }
        });
        team2.setIcon(new ImageIcon(awayTeamLogo));
        team2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
                new selectSection(match,awayTeamLogo,1);
            }
        });

        team1.setOpaque(true);
        team2.setOpaque(true);

    }

}