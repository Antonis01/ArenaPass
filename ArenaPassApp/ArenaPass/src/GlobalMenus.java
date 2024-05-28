import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.sql.SQLException;

public class GlobalMenus {
    private JFrame currentFrame;

    public GlobalMenus(JFrame currentFrame) {
        this.currentFrame = currentFrame;
    }

    public static void dropDownFan(JComboBox comboBox) {
        comboBox.removeAllItems();
        comboBox.addItem("Home");
        comboBox.addItem("Buy Ticket");
        comboBox.addItem("Buy Season Ticket");
        comboBox.addItem("Cancel Reservation");
        comboBox.addItem("Ticket History");
        comboBox.addItem("Leaderboards");
        comboBox.addItem("Busses");
        comboBox.addItem("Feedback");
        comboBox.addItem("Contact us");
    }

    public static void dropDownAdmin(JComboBox comboBox) {
        comboBox.removeAllItems();
        comboBox.addItem("Home");
        comboBox.addItem("FanID Application Form");
        comboBox.addItem("Feedback Form");
        comboBox.addItem("Ban Users Chatroom");
        comboBox.addItem("Busses Form");
    }

    public void switchPanel(ActionEvent actionEvent) {
        JComboBox comboBox = (JComboBox) actionEvent.getSource();
        JOptionPane.showMessageDialog(comboBox, comboBox.getSelectedItem());

        switch (comboBox.getSelectedIndex()) {
            case 0:
                currentFrame.setVisible(false);
                currentFrame.dispose();
                new MainPage().setVisible(true);
                break;
            case 1:
                currentFrame.setVisible(false);
                currentFrame.dispose();
                try {
                    new BuyTicket().setVisible(true);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 2:
                currentFrame.setVisible(false);
                currentFrame.dispose();
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
                currentFrame.setVisible(false);
                currentFrame.dispose();
                new Busses().setVisible(true);
                break;
            case 7:
                // Feedback
                currentFrame.setVisible(false);
                currentFrame.dispose();
                new FeedBack().setVisible(true);
                break;
            case 8:
                // contact us
                break;
            case 9:
                // Chatroom
                currentFrame.setVisible(false);
                currentFrame.dispose();
                new ChatRoom().setVisible(true);
                break;
        }
    }

    public void logout(ActionEvent actionEvent) {
        JButton button = (JButton) actionEvent.getSource();
        currentFrame.setVisible(false);
        currentFrame.dispose();
        new LoginUI().setVisible(true);
    }
}
