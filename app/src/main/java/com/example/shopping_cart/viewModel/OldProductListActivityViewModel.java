package com.example.shopping_cart.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shopping_cart.core.entities.Product;
import com.example.shopping_cart.core.repository.Firestore;

import java.util.ArrayList;

public class OldProductListActivityViewModel extends ViewModel implements Firestore.OnReadProductsComplete {
    Firestore firestore = new Firestore();
    ArrayList<Product> allProductsFromDatabase = new ArrayList<>();
    MutableLiveData<ArrayList<Product>> productLiveData;

    public OldProductListActivityViewModel() {
        initializeViewModel();
    }

    public ArrayList<Product> getAllProductsFromDatabase() {
        return allProductsFromDatabase;
    }

    public MutableLiveData<ArrayList<Product>> getProductLiveData() {
        return productLiveData;
    }

    public void initializeViewModel() {
        productLiveData = new MutableLiveData<>();
    }

    public void fetchAllProducts(ArrayList<Product> localProducts) {
        firestore.gettingProductsFromFirestore(this, localProducts);
    }

    @Override
    public void getProductData(ArrayList<Product> databaseProducts, ArrayList<Product> localProducts) {
        allProductsFromDatabase.addAll(databaseProducts);
        localProducts.addAll(databaseProducts);
        productLiveData.setValue(allProductsFromDatabase);
    }

}
