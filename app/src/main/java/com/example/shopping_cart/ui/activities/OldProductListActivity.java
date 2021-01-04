package com.example.shopping_cart.ui.activities;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping_cart.R;
import com.example.shopping_cart.core.entities.Product;
import com.example.shopping_cart.ui.adapter.OldProductListRecyclerViewAdapter;
import com.example.shopping_cart.viewModel.OldProductListActivityViewModel;

import java.util.ArrayList;

public class OldProductListActivity extends AppCompatActivity {
    Context context;
    RecyclerView myRecyclerView;
    ArrayList<Product> myProductList = new ArrayList<>();

    OldProductListRecyclerViewAdapter myAdapter;
    OldProductListActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_product_list);
        context = this;

        viewModel = ViewModelProviders.of(this).get(OldProductListActivityViewModel.class);
        viewModel.getProductLiveData().observe(this, productUpdateObserver);
        viewModel.fetchAllProducts(myProductList);

        myRecyclerView = findViewById(R.id.recyclerView);
    }

    Observer<ArrayList<Product>> productUpdateObserver = new Observer<ArrayList<Product>>() {
        @Override
        public void onChanged(ArrayList<Product> cartArrayList) {
            myAdapter = new OldProductListRecyclerViewAdapter(context, myProductList);
            myRecyclerView.setLayoutManager(new LinearLayoutManager(OldProductListActivity.this));
            myRecyclerView.setAdapter(myAdapter);
        }
    };
}