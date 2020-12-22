package com.example.shopping_cart;

import java.io.Serializable;
import java.util.ArrayList;

public class Cart implements Serializable {
    String cartID;
    String name;

    public ArrayList<Product> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(ArrayList<Product> cartProducts) {
        this.cartProducts = cartProducts;
    }

    ArrayList<Product> cartProducts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}