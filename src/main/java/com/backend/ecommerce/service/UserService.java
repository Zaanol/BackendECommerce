package com.backend.ecommerce.service;

import com.backend.ecommerce.model.ErrorResponse;
import com.backend.ecommerce.model.SuccessResponse;
import com.backend.ecommerce.model.User;
import com.backend.ecommerce.util.JPAUtil;
import com.backend.ecommerce.util.Util;

import javax.ws.rs.*;
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

    @GET
    @Path("/list/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Object getUser(@PathParam("id") int id){
        User user = (User) JPAUtil.getObject(User.class, id);
        if(user == null){
            return new ErrorResponse(1, "User not found");
        }
        return user;
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object createUser(User user){
        try{
            if(user.getId() != null){
                return new ErrorResponse(4, "The ID must not be completed in a creation");
            }

            user.setCreationDate(Util.getCurrentDate());
            user.setUpdateDate(Util.getCurrentDate());

            JPAUtil.persist(user);
            return new SuccessResponse(user.getId(), "User created successfully");
        }catch (Exception e){
            return new ErrorResponse(2, "User creation failed");
        }
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object updateUser(User user){
        try{
            if(user.getId() == null){
                return new ErrorResponse(3, "The ID must be completed in an update");
            }

            user.setUpdateDate(Util.getCurrentDate());
            User actualUser = (User) JPAUtil.getObject(User.class, user.getId());
            user.setCreationDate(actualUser.getCreationDate());
            JPAUtil.update(user);
            return new SuccessResponse(user.getId(), "User updated successfully");
        }catch (Exception e){
            return new ErrorResponse(2, "User update failed");
        }
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Object deleteUser(@PathParam("id") int id){
        try{
            User user = (User) JPAUtil.getObject(User.class, id);

            if(user == null){
                return new ErrorResponse(1, "User not found");
            }

            JPAUtil.remove(user, id);

            return new SuccessResponse(id, "User successfully removed");

        }catch (Exception e){
            return new ErrorResponse(2, "User remove failed");
        }

    }
}