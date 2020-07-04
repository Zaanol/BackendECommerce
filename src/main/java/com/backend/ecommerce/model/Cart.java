package com.backend.ecommerce.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart implements Serializable {

    @Id
    private Integer user;

    @OneToMany(fetch = FetchType.EAGER)
    private List<CartProduct> products = new ArrayList<>();

    public Cart(){}

    public Cart(Integer user, List<CartProduct> products) {
        this.user = user;
        this.products = products;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public List<CartProduct> getProducts() {
        return products;
    }

    public CartProduct getProduct(Integer idProduct) {
        for(CartProduct product : this.products){
            if(product.getProduct() == idProduct) return product;
        }

        return null;
    }

    public void setProducts(List<CartProduct> products) {
        this.products = products;
    }

    public void addProduct(CartProduct cartProduct){
        this.products.add(cartProduct);
    }

    public void removeProduct(CartProduct cartProduct){
        this.products.remove(cartProduct);
    }
}