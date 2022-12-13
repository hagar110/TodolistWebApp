package client.clients;

import client.connection.APIConnection;
import enums.Category;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import server.model.TodoItem;
import utility.Response;
import utility.Utils;

import java.util.ArrayList;
import java.util.List;


public class TodoListClient {

   private final Utils utils = new Utils();
   private static TodoListClient todoListClient;
   private TodoListClient(){}
    public static TodoListClient getInstance(){
       if(todoListClient==null){
           todoListClient=new TodoListClient();
       }
       return todoListClient;
    }

    private final Client client = ClientBuilder.newClient();

    public ArrayList<TodoItem> get(String username, String endpoint){
        ArrayList arrayList = (ArrayList) client.target(APIConnection.REST_URI)
                .path("get").path(endpoint).queryParam("username", username)
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class).getItemsToBeReturned();

        return utils.jsonToTodos(arrayList);
    }


    public List<TodoItem> SearchByTitle(String username, String searchValue)  {
        utility.Response response = client
                .target(APIConnection.REST_URI+"/searchBy/Title?username="+username+"&searchValue="+searchValue)
                .request(MediaType.APPLICATION_JSON)
                .get().readEntity(utility.Response.class);
        List<TodoItem> result=utils.jsonToTodos(
                (ArrayList<String>) response.getItemsToBeReturned());
        if (result==null||result.isEmpty())
            System.out.println(response.getMessage());
        return result;
    }
    public List<TodoItem> SearchByPriority(String username, String searchValue)  {
        utility.Response response = client
                .target(APIConnection.REST_URI+"/searchBy/Priority?username="+username+"&searchValue="+searchValue)
                .request(MediaType.APPLICATION_JSON)
                .get().readEntity(utility.Response.class);
        List<TodoItem> result=utils.jsonToTodos(
                (ArrayList<String>) response.getItemsToBeReturned());
        if (result==null||result.isEmpty())
            System.out.println(response.getMessage());
        return result;
    }
    public List<TodoItem> SearchByStartDate(String username, String searchValue)  {
        utility.Response response = client
                .target(APIConnection.REST_URI+"/searchBy/StartDate?username="+username+"&searchValue="+searchValue)
                .request(MediaType.APPLICATION_JSON)
                .get().readEntity(utility.Response.class);
        List<TodoItem> result=utils.jsonToTodos(
                (ArrayList<String>) response.getItemsToBeReturned());
        if (result==null||result.isEmpty())
            System.out.println(response.getMessage());
        return result;
    }
    public List<TodoItem> SearchByEndDate(String username, String searchValue)  {
        utility.Response response = client
                .target(APIConnection.REST_URI+"/searchBy/EndDate?username="+username+"&searchValue="+searchValue)
                .request(MediaType.APPLICATION_JSON)
                .get().readEntity(utility.Response.class);
        List<TodoItem> result=utils.jsonToTodos(
                (ArrayList<String>) response.getItemsToBeReturned());
        if (result==null||result.isEmpty())
            System.out.println(response.getMessage());
        return result;
    }
    public boolean updateTodoItem(String name,TodoItem item, String oldTitle){
        return client
                .target(APIConnection.REST_URI)
                .path(name)
                .path("todolist")
                .path(oldTitle)
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(item, MediaType.APPLICATION_JSON))
                .readEntity(Response.class)
                .getStatusCode()/100 == 2;
    }
    public boolean addItemToCategory(String name , String title, Category category){
        return client
                .target(APIConnection.REST_URI)
                .path(name)
                .path("todolist")
                .path(title)
                .path("category")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(category, MediaType.APPLICATION_JSON))
                .readEntity(Response.class)
                .getStatusCode()/100 == 2 ;
    }
    public boolean addItemToFavorites(String name , String title){
        return client
                .target(APIConnection.REST_URI)
                .path(name)
                .path("todolist")
                .path(title)
                .path("fav")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity("true", MediaType.APPLICATION_JSON))
                .readEntity(Response.class)
                .getStatusCode()/100 == 2 ;
    }
    public utility.Response deleteTodoItem(String name, String title) {
        utility.Response response = client.target(APIConnection.REST_URI)
                .path("/todolist")
                .path("/deleteItem")
                .path(title)
                .queryParam("name", name)
                .request(MediaType.APPLICATION_JSON)
                .delete()
                .readEntity(utility.Response.class);
        return response;
    }
    public utility.Response createTodoItem(String name, TodoItem todoItem) {
        utility.Response statusCode = client.target(APIConnection.REST_URI)
                .path("/todolist")
                .path("/createItem")
                .queryParam("name", name)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(todoItem, MediaType.APPLICATION_JSON))
                .readEntity(utility.Response.class);
        return statusCode;
    }
}
