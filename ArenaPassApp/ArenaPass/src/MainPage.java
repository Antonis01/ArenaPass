import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage {
    private JButton showFanIDButton;
    private JButton buySeasonTicketButton;
    private JButton buyTicketButton;

    public MainPage() {
        showFanIDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(showFanIDButton ,"View FanID will be opened.");
            }
        });
        buyTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(buyTicketButton ,"View Tickets Form will be opened.");
            }
        });
        buySeasonTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(buyTicketButton ,"View Season Tickets Form will be opened.");
            }
        });
    }
}
