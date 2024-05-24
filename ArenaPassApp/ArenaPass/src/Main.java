import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws SQLException {
        new LoginUI().setVisible(true);
        new RegisterUI().setVisible(true);
    }
}