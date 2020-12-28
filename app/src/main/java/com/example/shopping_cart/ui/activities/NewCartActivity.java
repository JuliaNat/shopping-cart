package com.example.shopping_cart.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping_cart.R;
import com.example.shopping_cart.core.entities.Cart;
import com.example.shopping_cart.core.entities.Product;
import com.example.shopping_cart.ui.adapter.ProductListRecyclerViewAdapter;
import com.example.shopping_cart.viewModel.NewCartActivityViewModel;

import java.util.ArrayList;
import java.util.UUID;

public class NewCartActivity extends AppCompatActivity implements ProductListRecyclerViewAdapter.OnCanClickListener {
    Button addNewProduct, saveAndBack;
    EditText cartNameInput;

    RecyclerView myRecyclerView;
    ProductListRecyclerViewAdapter myAdapter;

    ArrayList<Product> myProductList = new ArrayList<>();

    NewCartActivityViewModel viewModel = new NewCartActivityViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cart);

        cartNameInput = findViewById(R.id.cart_name_input);
        addNewProduct = findViewById(R.id.add_product_button);
        saveAndBack = findViewById(R.id.save_and_back_button);

        // Recycler View for product list
        myRecyclerView = findViewById(R.id.productRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        myRecyclerView.setLayoutManager(layoutManager);

        myAdapter = new ProductListRecyclerViewAdapter(this, myProductList, this);
        myRecyclerView.setAdapter(myAdapter);

        if (getIntent().getSerializableExtra("selectedCart") != null) {
            Cart selected = (Cart) getIntent().getSerializableExtra("selectedCart");
            cartNameInput.setText(selected.name);
            myProductList.addAll(selected.cartProducts);
        }

        addNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NewCartActivity.this);
                final View newProductView = getLayoutInflater().inflate(R.layout.new_product, null);
                builder.setTitle(getResources().getString(R.string.add_new_product));

                final Spinner nutritionSpinner = newProductView.findViewById(R.id.nutrition_spinner);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(NewCartActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.nutritions_array));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                nutritionSpinner.setAdapter(adapter);

                builder.setView(newProductView);

                builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText productName = newProductView.findViewById(R.id.product_name);
                        EditText productWeight = newProductView.findViewById(R.id.product_weight);

                        if (!nutritionSpinner.getSelectedItem().toString().equalsIgnoreCase("Einheit wählen…") &&
                                !productName.getText().toString().equalsIgnoreCase("") &&
                                !productWeight.getText().toString().equalsIgnoreCase("")) {
                            Product product = new Product();
                            product.name = productName.getText().toString();
                            product.weight = productWeight.getText().toString();
                            product.nutrition = nutritionSpinner.getSelectedItem().toString();

                            myProductList.add(product);
                            myAdapter.notifyDataSetChanged();
                            dialog.dismiss();
                        } else {
                            AlertDialog.Builder errorBuilder = new AlertDialog.Builder(NewCartActivity.this);
                            errorBuilder.setTitle("Error");
                            errorBuilder.setMessage("Bitte füllen Sie alle Felder aus");
                            errorBuilder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog errorDialog = errorBuilder.create();
                            errorDialog.show();
                        }
                    }
                });

                builder.setNegativeButton(getResources().getString(R.string.back), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

                // Leave this area for documentation
              /*  LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.new_product, null);

                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                ImageButton closePopup = (ImageButton) popupView.findViewById(R.id.closing_button);
                Spinner nutritionSpinner = (Spinner) popupView.findViewById(R.id.nutrition_spinner);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(NewCartActivity.this, R.array.nutritions_array, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                nutritionSpinner.setAdapter(adapter);

                closePopup.setOnClickListener(new Button.OnClickListener(){
                   @Override
                   public void onClick(View v) {
                       popupWindow.dismiss();
                   }
                });

                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });*/
            }
        });

        saveAndBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart newCart = new Cart();
                if(getIntent().getSerializableExtra("selectedCart") != null) {
                    Cart selectedCart = (Cart) getIntent().getSerializableExtra("selectedCart");
                    newCart.cartID = selectedCart.cartID;
                } else {
                    newCart.cartID = String.valueOf(UUID.randomUUID());
                }

                newCart.name = cartNameInput.getText().toString();
                newCart.cartProducts = myProductList;

                // Write saved cart in database
                viewModel.writeInDatabase(newCart);

                Intent newCartReturn = new Intent(getApplicationContext(), HomeActivity.class);
                newCartReturn.putExtra("newCart", newCart);

                setResult(Activity.RESULT_OK, newCartReturn);
                finish();
            }
        });
    }

    @Override
    public void onCanClick(int position) {
        Product p = myProductList.get(position);
        myProductList.remove(p);
        myAdapter.notifyDataSetChanged();
    }
}