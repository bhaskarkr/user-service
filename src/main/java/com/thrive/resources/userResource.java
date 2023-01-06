package com.thrive.resources;

import com.thrive.model.dto.Base;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Path("/base")
public class userResource {


    @Inject
    public void BaseResource(){
    }

    @GET
    @Path("/get")
    public Base getBase() {
        return Base.builder().usn("afdssdafsadf").id("asdfasdf").build();
    }
}