package server.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
     Connection connection;
    DBConfig db = new DBConfig();

    public  Connection configureConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(db.getUrl() + db.getDbName(), db.getDbUser() , db.getPassword());
        } catch (ClassNotFoundException | SQLException c) {
            System.out.println(c);
        }

        return connection;
    }


}
