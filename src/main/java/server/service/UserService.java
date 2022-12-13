package server.service;

import server.repository.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserService {
    private final UserRepository userRepository;
    private static UserService userService;
    public static UserService getInstance(){
        if(userService==null){
            userService=new UserService();
        }
        return userService;
    }

    private UserService() {
        this.userRepository = UserRepository.getInstance();

    }

    public boolean addUser(String name) {
        return userRepository.createUser(name);
    }

    public boolean updateUsersName(String name, String newName){
        boolean updated = userRepository.updateUsersName(name,newName);
        if(updated){
            return true;
        }
        return false;
    }

    public ArrayList<String> getUserNames(){
        ResultSet result = userRepository.getUserNames();

        ArrayList<String> usernames = new ArrayList<>();

        try {
            while (result.next())
                usernames.add(result.getString(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usernames;
    }
}
