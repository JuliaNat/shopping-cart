package com.example.shopping_cart.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping_cart.R;
import com.example.shopping_cart.core.entities.Cart;
import com.example.shopping_cart.ui.adapter.ShoppingCartRecyclerViewAdapter;
import com.example.shopping_cart.viewModel.HomeActivityViewModel;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity implements ShoppingCartRecyclerViewAdapter.OnCartClickListener, ShoppingCartRecyclerViewAdapter.OnCanClickListener {
    int LAUNCH_NEW_CART_ACTIVITY = 1;
    Context context;
    RecyclerView myRecyclerView;
    ArrayList<Cart> myCartList = new ArrayList<>();

    HomeActivityViewModel viewModel;
    ShoppingCartRecyclerViewAdapter myAdapter;

    /**
     * Called when the activity is starting
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        // For every UI component a java object
        setContentView(R.layout.activity_home);
        myRecyclerView = findViewById(R.id.cart_recycler_view);

        // ViewModelProviders depricated!
        viewModel = ViewModelProviders.of(this).get(HomeActivityViewModel.class);
        viewModel.getCartLiveData().observe(this, cartUpdateObserver);
        viewModel.fetchAllCarts(myCartList);


        // OnClickListener which opens a new activity to create a new cart
        Button createNewCart = findViewById(R.id.new_cart_button);
        createNewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newCart = new Intent(getApplicationContext(), NewCartActivity.class);
                startActivityForResult(newCart, LAUNCH_NEW_CART_ACTIVITY);
            }
        });
    }

    /**
     * An observer that listens for changes and notifies the adapter so that the recycler view can be adjusted
     */
    Observer<ArrayList<Cart>> cartUpdateObserver = new Observer<ArrayList<Cart>>() {
        @Override
        public void onChanged(ArrayList<Cart> cartArrayList) {
            myAdapter = new ShoppingCartRecyclerViewAdapter(context, myCartList, HomeActivity.this, HomeActivity.this);
            myRecyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
            myRecyclerView.setAdapter(myAdapter);
        }
    };

    /**
     * What is executed when returning to this activity
     * @param requestCode Value checked with the one passed with startActivityForResult
     * @param resultCode Says something about what the result looks like, whether it was successful or not
     * @param data Data supplied via the Intent
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_NEW_CART_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                Cart shoppingCart;
                shoppingCart = (Cart) data.getSerializableExtra("newCart");
                viewModel.updateOrCreateCartList(myCartList, shoppingCart);
            }
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            System.out.println("An Error has occured!");
        }
    }

    /**
     * OnClick function to open a new activity that contains the information about the shopping cart and can be edited on it
     * @param position adapter position to know which shopping cart information should be provided by the array
     */
    @Override
    public void onCartClick(int position) {
        Intent onCartClick = new Intent(this, NewCartActivity.class);
        onCartClick.putExtra("selectedCart", myCartList.get(position));
        startActivityForResult(onCartClick, LAUNCH_NEW_CART_ACTIVITY);
    }

    /**
     * OnClick function for deleting a cart from the list
     * @param position adapter position to know at which position in the array the cart should be deleted
     */
    @Override
    public void onCanClick(int position) {
        Cart c = myCartList.get(position);
        myCartList.remove(c);
        viewModel.deleteCartFromDatabase(c.name);
        myAdapter.notifyDataSetChanged();
    }
}