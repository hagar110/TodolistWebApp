package org.example.controller.user;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import server.service.UserService;
import server.model.TodoItem;
import server.repository.TodoItemsRepository;
import server.service.TodoItemsService;
import utility.Response;
import utility.Utils;

import java.util.ArrayList;

@Path("get")
public class userOperationsController {

    TodoItemsRepository repository = TodoItemsRepository.getInstance();
    TodoItemsService todoItemsService=TodoItemsService.getInstance();

    UserService userService = UserService.getInstance();
    Utils utils = new Utils();

    @GET
    @Path("useritems")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUserItems(@QueryParam("username") String username){
        ArrayList<TodoItem> todoItems=todoItemsService.getTodosFromDB(repository.getUserTodos(username));
        if (todoItems.isEmpty())
            return new Response("no items found for this user",204, new ArrayList<String>());
        return new Response("OK",200,utils.convertItemsToJson(todoItems));
    }

    @GET
    @Path("userlatest")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUserLatestItems(@QueryParam("username") String username){
        ArrayList<TodoItem> todoItems=todoItemsService.getTodosFromDB(repository.getUserLatestTodos(username));
        if (todoItems.isEmpty())
            return new Response("no items found for this user",204, new ArrayList<String>());
        return new Response("OK",200, utils.convertItemsToJson(todoItems));
    }

    @GET
    @Path("userfavorites")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUserFavorites(@QueryParam("username") String username){
        ArrayList<TodoItem> todoItems=todoItemsService.getTodosFromDB(repository.getFavorites(username));
        if (todoItems.isEmpty())
            return new Response("no items found for this user",204, new ArrayList<String>());
        return new Response("OK",200,utils.convertItemsToJson(todoItems));
    }


    @GET
    @Path("usernames")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUserNames(){
        ArrayList<String> usernames = userService.getUserNames();

        if(usernames.isEmpty())
            return new Response("no users found", 204, usernames);
        return new Response("OK", 200, usernames);
    }




}
