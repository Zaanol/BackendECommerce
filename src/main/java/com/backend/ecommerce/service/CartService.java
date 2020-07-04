package com.backend.ecommerce.service;

import com.backend.ecommerce.model.*;
import com.backend.ecommerce.util.JPAUtil;
import com.backend.ecommerce.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

@Path("/cart")
public class CartService {

    @GET
    @Path("/list/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Object getCart(@PathParam("id") Integer id){
        Cart cart = (Cart) JPAUtil.getObject(Cart.class, id);
        if(cart == null){
            return new ErrorResponse(1, "Empty cart");
        }
        return cart;
    }

    @POST
    @Path("/add/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object addProduct(@PathParam("id") Integer id, Map mapProduct){
        try{
            User user = (User) JPAUtil.getObject(User.class, id);

            if(user == null){
                return new ErrorResponse(1, "User not found!");
            }

            Product product = (Product) JPAUtil.getObject(Product.class, mapProduct.get("product"));

            if(product == null){
                return new ErrorResponse(1, "Product not found!");
            }

            CartProduct cartProduct = new ObjectMapper().convertValue(mapProduct, CartProduct.class);
            Cart cart = (Cart) JPAUtil.getObject(Cart.class, id);

            if(cart == null){
                cart = new Cart();
                cart.setUser(user.getId());
                JPAUtil.persist(cart);
            }

            CartProduct registeredProduct = cart.getProduct((Integer) mapProduct.get("product"));
            if(registeredProduct != null){
                registeredProduct.setAmount(registeredProduct.getAmount() + (Integer) mapProduct.get("amount"));
                JPAUtil.update(registeredProduct);

                return new SuccessResponse(cart.getUser(), "Product added successfully");
            }

            JPAUtil.persist(cartProduct);
            cart.addProduct(cartProduct);
            JPAUtil.update(cart);

            return new SuccessResponse(cart.getUser(), "Product added successfully");
        }catch (Exception e){
            e.printStackTrace();
            return new ErrorResponse(2, "Error adding product");
        }
    }

    @POST
    @Path("/delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object deleteProduct(@PathParam("id") Integer id, Map mapProduct){
        try{
            User user = (User) JPAUtil.getObject(User.class, id);

            if(user == null){
                return new ErrorResponse(1, "User not found!");
            }

            Product product = (Product) JPAUtil.getObject(Product.class, mapProduct.get("product"));

            if(product == null){
                return new ErrorResponse(1, "Product not found!");
            }

            Cart cart = (Cart) JPAUtil.getObject(Cart.class, id);

            if(cart == null){
                return new ErrorResponse(1, "Cart for this user not found");
            }

            CartProduct cartProduct = cart.getProduct((Integer) mapProduct.get("product"));

            if(cartProduct == null){
                return new ErrorResponse(1, "Product for this cart not found");
            }

            for(CartProduct cProduct : cart.getProducts()){
                if(cProduct.getProduct() == cartProduct.getProduct() && (Integer) mapProduct.get("amount") < cProduct.getAmount()){
                    cProduct.setAmount(cProduct.getAmount() - (Integer) mapProduct.get("amount"));
                    JPAUtil.update(cProduct);

                    return new SuccessResponse(cart.getUser(), "Product successfully removed");
                }
            }

            cart.removeProduct(cartProduct);
            JPAUtil.update(cart);

            return new SuccessResponse(cart.getUser(), "Product successfully removed");
        }catch (Exception e){
            e.printStackTrace();
            return new ErrorResponse(2, "Error removing product");
        }
    }

    @GET
    @Path("/clear/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Object clearCart(@PathParam("id") Integer id){
        try{
            User user = (User) JPAUtil.getObject(User.class, id);

            if(user == null){
                return new ErrorResponse(1, "User not found!");
            }

            Cart cart = (Cart) JPAUtil.getObject(Cart.class, id);

            if(cart == null){
                return new ErrorResponse(1, "The cart is already clean");
            }

            cart.setProducts(null);
            JPAUtil.update(cart);

            return new SuccessResponse(cart.getUser(), "Products successfully removed");
        }catch (Exception e){
            e.printStackTrace();
            return new ErrorResponse(2, "Error removing products");
        }
    }
}