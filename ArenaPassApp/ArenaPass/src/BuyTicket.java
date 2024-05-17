import javax.swing.*;

public class BuyTicket extends JFrame{
    private JLabel logo;

    public BuyTicket(){
        ImageIcon image = new ImageIcon("../ArenaPass.png");

        JLabel label = new JLabel();
        label.setIcon(image);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(SwingConstants.TOP);


        JFrame frame = new JFrame();
        frame.setTitle("Buy Tickets");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setVisible(true);
        frame.add(label);
    }

}
