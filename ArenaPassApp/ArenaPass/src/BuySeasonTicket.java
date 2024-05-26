import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    private void buySeasonTicket(ActionEvent actionEvent) {
        JButton button = (JButton) actionEvent.getSource();
        String buttonText = button.getToolTipText();
        JOptionPane.showMessageDialog(null, "Season Ticket for " + buttonText);
        setVisible(false);
        dispose();
        new selectSection().setVisible(true);
    }
}