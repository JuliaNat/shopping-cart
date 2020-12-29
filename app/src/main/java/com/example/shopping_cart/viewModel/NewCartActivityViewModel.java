package com.example.shopping_cart.viewModel;

import com.example.shopping_cart.core.entities.Cart;
import com.example.shopping_cart.core.repository.Firestore;

public class NewCartActivityViewModel {
    Firestore myDatabase = new Firestore();

    public void writeInDatabase(Cart cart) {
        myDatabase.addDataToFirestore(cart);
    }
}
