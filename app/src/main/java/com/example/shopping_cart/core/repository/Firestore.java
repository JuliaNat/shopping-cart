package com.example.shopping_cart.core.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.shopping_cart.core.entities.Cart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class Firestore {
    FirebaseFirestore firestoreDatabase = FirebaseFirestore.getInstance();
    CollectionReference collectionReference = firestoreDatabase.collection("carts");

    public void addDataToFirestore(Cart cart) {
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

    public void gettingDataFromFirestore(final OnReadDataComplete callbackOnRead) {
        final ArrayList<Cart> allCartsFromDatabase = new ArrayList<>();
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc:task.getResult()) {
                        allCartsFromDatabase.add(doc.toObject(Cart.class));
                    }
                }
                callbackOnRead.getCartData(allCartsFromDatabase);
            }
        });
    }

    public void deleteDataFromFirestore(String cartName) {
        collectionReference.document(cartName)
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
    // Callback method declared
    public interface OnReadDataComplete {
        void getCartData(ArrayList<Cart> databaseCarts);
    }
}
