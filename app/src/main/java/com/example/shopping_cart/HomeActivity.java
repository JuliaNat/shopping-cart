package com.example.shopping_cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {
    int LAUNCH_NEW_CART_ACTIVITY = 1;
    RecyclerView myRecyclerView;
    RecyclerViewAdapter myAdapter;

    ArrayList<Cart> myCartList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Cart myFirstCart = new Cart();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button createNewCart = findViewById(R.id.new_cart_button);
        createNewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newCart = new Intent(getApplicationContext(), NewCartActivity.class);
                startActivityForResult(newCart, LAUNCH_NEW_CART_ACTIVITY);
            }
        });

        myRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        myRecyclerView.setLayoutManager(layoutManager);

        myAdapter = new RecyclerViewAdapter(this, myCartList);
        myRecyclerView.setAdapter(myAdapter);

        myCartList.add(myFirstCart);
        myAdapter.notifyDataSetChanged();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TextView cartNameHome = (TextView) findViewById(R.id.shopping_cart_name);

        if(requestCode == LAUNCH_NEW_CART_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK) {
                Cart shoppingCart = new Cart();

                shoppingCart.name = data.getStringExtra("cartNameValue");
                myCartList.add(shoppingCart);

                for(Cart cart:myCartList) {
                    cartNameHome.setText(cart.name);
                }
            }
            if(resultCode == Activity.RESULT_CANCELED) {
                System.out.println("An Error has occured!");
            }
        }
    }
}