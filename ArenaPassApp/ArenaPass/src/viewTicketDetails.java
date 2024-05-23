import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;


public class viewTicketDetails extends JFrame{
    private JPanel viewTicketDetailsForm;
    private JLabel qrcode;
    private JComboBox mainMenuDropDown;
    private JTextField textField1;
    private JButton logoutBtn;

    public viewTicketDetails() {
        setupFrame();
        setupActions();
    }

    private void setupFrame() {
        add(viewTicketDetailsForm);
        setTitle("ArenaPass viewTicketDetails");
        setSize(1920, 1080);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setupActions() {
        showQrCode();
    }

    private void showQrCode() {
        String path = "qr.jpg";
        try {
            Image img = ImageIO.read(new File(path));
            ImageIcon icon = new ImageIcon(img);
            qrcode.setIcon(icon);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
