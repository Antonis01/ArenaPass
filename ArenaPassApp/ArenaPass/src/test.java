import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class test extends JFrame {
    private JPanel testPanel;
    private JTextField textField1;
    private JButton button1;
    private JTextArea textArea1;

    public test() throws SQLException {
        add(testPanel);
        setTitle("Buy Tickets");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1920,1080);
        Connection connection = ConnectDB.createConnection();
        Statement statement = connection.createStatement();
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResultSet rs = null;
                String query = "SELECT * FROM teams WHERE team_id = ?";
                try {
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1, Integer.parseInt(textField1.getText()));
                   // ps.executeUpdate();
                    rs = ps.executeQuery();
                    while(rs.next())
                    {
                        int teamID = rs.getInt(1);
                        String teamName = rs.getString(2);
                        int stadiumID = rs.getInt(3);
                        int teamPoints = rs.getInt(4);
                        textArea1.setText(teamID + "\n" + teamName + "\n" + stadiumID + "\n" + teamPoints);
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        testPanel.setVisible(true);
    }
}
