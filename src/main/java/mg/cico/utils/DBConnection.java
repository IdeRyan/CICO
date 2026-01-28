package mg.cico.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection startConnection(){
        String url = "jdbc:postgresql://localhost:5432/CICO";
        String username = "ryan";
        try {
            Connection c = DriverManager.getConnection(url, username , "");
            return c;
        }
        catch (SQLException e) {
            System.out.println("Error while trying to connect to database: "+e.getMessage());
            return null;
        }

        
    }
}
