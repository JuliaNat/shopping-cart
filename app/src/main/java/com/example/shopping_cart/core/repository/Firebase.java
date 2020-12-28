package com.example.shopping_cart.core.repository;

import androidx.annotation.NonNull;

import com.example.shopping_cart.core.entities.Cart;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Firebase {
    // Initialize database reference
    DatabaseReference myDatabase = FirebaseDatabase.getInstance().getReference();

    private ArrayList<Cart> databaseCarts = new ArrayList<>();
    ArrayList<String> keys = new ArrayList<>();

    public void addCartToDatabase(Cart cart) {
        myDatabase.child(cart.name).child("name").setValue(cart.name);
        myDatabase.child(cart.name).child("cartID").setValue(cart.cartID);
        myDatabase.child(cart.name).child("products").setValue(cart.cartProducts);
    }

    public void readDataFromDatabase() {
        myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseCarts.clear();
                for(DataSnapshot keyNode:snapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Cart cart = keyNode.getValue(Cart.class);
                    databaseCarts.add(cart);
                    // DataSnapshot { key = Ente, value = {cartID=2a9acbd1-bb17-4d7f-911c-9f6125574d24, name=Ente, products={0={nutrition=Stück, name=Apfel, weight=2}}} }
                    // System.out.println("++++++++++*:" + keyNode);

                    // [Ente, Gans]
                    // System.out.println("++++++++++*:" + keys);

                    // [com.example.shopping_cart.core.entities.Cart@89a80cb, com.example.shopping_cart.core.entities.Cart@508e0a8]
                    // System.out.println("++++++++++*:" + databaseCarts);
                }
            }

            // TODO improve error handling
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Database reading error - onCancelled!");
            }
        });
    }
}
