import javax.swing.*;
import java.awt.event.ActionEvent;

public class FeedBack extends JFrame {
    private JPanel FeedBackForm;
    private JComboBox mainMenuDropDown;
    private JTextField textField1;
    private JButton logoutBtn;
    private JTextField textField2;
    private JTextField textField3;
    private JButton sumbitBtn;


    public FeedBack() {
        setupFrame();
        setUpActions();
    }
    private void setupFrame() {
        add(FeedBackForm);
        setTitle("ArenaPass FeedBack");
        setSize(1920, 1080);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void setUpActions() {
        mainMenuDropDown.addActionListener(this::switchPanel);
        logoutBtn.addActionListener(this::logout);
        sumbitBtn.addActionListener(this::submitForm);
    }

    private void submitForm(ActionEvent actionEvent){
        JOptionPane.showMessageDialog(sumbitBtn,"successfully submit");
        setVisible(false);
        dispose();
        new MainPage().setVisible(true);
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

