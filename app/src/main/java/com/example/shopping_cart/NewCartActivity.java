package com.example.shopping_cart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class NewCartActivity extends AppCompatActivity {
    Button addNewProduct, saveAndBack;
    EditText cartNameInput;
    String cartName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cart);

        cartNameInput = (EditText) findViewById(R.id.cart_name_input);
        addNewProduct = (Button) findViewById(R.id.add_product_button);
        saveAndBack = (Button) findViewById(R.id.save_and_back_button);

        if (getIntent().getSerializableExtra("selectedCart") != null) {
            Cart selected = (Cart) getIntent().getSerializableExtra("selectedCart");
            cartNameInput.setText(selected.name);
        }

        addNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.new_product, null);

                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                ImageButton closePopup = (ImageButton) popupView.findViewById(R.id.closing_button);
                Spinner nutritionSpinner = (Spinner) findViewById(R.id.nutrition_spinner);
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
                });
            }
        });

        saveAndBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartName = cartNameInput.getText().toString();

                Intent newCartReturn = new Intent(getApplicationContext(), HomeActivity.class);
                newCartReturn.putExtra("cartNameValue", cartName);

                setResult(Activity.RESULT_OK, newCartReturn);
                finish();
            }
        });
    }
}