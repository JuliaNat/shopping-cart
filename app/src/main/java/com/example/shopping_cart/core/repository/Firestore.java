package com.example.shopping_cart.core.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.shopping_cart.core.entities.Cart;
import com.example.shopping_cart.core.entities.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class Firestore {
    FirebaseFirestore firestoreDatabase = FirebaseFirestore.getInstance();

    public void addCartToFirestore(Cart cart) {
        firestoreDatabase.collection("carts").document(cart.name)
                .set(cart)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);

                    }
                });
    }

    public void addProductToFirestore(Product product) {
        firestoreDatabase.collection("products").document(product.name)
                .set(product)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);

                    }
                });
    }

    public void gettingCartsFromFirestore(final OnReadCartsComplete callbackOnRead, final ArrayList<Cart> localCarts) {
        final ArrayList<Cart> allCartsFromDatabase = new ArrayList<>();
        firestoreDatabase.collection("carts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc:task.getResult()) {
                        allCartsFromDatabase.add(doc.toObject(Cart.class));
                    }
                }
                callbackOnRead.getCartData(allCartsFromDatabase, localCarts);
            }
        });
    }

    public void gettingProductsFromFirestore(final OnReadProductsComplete callbackOnRead, final ArrayList<Product> localProducts) {
        final ArrayList<Product> allProductsFromDatabase = new ArrayList<>();
        firestoreDatabase.collection("products").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc:task.getResult()) {
                        allProductsFromDatabase.add(doc.toObject(Product.class));
                    }
                }
                callbackOnRead.getProductData(allProductsFromDatabase, localProducts);
            }
        });
    }

    public void deleteDataFromFirestore(String cartName) {
        firestoreDatabase.collection("carts").document(cartName)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }

    // Callback methods declared
    public interface OnReadCartsComplete {
        void getCartData(ArrayList<Cart> databaseCarts, ArrayList<Cart> localCarts);
    }

    public interface OnReadProductsComplete {
        void getProductData(ArrayList<Product> databaseProducts, ArrayList<Product> localProducts);
    }


}
