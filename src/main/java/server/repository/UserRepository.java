package server.repository;

import server.connection.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserRepository {
    DBConnection dbConnection = new DBConnection();
    Connection connection;
    Statement stmt;
    private static UserRepository userRepository;
    public static UserRepository getInstance(){
        if(userRepository==null){
            userRepository=new UserRepository();
        }
        return userRepository;
    }
    private UserRepository() {
        connection = dbConnection.configureConnection();
        try {
            stmt = connection.createStatement();
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }

    public boolean createUser(String name) {
        String insertQuery = "INSERT INTO `todolist`.`user` (`name`) VALUES ('"+name+"');";
        try {
            int result = stmt.executeUpdate(insertQuery);
            return result > 0;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean updateUsersName(String name, String newName){
        String updateStatement = "UPDATE todolist.user SET name= '" + newName+
                "'\nWHERE name= '" + name + "';";

        try {
            int result = stmt.executeUpdate(updateStatement);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public ResultSet getUserNames() {
        ResultSet result = null;
        try {
            result = stmt.executeQuery(" SELECT name \n" +
                    "FROM todolist.user");
        } catch (SQLException e) {
            System.out.println(e);
        }

        return result;
    }
}
