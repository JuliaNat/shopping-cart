package com.example.shopping_cart.core.repository;

import com.example.shopping_cart.core.entities.Cart;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Firebase {
    // Initialize database reference
    DatabaseReference myDatabase = FirebaseDatabase.getInstance().getReference();


    public void addCartToDatabase(Cart cart) {
        // Necessary for displaying a cart even if it's empty
        myDatabase.child(cart.name).setValue(cart.name);

        myDatabase.child(cart.name).child("products").setValue(cart.cartProducts);
    }
}
