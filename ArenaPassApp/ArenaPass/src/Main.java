import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {
        new BuyTicketNew().setVisible(true);
        //new test().setVisible(true);
        new LoginUI().setVisible(true);
    }
}
