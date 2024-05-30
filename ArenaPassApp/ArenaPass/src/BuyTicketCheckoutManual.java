import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BuyTicketCheckoutManual extends JFrame{
    private JPanel panel1;
    private JLabel seatLabel;
    private JTextField fanPassField;
    private JButton checkout;
    private JComboBox ticketType;

    public BuyTicketCheckoutManual(String seatName,int seatID, Match currMatch){
        setupTicketType();
        seatLabel.setText("Seat "+seatName+" Fan Pass ID");
        setSize(600,400);
        add(panel1);
        setVisible(true);
        checkout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int fanID=Integer.valueOf(fanPassField.getText());
                    Connection connection = ConnectDB.createConnection();
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO tickets (ticket_seat_id,ticket_match_id,ticket_fan_pass_id,ticket_type) VALUES (?,?,?,?)");
                    ps.setInt(1,seatID);
                    ps.setInt(2,currMatch.getMatchID());
                    ps.setInt(3,fanID);
                    ps.setInt(4,ticketType.getSelectedIndex()+1);
                    ps.executeUpdate();
                    dispose();
                    setVisible(false);
                } catch (SQLException ee) {
                    throw new RuntimeException(ee);
                }
            }
        });
    }

    private void setupTicketType(){
        ticketType.addItem("STUDENT");
        ticketType.addItem("FULL");
        ticketType.addItem("CHILD");
        ticketType.setSelectedIndex(1);
    }


}
