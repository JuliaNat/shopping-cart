package com.example.shopping_cart.viewModel;

import com.example.shopping_cart.core.entities.Cart;
import com.example.shopping_cart.core.entities.Product;
import com.example.shopping_cart.core.repository.Firestore;

import java.util.ArrayList;

public class NewCartActivityViewModel {
    Firestore myDatabase = new Firestore();

    public void writeCartInDatabase(Cart cart) {
        myDatabase.addCartToFirestore(cart);
    }

    public void writeProductsInDatabase(ArrayList<Product> products) {
        for (Product product:products) {
            myDatabase.addProductToFirestore(product);
        }
    }
}
