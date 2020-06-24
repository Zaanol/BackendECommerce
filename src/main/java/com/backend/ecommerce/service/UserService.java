package com.backend.ecommerce.service;

import com.backend.ecommerce.model.User;
import com.backend.ecommerce.util.JPAUtil;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/user")
public class UserService {

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Object> getUsers(){
        return JPAUtil.getObjects(User.class);
    }
}
