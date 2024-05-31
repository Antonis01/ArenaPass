import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;


public class viewTicketDetails extends JFrame{
    private JPanel viewTicketDetailsForm;
    private JLabel qrcode;
    private JComboBox mainMenuDropDown;
    private JTextField textField1;
    private JButton logoutBtn;
    private JButton sendTicketViaEmailButton;
    private JButton downloadTicketButton;

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
        GlobalMenus globalMenus = new GlobalMenus(this);
        globalMenus.switchPanel(mainMenuDropDown);
        logoutBtn.addActionListener(globalMenus::logout);
        showQrCode();
        downloadTicketButton.addActionListener(this::downloadTicket);
    }

    private void showQrCode() {
        String path = "qr.png";
        try {
            Image img = ImageIO.read(new File(path));
            ImageIcon icon = new ImageIcon(img);
            qrcode.setIcon(icon);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void downloadTicket(ActionEvent actionEvent){
        try {
            File file = new File("\"ticket.pdf\"");
            if (!file.exists()) {
                throw new FileNotFoundException();
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int option = fileChooser.showSaveDialog(null);
            if (option == JFileChooser.APPROVE_OPTION) {
                File dir = fileChooser.getSelectedFile();
                File newFile = new File(dir, file.getName());
                Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                Desktop desktop = Desktop.getDesktop();
                desktop.open(dir);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}