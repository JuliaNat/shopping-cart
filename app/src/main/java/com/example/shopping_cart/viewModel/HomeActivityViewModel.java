package com.example.shopping_cart.viewModel;

import com.example.shopping_cart.core.entities.Cart;
import com.example.shopping_cart.core.repository.Firestore;

import java.util.ArrayList;

// import com.example.shopping_cart.core.repository.Firebase;

public class HomeActivityViewModel implements Firestore.OnReadDataComplete {
    Firestore firestore = new Firestore();
    ArrayList<Cart> allCartsFromDatabase = new ArrayList<>();

    // sobald vm erstellt wird, wird die init methode aufgerufen, welche ja alle daten holt
    public HomeActivityViewModel() {
        initializeViewModel();
    }

    public ArrayList<Cart> getAllCartsFromDatabase() {
        return allCartsFromDatabase;
    }

    public void updateOrCreateCartList(ArrayList<Cart> myCartList, Cart shoppingCart) {
        ArrayList<String> ids = new ArrayList<>();

        for (Cart cart : myCartList) {
            ids.add(cart.cartID);
        }

        if (ids.contains(shoppingCart.cartID)) {
            for (int i = 0; i < ids.size(); i++) {
                if (ids.get(i).equals(shoppingCart.cartID)) {
                    myCartList.remove(i);
                    myCartList.add(shoppingCart);
                }
            }
        } else {
            myCartList.add(shoppingCart);
        }
    }

   public void initializeViewModel() {
        // Jetzt hab ich alle Daten von der Datenbank!
        firestore.gettingDataFromFirestore(this);
    }

    @Override
    public void getCartData(ArrayList<Cart> databaseCarts) {
       allCartsFromDatabase.addAll(databaseCarts);
    }
}
