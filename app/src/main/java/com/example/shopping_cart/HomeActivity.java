package com.example.shopping_cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {
    RecyclerView myRecyclerView;
    RecyclerViewAdapter myAdapter;

    ArrayList<Cart> myArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Cart myFirstCart = new Cart();

        myFirstCart.name = "Test";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        myRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        myRecyclerView.setLayoutManager(layoutManager);

        myAdapter = new RecyclerViewAdapter(this, myArrayList);
        myRecyclerView.setAdapter(myAdapter);

        myArrayList.add(myFirstCart);
        myAdapter.notifyDataSetChanged();
    }
}