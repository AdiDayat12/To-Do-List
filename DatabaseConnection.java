import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/your_db";
    private static final String USER = "root";
    private static final String PASS = "your_Pass";

    public Connection getConnection (){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        }catch (SQLException | ClassNotFoundException e){
            System.out.println("Error : " + e.getMessage());
            return null;
        }
    }


}
