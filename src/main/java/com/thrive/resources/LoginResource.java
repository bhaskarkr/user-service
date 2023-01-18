package com.thrive.resources;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.thrive.model.dto.User;
import com.thrive.model.request.LoginRequest;
import com.thrive.model.response.LoginResponse;
import com.thrive.services.SessionService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Path("/login")
public class LoginResource {
    private SessionService sessionService;
    @Inject
    public LoginResource(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @POST
    @Path("/")
    public LoginResponse getUser(LoginRequest loginRequest) throws Exception {
        return sessionService.login(loginRequest);
    }
}
