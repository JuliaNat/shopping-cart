package com.example.shopping_cart.viewModel;

import com.example.shopping_cart.core.entities.Cart;
import com.example.shopping_cart.core.entities.Product;
import com.example.shopping_cart.core.repository.Firestore;

import java.util.ArrayList;

public class NewCartActivityViewModel {
    Firestore myDatabase = new Firestore();

    /**
     * Adding a new shopping cart to the cloud
     * @param cart cart to be stored in the cloud
     */
    public void writeCartInDatabase(Cart cart) {
        myDatabase.addCartToFirestore(cart);
    }

    /**
     * Adding a new product to the cloud
     * @param products product to be stored in the cloud
     */
    public void writeProductsInDatabase(ArrayList<Product> products) {
        for (Product product:products) {
            myDatabase.addProductToFirestore(product);
        }
    }
}
