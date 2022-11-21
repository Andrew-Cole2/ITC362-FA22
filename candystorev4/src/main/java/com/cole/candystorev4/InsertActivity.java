package com.cole.candystorev4;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class InsertActivity extends AppCompatActivity {

    private DatabaseManager dbManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DatabaseManager(this);
        setContentView(R.layout.activity_insert);
    }

    public void insert(View view) {
        Log.i("insert", "Inserting form data");
        // Get User Data
        EditText editTextName = findViewById(R.id.input_name);
        EditText editTextPrice = findViewById(R.id.input_price);

        String name = editTextName.getText().toString();
        String priceString = editTextPrice.getText().toString();

        // insert to DB
        Candy insertCandy = new Candy();
        insertCandy.setName(name);
        try {
            insertCandy.setPrice(Double.parseDouble(priceString));
            try {
                dbManager.insert(insertCandy);
            } catch (Exception e) {
                Toast.makeText(this, "Unable to add candy: " + name, Toast.LENGTH_LONG).show();
                Log.e("insert", "Error inserting candy to db: " + e.getMessage(), e.getCause());
            }
            Toast.makeText(this, "Candy Added", Toast.LENGTH_LONG).show();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Unable to add candy: " + name, Toast.LENGTH_LONG).show();
            Log.e("insert", "Error converting number to a double: " + e.getMessage(), e.getCause());
        }


        // clear data
        editTextName.getText().clear();
        editTextPrice.getText().clear();
    }

    public void returnMain(View view) {
        this.finish();
    }

}
