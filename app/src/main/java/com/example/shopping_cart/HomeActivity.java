package com.example.shopping_cart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity implements RecyclerViewAdapter.OnCartClickListener {
    int LAUNCH_NEW_CART_ACTIVITY = 1;
    RecyclerView myRecyclerView;
    RecyclerViewAdapter myAdapter;

    ArrayList<Cart> myCartList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        myAdapter = new RecyclerViewAdapter(this, myCartList, this);
        myRecyclerView.setAdapter(myAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TextView cartNameHome = (TextView) findViewById(R.id.shopping_cart_name);

        if (requestCode == LAUNCH_NEW_CART_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                Cart shoppingCart = new Cart();

                shoppingCart.name = data.getStringExtra("cartNameValue");
                myCartList.add(shoppingCart);

                myAdapter.notifyDataSetChanged();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                System.out.println("An Error has occured!");
            }
        }
    }

    @Override
    public void onCartClick(int position) {
        Intent onCartClick = new Intent(this, NewCartActivity.class);
        onCartClick.putExtra("selectedCart", myCartList.get(position));
        startActivityForResult(onCartClick, LAUNCH_NEW_CART_ACTIVITY);
    }
}