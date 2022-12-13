package org.example.controller.todo;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import server.model.TodoItem;
import server.service.TodoItemsService;
import utility.Response;

@Path("/todolist")
public class TodoItemCreateController {

    TodoItemsService todoItemsService = TodoItemsService.getInstance();

    @POST
    @Path("/createItem")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTodoItem(@QueryParam("name") String name, TodoItem todoItem) {
        boolean isCreated = todoItemsService.addTodoItem(name,todoItem);
        if(isCreated)
            return new Response("Item created successfully.", 201);
        else
            return new Response("Item couldn't be created.", 400);
    }
}
