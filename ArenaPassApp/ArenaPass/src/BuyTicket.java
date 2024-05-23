import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class BuyTicket extends JFrame{
    private JLabel logo;
    private JPanel BuyTicketForm;
    private JComboBox mainMenuDropDown;
    private JTextField textField1;
    private JButton logoutBtn;
    private JButton buyBtnMatch1;
    private JButton buyBtnMatch2;
    private JButton buyBtnMatch3;
    private JButton buyBtnMatch4;
    private JPanel BuyTicketPanel;

    public BuyTicket(){
        setupFrame();
        setUpActions();
    }

    private void setupFrame(){
        dispose();
        add(BuyTicketForm);
        setTitle("Buy Tickets");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1920,1080);
    }

    private void setUpActions() {
        mainMenuDropDown.addActionListener(this::switchPanel);
        logoutBtn.addActionListener(this::logout);
        buyBtnMatch1.addActionListener(this::buyTicket1);
        buyBtnMatch2.addActionListener(this::buyTicket2);
        buyBtnMatch3.addActionListener(this::buyTicket3);
        buyBtnMatch4.addActionListener(this::buyTicket4);
    }

    private void buyTicket1(ActionEvent actionEvent) {
        JOptionPane.showMessageDialog(null, "Selected Match: 1");
        //SelectTeam.setTeamNames("Olympiakos","Panathinaikos");
        //SelectTeam.setTeamLogos("Olympiacos_FC_logo.svg.png","Panathinaikos_F.C._logo.svg.png");
        setVisible(false);
        dispose();
        //new SelectTeam().setVisible(true);

        new chooseMatch().setVisible(true);
    }

    private void buyTicket2(ActionEvent actionEvent) {
        JOptionPane.showMessageDialog(null, "Selected Match: 2");
        setVisible(false);
        dispose();
        //new selectMatch2().setVisible(true);
    }

    private void buyTicket3(ActionEvent actionEvent) {
        JOptionPane.showMessageDialog(null, "Selected Match: 3");
        setVisible(false);
        dispose();
        //new selectMatch3().setVisible(true);
    }

    private void buyTicket4(ActionEvent actionEvent) {
        JOptionPane.showMessageDialog(null, "Selected Match: 4");
        setVisible(false);
        dispose();
        //new selectMatch4().setVisible(true);
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
                // new BuySeasonTicket().setVisible(true);
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
        }
    }

    private void logout(ActionEvent actionEvent) {
        setVisible(false);
        dispose();
        new LoginUI().setVisible(true);
    }
}
