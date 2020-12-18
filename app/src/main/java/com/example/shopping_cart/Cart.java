package com.example.shopping_cart;

import java.io.Serializable;

public class Cart implements Serializable {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}