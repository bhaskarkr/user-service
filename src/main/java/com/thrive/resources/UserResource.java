package com.thrive.resources;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.thrive.model.dto.User;
import com.thrive.model.request.UserCreateRequest;
import com.thrive.services.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Path("/user")
public class UserResource {

    private UserService userService;
    @Inject
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Path("/{userId}")
    public User getUser(@PathParam("userId") String id,
                        @QueryParam("allowInactive") @DefaultValue("false") boolean allowInactive) throws Exception{
        return userService.getUser(id, allowInactive);
    }

    @POST
    @Path("/")
    public User postBase(UserCreateRequest request) throws Exception{
        return userService.createUser(request);
    }
}