import javax.swing.*;
import java.awt.event.ActionEvent;

public class SelectMatchModify extends JFrame{
    private JPanel panel1;
    private JPanel SelectMatchModifyForm;
    private JComboBox FedAdminMenuDropDown;
    private JTextField textField1;
    private JButton logoutBtn;
    private JButton ModifyBtn;


    public SelectMatchModify(){
        setupFrame();
        setUpActions();
    }

    private void setupFrame() {
        add(SelectMatchModifyForm);
        setTitle("ArenaPass SelectMatchModifyPage");
        setSize(1920, 1080);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setUpActions() {
        GlobalMenus globalMenus = new GlobalMenus(this);
        globalMenus.dropDownFedAdmin(FedAdminMenuDropDown);
        FedAdminMenuDropDown.addActionListener(this::switchPanel);
        logoutBtn.addActionListener(this::logout);
        ModifyBtn.addActionListener(this::modify);
    }

    private void switchPanel(ActionEvent actionEvent) {
        JOptionPane.showMessageDialog(FedAdminMenuDropDown, FedAdminMenuDropDown.getSelectedItem());

        switch (FedAdminMenuDropDown.getSelectedIndex()){
            case 0:
                setVisible(false);
                dispose();
                new FederationAdminMainPage().setVisible(true);
                break;
            case 1:
                setVisible(false);
                dispose();
                new FederationAddNewMatch().setVisible(true);
                break;
            case 2:
                setVisible(false);
                dispose();
                new SelectMatchModify().setVisible(true);
                break;
            case 3:
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
    private void modify(ActionEvent actionEvent) {
        setVisible(false);
        dispose();
        new ModifyMatch().setVisible(true);
    }
}





