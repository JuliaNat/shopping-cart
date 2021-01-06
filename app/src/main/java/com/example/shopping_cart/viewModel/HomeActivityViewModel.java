package com.example.shopping_cart.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shopping_cart.core.entities.Cart;
import com.example.shopping_cart.core.repository.Firestore;

import java.util.ArrayList;

public class HomeActivityViewModel extends ViewModel implements Firestore.OnReadCartsComplete {
    Firestore firestore = new Firestore();
    ArrayList<Cart> allCartsFromDatabase = new ArrayList<>();
    MutableLiveData<ArrayList<Cart>> cartLiveData;

    /**
     * Called when the ViewModel is created
     */
    public HomeActivityViewModel() {
        initializeViewModel();
    }


    /**
     * For observing changes and updating afterwards. Receiving the live data.
     * @return list of carts
     */
    public MutableLiveData<ArrayList<Cart>> getCartLiveData() {
        return cartLiveData;
    }

    /**
     * Updates cart or create new if not existing yet. Check by random generated UUID
     *
     * @param myCartList  list existing so far
     * @param currentCart currently edited or created cart
     */
    public void updateOrCreateCartList(ArrayList<Cart> myCartList, Cart currentCart) {
        ArrayList<String> ids = new ArrayList<>();

        for (Cart cart : myCartList) {
            ids.add(cart.cartID);
        }

        if (ids.contains(currentCart.cartID)) {
            for (int i = 0; i < ids.size(); i++) {
                if (ids.get(i).equals(currentCart.cartID)) {
                    myCartList.remove(i);
                    myCartList.add(currentCart);
                }
            }
        } else {
            myCartList.add(currentCart);
        }
        cartLiveData.setValue(myCartList);
    }

    /**
     * Communication channel between activity and cloud
     *
     * @param cartName which cart is to be deleted
     */
    public void deleteCartFromDatabase(String cartName) {
        firestore.deleteDataFromFirestore(cartName);
    }

    /**
     * Creates new MutableLiveData on initialization of the ViewModel
     */
    public void initializeViewModel() {
        cartLiveData = new MutableLiveData<>();
    }

    /**
     * Fetching all carts from the Cloud Firestore
     * @param localCarts List to be written to
     */
    public void fetchAllCarts(ArrayList<Cart> localCarts) {
        firestore.gettingCartsFromFirestore(this, localCarts);
    }

    /**
     * When the callback is executed, all the shopping carts in the database are written to the variables provided for this purpose
     * @param databaseCarts carts from the database to process them
     * @param localCarts carts from the database to be able to use them in the recycler view
     */
    @Override
    public void getCartData(ArrayList<Cart> databaseCarts, ArrayList<Cart> localCarts) {
        allCartsFromDatabase.addAll(databaseCarts);
        localCarts.addAll(databaseCarts);
        cartLiveData.setValue(allCartsFromDatabase);
    }
}
