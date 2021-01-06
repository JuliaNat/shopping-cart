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

    /**
     * Adding shopping carts to the cloud under the collection "carts". The name of the document is set as the name of the shopping cart.
     * @param cart the cart to be stored in cloud firestore
     */
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

    /**
     * Adding products to the cloud under the collection "products". The name of the document is set as the name of the product.
     * @param product the product to be stored in cloud firestore
     */
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

    /**
     * Loading of all shopping carts from the cloud to display them in the app
     * @param callbackOnRead callback function to return the results when they are retrieved
     * @param localCarts array to cache the data from the database locally
     */
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

    /**
     * Loading of all products from the cloud to display them in the app
     * @param callbackOnRead callback function to return the results when they are needed
     * @param localProducts array to cache the data from the database locally
     */
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

    /**
     * Delete a shopping cart from the cloud
     * @param cartName cart to be deleted
     */
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

    /**
     * Interface declaration for the cart callback method
     */
    public interface OnReadCartsComplete {
        void getCartData(ArrayList<Cart> databaseCarts, ArrayList<Cart> localCarts);
    }

    /**
     * Interface declaration for the product callback method
     */
    public interface OnReadProductsComplete {
        void getProductData(ArrayList<Product> databaseProducts, ArrayList<Product> localProducts);
    }


}
