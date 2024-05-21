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
    private JButton seasonBtn9;
    private JButton seasonBtn10;
    private JButton seasonBtn11;
    private JButton seasonBtn12;
    private JButton seasonBtn8;
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
        mainMenuDropDown.addActionListener(this::switchPanel);
        logoutBtn.addActionListener(this::logout);
        seasonBtn1.addActionListener(this::buySeasonTicket1);
        //seasonBtn2.addActionListener(this::buySeasonTicket2);
        //seasonBtn3.addActionListener(this::buySeasonTicket3);
        //seasonBtn4.addActionListener(this::buySeasonTicket4);
        //seasonBtn5.addActionListener(this::buySeasonTicket5);
        //seasonBtn6.addActionListener(this::buySeasonTicket6);
        //seasonBtn7.addActionListener(this::buySeasonTicket7);
        //seasonBtn8.addActionListener(this::buySeasonTicket8);
        //seasonBtn9.addActionListener(this::buySeasonTicket9);
        //seasonBtn10.addActionListener(this::buySeasonTicket10);
        //seasonBtn11.addActionListener(this::buySeasonTicket11);
        //seasonBtn12.addActionListener(this::buySeasonTicket12);
        //seasonBtn13.addActionListener(this::buySeasonTicket13);
        //seasonBtn14.addActionListener(this::buySeasonTicket14);
    }

    private void buySeasonTicket1(ActionEvent actionEvent) {
        JOptionPane.showMessageDialog(null, "Season Ticket for Olympiacos");
        setVisible(false);
        dispose();
        new seatSelect().setVisible(true);
    }

    private void switchPanel(ActionEvent actionEvent) {
        JOptionPane.showMessageDialog(mainMenuDropDown, mainMenuDropDown.getSelectedItem());

        switch (mainMenuDropDown.getSelectedIndex()){
            case 0:
                setVisible(false);
                dispose();
                new MainPage().setVisible(true);
                break;
            case 1:
                setVisible(false);
                dispose();
                new BuyTicket().setVisible(true);
                break;
            case 2:
                setVisible(false);
                dispose();
                new BuySeasonTicket().setVisible(true);
                break;
            case 3:
                // cancel reservation
                break;
            case 4:
                // ticket history
                break;
            case 5:
                // Leaderboards
                break;
            case 6:
                // Busses
                break;
            case 7:
                // Feedback
                break;
            case 8:
                // contact us
                break;
        }
    }

    private void logout(ActionEvent actionEvent) {
        setVisible(false);
        dispose();
        new LoginUI().setVisible(true);
    }
}


