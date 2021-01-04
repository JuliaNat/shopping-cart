package com.example.shopping_cart.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping_cart.R;
import com.example.shopping_cart.core.entities.Product;
import com.example.shopping_cart.ui.adapter.OldProductListRecyclerViewAdapter;

import java.util.ArrayList;

public class OldProductListActivity extends AppCompatActivity {
    ArrayList<Product> oldProductList;
    RecyclerView myRecyclerView;
    OldProductListRecyclerViewAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_product_list);

        myRecyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        myRecyclerView.setLayoutManager(layoutManager);

        myAdapter = new OldProductListRecyclerViewAdapter(this, oldProductList);
        myRecyclerView.setAdapter(myAdapter);
    }
}