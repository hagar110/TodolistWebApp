package org.example.controller.user;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import server.service.UserService;
import utility.Response;

@Path("/user")
public class UserCreateContoller {

    UserService userService = UserService.getInstance();

    @POST
    @Path("/createUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(@QueryParam("name") String name) {
        boolean isCreated = userService.addUser(name);
        if(isCreated)
            return new Response("User "+name+" created successfully.", 201, name);
        else
            return new Response("User "+name+" couldn't be created.", 400, name);
    }


}
