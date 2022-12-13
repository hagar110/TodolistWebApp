package client.connection;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;

public class APIConnection {
    public static final String REST_URI
            = "http://localhost:8080/TodoListWebApp/webapi";
    public static Client client = ClientBuilder.newClient();
}
