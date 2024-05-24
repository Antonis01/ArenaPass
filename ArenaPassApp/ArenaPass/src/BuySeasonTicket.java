import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

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
        mainMenuDropDown.addActionListener(this::switchPanel);
        logoutBtn.addActionListener(this::logout);
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
                try {
                    new BuyTicket().setVisible(true);
                }catch (SQLException sqle){
                    sqle.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }                break;
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
                setVisible(false);
                dispose();
                new Busses().setVisible(true);
                break;
            case 7:
                // Feedback
                setVisible(false);
                dispose();
                new FeedBack().setVisible(true);
                break;
            case 8:
                // contact us
                break;
            case 9:
                // Chatroom
                setVisible(false);
                dispose();
                new ChatRoom().setVisible(true);
                break;
        }
    }

    private void logout(ActionEvent actionEvent) {
        setVisible(false);
        dispose();
        new LoginUI().setVisible(true);
    }
}


