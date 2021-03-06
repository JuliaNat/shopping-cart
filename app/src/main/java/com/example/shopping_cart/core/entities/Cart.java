package com.example.shopping_cart.core.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Cart implements Serializable {
    public String cartID;
    public String name;
    public ArrayList<Product> cartProducts;

    public String getCartID() {
        return cartID;
    }

    public void setCartID(String cartID) {
        this.cartID = cartID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Product> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(ArrayList<Product> cartProducts) {
        this.cartProducts = cartProducts;
    }
}