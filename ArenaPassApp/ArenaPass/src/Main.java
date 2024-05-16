import javax.swing.*;

public class Main extends JFrame{
    public static void main(String[] args) {
        LoginUI l = new LoginUI();
        l.setContentPane(l.LoginForm);
        l.setTitle("ArenaPass Login");
        l.setSize(1920, 1080);
        l.setVisible(true);
        l.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
