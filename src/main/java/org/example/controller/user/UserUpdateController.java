package org.example.controller.user;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import server.service.UserService;
import utility.Response;

@Path("/{name}")
public class UserUpdateController {
    UserService userService = UserService.getInstance();


    /**
     * Accepts a string of the new name
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUsersName(@PathParam("name") String name, String newName) {
        boolean updated = userService.updateUsersName(name,newName.trim());
        if(updated) {
            return new Response("YOUR NAME WAS UPDATED SUCCESSFULLY "+
                    newName.trim().toUpperCase(), 200);
        }
        else{
            return new Response("COULD NOT UPDATE NAME",400);
        }
    }


}
