import javax.swing.*;
import java.awt.event.ActionEvent;

public class SelectMatchModify extends JFrame{
    private JPanel panel1;
    private JPanel SelectMatchModifyForm;
    private JComboBox AdminMenuDropDown;
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
        AdminMenuDropDown.addActionListener(this::switchPanel);
        logoutBtn.addActionListener(this::logout);
        ModifyBtn.addActionListener(this::modify);
    }

    private void switchPanel(ActionEvent actionEvent) {
        JOptionPane.showMessageDialog(AdminMenuDropDown, AdminMenuDropDown.getSelectedItem());

        switch (AdminMenuDropDown.getSelectedIndex()){
            case 0:
                setVisible(false);
                dispose();
                new AdminMainPage().setVisible(true);
                break;
            case 1:
                setVisible(false);
                dispose();
                new SelectMatchModify().setVisible(true);
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





