package server.repository;


import enums.Category;
import server.connection.DBConnection;
import server.model.TodoItem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TodoItemsRepository {
    DBConnection dbConnection = new DBConnection();
    Connection connection;
    Statement stmt;
    private static TodoItemsRepository todoRepository;
    public static TodoItemsRepository getInstance(){
        if (todoRepository==null){
            todoRepository=new TodoItemsRepository();
        }
        return todoRepository;
    }

    private TodoItemsRepository() {
        connection = dbConnection.configureConnection();
        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public boolean createUserTodo(String name, TodoItem item){
        java.sql.Date convertedStartDate = new java.sql.Date(item.getStartDate().getTime());
        java.sql.Date convertedEndDate = new java.sql.Date(item.getEndDate().getTime());
        String insertQuery = "INSERT INTO `todolist`.`todoitem` (`title`, `description`, `priority`, `category`," +
                "`startDate`, `endDate`, `isFavorite`, `userId`)" +
                "VALUES ('"+item.getTitle()+"'," +
                "'"+item.getDescription()+"'," +
                "'"+item.getPriority()+"'," +
                "'"+item.getCategory()+"'," +
                "'"+convertedStartDate+"'," +
                "'"+convertedEndDate+"'," +
                "0, "+
                "(SELECT iduser FROM `todolist`.`user` WHERE name = '"+name+"'));";
        try {
            int result = stmt.executeUpdate(insertQuery);
            return result > 0;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public ResultSet searchByTitle(String username,String title) {
        ResultSet result = null;
        try {
            result = stmt.executeQuery("SELECT t.title, t.description, " +
                    "t.priority, t.category, t.startDate, t.endDate, t.isFavorite \n" +
                    "FROM todolist.user as u LEFT OUTER JOIN todolist.todoitem as t\n" +
                    "ON t.userId = u.iduser \n " +
                    "WHERE u.name = '" + username + "'"+" and t.title= '"+title+"'");

        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }
    public ResultSet searchByStartDate(String username,String startDate) {
        ResultSet result = null;
        try {
            result = stmt.executeQuery("SELECT t.title, t.description, " +
                    "t.priority, t.category, t.startDate, t.endDate, t.isFavorite \n" +
                    "FROM todolist.user as u LEFT OUTER JOIN todolist.todoitem as t\n" +
                    "ON t.userId = u.iduser \n " +
                    "WHERE u.name = '" + username + "'"+" and t.startDate= '"+startDate+"'");

        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }
    public ResultSet searchByEndDate(String username,String endDate) {
        ResultSet result = null;
        try {
            result = stmt.executeQuery("SELECT t.title, t.description, " +
                    "t.priority, t.category, t.startDate, t.endDate, t.isFavorite \n" +
                    "FROM todolist.user as u LEFT OUTER JOIN todolist.todoitem as t\n" +
                    "ON t.userId = u.iduser \n " +
                    "WHERE u.name = '" + username + "'"+" and t.endDate= '"+endDate+"'");

        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }
    public ResultSet searchByPriority(String username,String priority) {
        ResultSet result = null;
        try {
            result = stmt.executeQuery("SELECT t.title, t.description, " +
                    "t.priority, t.category, t.startDate, t.endDate, t.isFavorite \n" +
                    "FROM todolist.user as u LEFT OUTER JOIN todolist.todoitem as t\n" +
                    "ON t.userId = u.iduser \n " +
                    "WHERE u.name = '" + username + "'"+" and t.priority= '"+priority+"'");

        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }

    public ResultSet getFavorites(String username) {
        ResultSet result = null;
        try {
            result = stmt.executeQuery("SELECT t.title, t.description, " +
                    "t.priority, t.category, t.startDate, t.endDate, t.isFavorite \n" +
                    "FROM todolist.user as u JOIN todolist.todoitem as t\n" +
                    "ON t.userId = u.iduser \n " +
                    "WHERE u.name = '" + username + "'"+" and t.isFavorite= 1");

        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }
    public ResultSet getUserTodos(String username) {
        ResultSet result = null;
        try {
            result = stmt.executeQuery("SELECT t.title, t.description, " +
                    "t.priority, t.category, t.startDate, t.endDate, t.isFavorite \n" +
                    "FROM todolist.user as u LEFT OUTER JOIN todolist.todoitem as t\n" +
                    "ON t.userId = u.iduser \n " +
                    "WHERE u.name = '" + username + "'");

        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;
    }

    public ResultSet getUserLatestTodos(String username) {
        ResultSet result = null;
        try {
            result = stmt.executeQuery("SELECT t.title, t.description, t.priority, t.category, t.startDate, t.endDate, t.isFavorite\n" +
                    "FROM todolist.user as u LEFT OUTER JOIN todolist.todoitem as t\n" +
                    "ON t.userId = u.iduser\n" +
                    "WHERE u.name = '" + username + "'\n" +
                    "ORDER BY t.endDate\n" +
                    "LIMIT 5");

        } catch (SQLException e) {
            System.out.println(e);
        }
        return result;

    }

    public boolean deleteTodoItem(String title, String name) {
        String subQuery = "(SELECT iduser FROM todolist.user where name= '"+name+"')";
        String sqlQuery = "DELETE FROM todolist.todoitem " +
                "WHERE title = '" + title + "' AND userid = " + subQuery;
        try {
            int result = stmt.executeUpdate(sqlQuery);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateTodoItem(String name,TodoItem item, String oldTitle) {
        String idSubQuery = "(SELECT iduser FROM todolist.user where name= '"+name+"')";
        java.sql.Date sqlStartDate = new java.sql.Date(item.getStartDate().getTime());
        java.sql.Date sqlEndDate = new java.sql.Date(item.getEndDate().getTime());
        String updateStatement = "UPDATE todolist.todoitem SET title = '" + item.getTitle() +
                "' ,description= '" + item.getDescription() +
                "' ,priority= '" + item.getPriority() +
                "' ,category= '" + item.getCategory() +
                "' ,startDate= '" + sqlStartDate +
                "' ,endDate= '" + sqlEndDate+
                "'\nWHERE userId= " + idSubQuery + " AND title= '" + oldTitle + "';";

        try {
            int result = stmt.executeUpdate(updateStatement);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
    public boolean addItemToFavorite(String name,String title){
        String idSubQuery = "(SELECT iduser FROM todolist.user where name= '"+name+"')";
        String updateStatement = "UPDATE todolist.todoitem SET `isFavorite` = 1"+
                "\nWHERE userId= " + idSubQuery + " AND title= '" + title + "';";

        try {
            int result = stmt.executeUpdate(updateStatement);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addItemToCategory(String name,String title, Category category){
        String idSubQuery = "(SELECT iduser FROM todolist.user where name= '"+name+"')";

        String updateStatement = "UPDATE todolist.todoitem SET category= '" + category+
                "'\nWHERE userId= " + idSubQuery + " AND title= '" + title + "';";

        try {
            int result = stmt.executeUpdate(updateStatement);
            return result > 0;
        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }

    }


}

