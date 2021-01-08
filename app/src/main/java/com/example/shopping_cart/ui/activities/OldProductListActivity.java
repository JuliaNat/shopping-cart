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
import com.example.shopping_cart.core.entities.Product;
import com.example.shopping_cart.ui.adapter.OldProductListRecyclerViewAdapter;
import com.example.shopping_cart.viewModel.OldProductListActivityViewModel;

import java.util.ArrayList;

public class OldProductListActivity extends AppCompatActivity {
    Context context;
    Button abort, saveAndBack;
    RecyclerView myRecyclerView;
    ArrayList<Product> myProductList = new ArrayList<>();

    OldProductListRecyclerViewAdapter myAdapter;
    OldProductListActivityViewModel viewModel;

    /**
     * Called when the activity is starting
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_old_product_list);

        // For every UI component a java object
        abort = findViewById(R.id.abort_button);
        saveAndBack = findViewById(R.id.save_and_back_button);
        myRecyclerView = findViewById(R.id.old_product_recycler_view);

        viewModel = ViewModelProviders.of(this).get(OldProductListActivityViewModel.class);
        viewModel.getProductLiveData().observe(this, productUpdateObserver);
        viewModel.fetchAllProducts(myProductList);

        // Sends selected products back to the NewCartActivity to add them to a cart
        saveAndBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Product> checkedProducts = new ArrayList<>();
                Intent backToCart = new Intent(OldProductListActivity.this, NewCartActivity.class);
                backToCart.putExtra("checkedProducts", myAdapter.getCheckedProducts());
                checkedProducts.addAll(myAdapter.getCheckedProducts());
                // all isChecked back to false
                for(Product p:checkedProducts) {
                    p.setChecked(false);
                }
                // update products in database with false isChecked. otherwise every time you open the activity on isChecked
                viewModel.writeProductsInDatabase(checkedProducts);
                setResult(RESULT_OK, backToCart);
                finish();
            }
        });

        // ends activity and goes back to the previous one
        abort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToCart = new Intent();
                setResult(Activity.RESULT_CANCELED, backToCart);
                finish();
            }
        });
    }

    /**
     * An observer that listens for changes and notifies the adapter so that the recycler view can be adjusted
     */
    Observer<ArrayList<Product>> productUpdateObserver = new Observer<ArrayList<Product>>() {
        @Override
        public void onChanged(ArrayList<Product> productArrayList) {
            myAdapter = new OldProductListRecyclerViewAdapter(context, myProductList);
            myRecyclerView.setLayoutManager(new LinearLayoutManager(OldProductListActivity.this));
            myRecyclerView.setAdapter(myAdapter);
        }
    };
}