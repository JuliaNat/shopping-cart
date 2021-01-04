package com.example.shopping_cart.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shopping_cart.core.entities.Cart;
import com.example.shopping_cart.core.repository.Firestore;

import java.util.ArrayList;

// import com.example.shopping_cart.core.repository.Firebase;

public class HomeActivityViewModel extends ViewModel implements Firestore.OnReadCartsComplete {
    Firestore firestore = new Firestore();
    ArrayList<Cart> allCartsFromDatabase = new ArrayList<>();
    MutableLiveData<ArrayList<Cart>> cartLiveData;

    // sobald vm erstellt wird, wird die init methode aufgerufen, welche ja alle daten holt
    public HomeActivityViewModel() {
        initializeViewModel();
    }

    public ArrayList<Cart> getAllCartsFromDatabase() {
        return allCartsFromDatabase;
    }

    public MutableLiveData<ArrayList<Cart>> getCartLiveData() {
        return cartLiveData;
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
        cartLiveData.setValue(myCartList);
    }

    public void deleteCartFromDatabase(String cartName) {
        firestore.deleteDataFromFirestore(cartName);
    }

   public void initializeViewModel() {
        cartLiveData = new MutableLiveData<>();
    }

    public void fetchAllCarts(ArrayList<Cart> localCarts) {
        firestore.gettingCartsFromFirestore(this, localCarts);
    }

    @Override
    public void getCartData(ArrayList<Cart> databaseCarts, ArrayList<Cart> localCarts) {
       allCartsFromDatabase.addAll(databaseCarts);
       localCarts.addAll(databaseCarts);
       cartLiveData.setValue(allCartsFromDatabase);
    }
}
