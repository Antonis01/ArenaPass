import javax.swing.*;
import java.awt.event.ActionEvent;

public class Busses extends JFrame {
    private JPanel BussesForm;
    private JButton logoutBtn;
    private JComboBox mainMenuDropDown;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JComboBox comboBox1;
    private JButton submitButton;

    public Busses() {
        setupFrame();
        setUpActions();
    }

    private void setupFrame() {
        add(BussesForm);
        setTitle("Bus Application Form");
        setSize(1920, 1080);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setUpActions() {
        mainMenuDropDown.addActionListener(this::switchPanel);
        logoutBtn.addActionListener(this::logout);
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


