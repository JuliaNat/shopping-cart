package com.example.shopping_cart.viewModel;

import com.example.shopping_cart.core.entities.Cart;
import com.example.shopping_cart.core.repository.Firebase;

public class NewCartActivityViewModel {
    Firebase myDatabase = new Firebase();

    public void writeInDatabase(Cart cart) {
        myDatabase.addCartToDatabase(cart);
    }
}
