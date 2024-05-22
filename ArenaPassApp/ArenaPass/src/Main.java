import javax.swing.*;
import java.util.ArrayList;

public class Main {
    ArrayList<Team> teams = new ArrayList<>(14);
    ArrayList<Match> matches = new ArrayList<>(3);

    public static void main(String[] args) {
        new LoginUI().setVisible(true);
    }
}
