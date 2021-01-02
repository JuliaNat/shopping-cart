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
    ShoppingCartRecyclerViewAdapter myAdapter;
    ArrayList<Cart> myCartList = new ArrayList<>();
    HomeActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = this;

        viewModel = ViewModelProviders.of(this).get(HomeActivityViewModel.class);
        viewModel.getCartLiveData().observe(this, cartUpdateObserver);
        viewModel.fetchAllCarts(myCartList);

        Button createNewCart = findViewById(R.id.new_cart_button);
        createNewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newCart = new Intent(getApplicationContext(), NewCartActivity.class);
                startActivityForResult(newCart, LAUNCH_NEW_CART_ACTIVITY);
            }
        });

        myRecyclerView = findViewById(R.id.cartRecyclerView);
    }

    Observer<ArrayList<Cart>> cartUpdateObserver = new Observer<ArrayList<Cart>>() {
        @Override
        public void onChanged(ArrayList<Cart> cartArrayList) {
            myAdapter = new ShoppingCartRecyclerViewAdapter(context, myCartList, HomeActivity.this, HomeActivity.this);
            myRecyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
            myRecyclerView.setAdapter(myAdapter);
        }
    };

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
        // TODO correct implemented error handling
        if (resultCode == Activity.RESULT_CANCELED) {
            System.out.println("An Error has occured!");
        }
    }

    @Override
    public void onCartClick(int position) {
        Intent onCartClick = new Intent(this, NewCartActivity.class);
        onCartClick.putExtra("selectedCart", myCartList.get(position));
        startActivityForResult(onCartClick, LAUNCH_NEW_CART_ACTIVITY);
    }

    @Override
    public void onCanClick(int position) {
        Cart c = myCartList.get(position);
        myCartList.remove(c);
        viewModel.deleteCartFromDatabase(c.name);
        myAdapter.notifyDataSetChanged();
    }
}