package com.example.shopping_cart.viewModel;

import com.example.shopping_cart.core.entities.Cart;

import java.util.ArrayList;

public class HomeActivityViewModel {

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

}
