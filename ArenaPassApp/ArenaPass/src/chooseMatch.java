import javax.swing.*;
import java.awt.event.ActionEvent;

public class chooseMatch extends JFrame{

    private JPanel SelectTeamForm1;
    private JPanel selectTeamForm;
    private JComboBox mainMenuDropDown;
    private JLabel logo;
    private JTextField textField1;
    private JButton logoutBtn;
    private JButton button1;
    private JButton button2;
    private JLabel sectionLabel;
    private JTextField selectedSection;

    public chooseMatch() {
        setupFrame();
        setUpActions();
    }

    private void setupFrame() {
        add(SelectTeamForm1);
        setTitle("Choose Match");
        setSize(1920, 1080);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setUpActions() {
        mainMenuDropDown.addActionListener(this::switchPanel);
        logoutBtn.addActionListener(this::logout);
        button1.addActionListener(this::seatSelect);

    }

    private void seatSelect(ActionEvent actionEvent) {
        if(selectedSection.getText().isEmpty())
            JOptionPane.showMessageDialog(null,"No section selected");
        else {
            seatSelect.setSection(selectedSection.getText());
            setVisible(false);
            dispose();
            new seatSelect().setVisible(true);
        }
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
