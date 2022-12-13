package org.example.controller.todo;

import enums.Category;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import server.model.TodoItem;
import server.service.TodoItemsService;
import utility.Response;


@Path("/{name}/todolist")
public class TodoItemUpdateController {

    TodoItemsService todoListService =TodoItemsService.getInstance();
    /**
     * Accepts a todo Item
     */
    @PUT
    @Path("/{title}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTodoItem(@PathParam("name") String name, @PathParam("title") String oldTitle,
                                   TodoItem todoItem) {
        boolean updated = todoListService.updateTodoItem(name,todoItem,oldTitle);
        if(updated) {
            return new Response("ITEM WAS UPDATED SUCCESSFULLY",200);
        }
        else{
            return new Response("COULD NOT UPDATE ITEM",400);
        }
    }

    @PUT
    @Path("/{title}/fav")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addItemToFavorites(@PathParam("name") String name,@PathParam("title") String title) {
        boolean updated = todoListService.addItemToFavorite(name,title);
        if(updated) {
            return new Response("ITEM WAS ADDED TO FAVORITES SUCCESSFULLY",200);
        }
        else{
            return new Response("COULD NOT ADD ITEM TO FAVORITES",400);
        }
    }

    @PUT
    @Path("/{title}/category")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addItemToCategory(@PathParam("name") String name, @PathParam("title") String title,
                                    Category category) {
        boolean updated = todoListService.addItemToCategory(name,title,category);
        if(updated) {
            return new Response("ITEM WAS ADDED TO CATEGORY SUCCESSFULLY",200);
        }
        else{
            return new Response("COULD NOT ADD ITEM TO CATEGORY",400);
        }
    }
}
