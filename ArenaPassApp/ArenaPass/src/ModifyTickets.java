import javax.swing.*;
import java.awt.event.ActionEvent;

public class ModifyTickets extends JFrame{
    private JPanel panel1;
    private JButton logoutBtn;
    private JComboBox AdminMenuDropDown;
    private JTextField textField1;
    private JTextField textField3;
    private JTextField textField2;
    private JComboBox comboBox2;
    private JTextField textField4;
    private JButton EDITButton3;
    private JButton EDITButton2;
    private JButton EDITButton1;
    private JTextField SETAVAILABLETICKETSHOMETextField;
    private JButton EDITButton;
    private JTextField textField5;
    private JButton EDITButton4;
    private JTextField textField6;
    private JButton EDITButton5;


    public ModifyTickets(){
        setupFrame();
        setUpActions();

    }
    private void setupFrame() {
        add(panel1);
        setTitle("ArenaPass ModifyTicketsPage");
        setSize(1920, 1080);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setUpActions() {
        AdminMenuDropDown.addActionListener(this::switchPanel);
        logoutBtn.addActionListener(this::logout);
    }


    private void switchPanel(ActionEvent actionEvent) {
        JOptionPane.showMessageDialog(AdminMenuDropDown, AdminMenuDropDown.getSelectedItem());

        switch (AdminMenuDropDown.getSelectedIndex()){
            case 0:
                setVisible(false);
                dispose();
                new FederationAdminMainPage().setVisible(true);
                break;
            case 1:
                setVisible(false);
                dispose();
                new SelectMatchModify().setVisible(true);
                break;
            case 2:
                setVisible(false);
                dispose();
                new ModifyTickets().setVisible(true);
                break;

        }
    }

    private void logout(ActionEvent actionEvent) {
        setVisible(false);
        dispose();
        new LoginUI().setVisible(true);
    }

}
