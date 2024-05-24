import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;

public class MainPage extends JFrame {
    private JButton showFanIDButton;
    private JButton buySeasonTicketButton;
    private JButton buyTicketButton;
    private JPanel MainPageForm;
    private JComboBox mainMenuDropDown;
    private JTextField textField1;
    private JButton logoutBtn;
    private JLabel imageLabel;

    public MainPage() {
        setupFrame();
        setUpActions();
    }

    private void setupFrame() {
        add(MainPageForm);
        setTitle("ArenaPass MainPage");
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
                try {
                    new BuyTicket().setVisible(true);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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