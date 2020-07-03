package com.backend.ecommerce.service;

import com.backend.ecommerce.model.*;
import com.backend.ecommerce.util.JPAUtil;
import com.backend.ecommerce.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Path("/product")
public class ProductService {

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Object> getProducts(){
        return JPAUtil.getObjects(Product.class);
    }

    @GET
    @Path("/list/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Object getProduct(@PathParam("id") int id){
        Product product = (Product) JPAUtil.getObject(Product.class, id);
        if(product == null){
            return new ErrorResponse(1, "Product not found");
        }
        return product;
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object createProduct(Map mapProduct){
        try{
            User creationUser = (User) JPAUtil.getObject(User.class, mapProduct.get("creationUser"));

            if(creationUser == null){
                return new ErrorResponse(1, "Creation user not found!");
            }

            mapProduct.put("creationUser", creationUser);
            mapProduct.put("updateUser", creationUser);

            Product product = new ObjectMapper().convertValue(mapProduct, Product.class);
            product.setCreationDate(Util.getCurrentDate());
            product.setUpdateDate(Util.getCurrentDate());

            if(product.getId() != null){
                return new ErrorResponse(4, "The ID must not be completed in a creation");
            }

            for(Image obj : product.getImages()) JPAUtil.persist(obj);

            JPAUtil.persist(product);

            return new SuccessResponse(product.getId(), "Product created successfully");
        }catch (Exception e){
            return new ErrorResponse(2, "Product creation failed");
        }
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object updateProduct(Map mapProduct){
        try{
            User updateUser = (User) JPAUtil.getObject(User.class, mapProduct.get("updateUser"));

            if(updateUser == null){
                return new ErrorResponse(1, "Update user not found!");
            }

            mapProduct.put("updateUser", updateUser);

            Product product = new ObjectMapper().convertValue(mapProduct, Product.class);
            product.setUpdateDate(Util.getCurrentDate());

            if(product.getId() == null){
                return new ErrorResponse(3, "The ID must be completed in an update");
            }

            for(Image obj : product.getImages()) JPAUtil.update(obj);

            Product actualProduct = (Product) JPAUtil.getObject(Product.class, product.getId());
            product.setCreationDate(actualProduct.getCreationDate());
            product.setCreationUser(actualProduct.getCreationUser());
            JPAUtil.update(product);
            return new SuccessResponse(product.getId(), "Product updated successfully");
        }catch (Exception e){
            return new ErrorResponse(2, "Product update failed");
        }
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Object deleteUser(@PathParam("id") int id){
        try{
            Product product = (Product) JPAUtil.getObject(Product.class, id);

            if(product == null){
                return new ErrorResponse(1, "Product not found");
            }

            JPAUtil.remove(product, id);

            return new SuccessResponse(id, "Product successfully removed");

        }catch (Exception e){
            return new ErrorResponse(2, "Product remove failed");
        }

    }
}