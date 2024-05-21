import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class seatSelect extends JFrame {

    private JPanel SelectTeamForm1;
    private JPanel selectTeamForm;
    private JComboBox mainMenuDropDown;
    private JLabel logo;
    private JTextField textField1;
    private JButton logoutBtn;
    private JButton checkoutButton;
    private JButton button1;
    private JLabel selectedSeat;
    private JLabel seatAvailability;
    private JLabel sectionNumber;
    private JPanel seatPanel;
    private static int section;

    public static void setSection(String text)
    {
        section= Integer.parseInt(text);

    }

    public seatSelect() {
        setupFrame();
        setUpActions();
        seatPanel.setLayout(new GridLayout(10, 10));
        createSeatButtons();
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedSeat.setVisible(true);
            }
        });
    }

    private void reserveSeats(ArrayList seat) {
        int temp;
        for (int i = 0; i < 100; i++) {
            temp = (Math.random() <= 0.5) ? 0 : 1;
            seat.add(temp);
            System.out.println(seat.get(i));
            //System.out.println(temp);
        }
    }

    private void createSeatButtons() {
        JButton[] jButton = new JButton[100];
        ArrayList<Integer> isFree = new ArrayList<Integer>(100);
        reserveSeats(isFree);

        for (int i = 0; i < 100; i++) {
            jButton[i] = new JButton(Integer.toString(i + 1));
            jButton[i].setPreferredSize(new Dimension(50, 50));
            seatPanel.add(jButton[i]);
            if (isFree.get(i).equals(0))
                jButton[i].setBackground(Color.RED);

            else
                jButton[i].setBackground(Color.GREEN);

            jButton[i].setForeground(Color.BLACK);
            jButton[i].setOpaque(true);

            int finalI = i;
            jButton[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (isFree.get(finalI).equals(0))
                        JOptionPane.showMessageDialog(null, "Seat Taken");
                    else {
                        selectedSeat.setText("Selected Seat: " + String.valueOf(finalI + 1));
                        selectedSeat.setVisible(true);
                        checkoutButton.setVisible(true);
                    }
                }
            });
        }

    }

    private void setupFrame() {
        add(SelectTeamForm1);
        setTitle("Select Seat");
        setSize(1920, 1080);
        sectionNumber.setText("Section "+section);
        selectedSeat.setVisible(false);
        checkoutButton.setVisible(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setUpActions() {
        mainMenuDropDown.addActionListener(this::switchPanel);
        logoutBtn.addActionListener(this::logout);
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Transaction Completed");
                new seatSelect().setVisible(false);
                new MainPage().setVisible(true);
            }
        });
    }

    private void switchPanel(ActionEvent actionEvent) {
        JOptionPane.showMessageDialog(mainMenuDropDown, mainMenuDropDown.getSelectedItem());

        switch (mainMenuDropDown.getSelectedIndex()) {
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
