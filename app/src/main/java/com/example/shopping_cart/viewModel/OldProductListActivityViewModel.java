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

    /**
     * Called when the ViewModel is created
     */
    public OldProductListActivityViewModel() {
        initializeViewModel();
    }

    /**
     * For observing changes and updating afterwards
     *
     * @return list of products
     */
    public MutableLiveData<ArrayList<Product>> getProductLiveData() {
        return productLiveData;
    }
    /**
     * Creates new MutableLiveData on initialization of the ViewModel
     */
    public void initializeViewModel() {
        productLiveData = new MutableLiveData<>();
    }

    /**
     * Fetching all products from the Cloud Firestore
     * @param localProducts List to be written to
     */
    public void fetchAllProducts(ArrayList<Product> localProducts) {
        firestore.gettingProductsFromFirestore(this, localProducts);
    }

    /**
     * When the callback is executed, all the products in the database are written to the variables provided for this purpose
     * @param databaseProducts products from the database to process them
     * @param localProducts products from the database to be able to use them in the recycler view
     */
    @Override
    public void getProductData(ArrayList<Product> databaseProducts, ArrayList<Product> localProducts) {
        allProductsFromDatabase.addAll(databaseProducts);
        localProducts.addAll(databaseProducts);
        productLiveData.setValue(allProductsFromDatabase);
    }

    /**
     * Update product list in database with overwriting them
     * @param products product to be stored in the cloud
     */
    public void writeProductsInDatabase(ArrayList<Product> products) {
        for (Product product:products) {
            firestore.addProductToFirestore(product);
        }
    }

}
