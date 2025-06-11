import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnector {

    public static void main(String[] args) {
        
        String DB_URL = "jdbc:mysql://localhost:3306/EnzirtProjectDB";
        String DB_USER = "Hallelujah";
        String DB_PASSWORD = "Haile@23";
        String employeeIdToFind = "EMP001"; 

        try {
           
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("JDBC Driver loaded.");

           
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement pstmt = connection.prepareStatement("SELECT name, email FROM Employees WHERE id = ?")) {

                System.out.println("Connected to database. Executing query...");

                
                pstmt.setString(1, employeeIdToFind);

                try (ResultSet rs = pstmt.executeQuery()) { 
                    if (rs.next()) {
                        String name = rs.getString("name");
                        String email = rs.getString("email");
                        System.out.println("Found Employee: Name = " + name + ", Email = " + email);
                    } else {
                        System.out.println("Employee with ID " + employeeIdToFind + " not found.");
                    }
                }
            } 

            System.out.println("Database interaction complete and resources closed.");

        } catch (ClassNotFoundException e) {
            System.err.println("ERROR: Database driver not found! " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("ERROR: Database access failed! " + e.getMessage());
        }
    }
}
