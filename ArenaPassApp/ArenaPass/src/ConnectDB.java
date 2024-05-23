import java.sql.*;

public class ConnectDB {
    // JDBC URL, username, and password of MySQL server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/arenapass";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    public static Connection createConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        return connection;
    }

    public static boolean Connector(String username, String password) {
        try {
            // Establish a connection
            Connection connection = createConnection();
            if (connection != null) {
                System.out.println("Connected to the database!");
                // You can execute SQL queries here
                // Create a statement
                Statement statement = connection.createStatement();

                // Execute SQL query
                String sql = "SELECT * FROM fan_info";
                ResultSet resultSet = statement.executeQuery(sql);

                // Print results
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    if (name.equals(username) && resultSet.getString("password").equals(password)) {
                        System.out.println("Username:" + resultSet.getString("name") + " Password " + resultSet.getString("password"));
                        return true;

                    }

                }
                System.out.println("Username or password is incorrect!");
                return false;

            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
        return false;
    }

    public static String getRole(String username, String password){
        String role = "";

        try{
            Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            if (connection != null) {
                System.out.println("Connected to the database!");
                // You can execute SQL queries here
                // Create a statement
                Statement statement = connection.createStatement();

                // Execute SQL query
                String sql = "SELECT * FROM fan_info";
                ResultSet resultSet = statement.executeQuery(sql);

                // Print results
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    if (name.equals(username) && resultSet.getString("password").equals(password)) {
                        // Close resources
                        System.out.println(resultSet.getString("role"));
                        return resultSet.getString("role");

                    }

                }
                role = resultSet.getString("role");
                return role;



            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
        return role;
    }
}