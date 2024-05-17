import javax.swing.*;


public class BuyTicket extends JFrame{
    private JLabel logo;
    private JButton button1;
    private JPanel BuyTicketForm;
    private JPanel BuyTicketPanel;

    public BuyTicket(){
        dispose();
        add(BuyTicketForm);
        setTitle("Buy Tickets");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1920,1080);
    }
}
