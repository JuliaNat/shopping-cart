package com.example.shopping_cart;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NewCartActivity extends AppCompatActivity {
    Button saveAndBack;
    String cartName;
    EditText cartNameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cart);

        saveAndBack = (Button) findViewById(R.id.save_and_back_button);
        cartNameInput = (EditText) findViewById(R.id.cart_name_input);

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

        // Dropdown for nutrition selection

//        Spinner nutritionSpinner = (Spinner) findViewById(R.id.nutrition_dropdown);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.nutritions_array, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        nutritionSpinner.setAdapter(adapter);
//
//        nutritionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String nutrition = parent.getItemAtPosition(position).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                System.out.println("No nutrition selected");
//            }
//        });
    }
}