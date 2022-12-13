package client.clients;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import server.model.User;
import client.connection.APIConnection;
import utility.Response;

import java.util.ArrayList;

public class UserClient {

    private final Client client = ClientBuilder.newClient();
    private static UserClient userClient;
    private UserClient(){};
    public static UserClient getInstance(){
        if (userClient==null)
            userClient=new UserClient();
        return userClient;
    }

    public ArrayList<String> getUserNames(){
        return (ArrayList) client.target(APIConnection.REST_URI)
                .path("get").path("usernames")
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class).getItemsToBeReturned();
    }
    public utility.Response createUser(String name) {
        utility.Response response = client.target(APIConnection.REST_URI)
                .path("/user")
                .path("/createUser")
                .queryParam("name", name)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(new User(name), MediaType.APPLICATION_JSON))
                .readEntity(utility.Response.class);
        return response;
    }
    public boolean updateUsersName(String oldName, String newName){
        return client
                .target(APIConnection.REST_URI)
                .path(oldName)
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(newName, MediaType.APPLICATION_JSON))
                .readEntity(Response.class)
                .getStatusCode()/100 == 2 ;
    }
}
