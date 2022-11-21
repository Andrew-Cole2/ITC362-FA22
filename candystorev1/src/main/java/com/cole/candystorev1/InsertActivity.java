package com.cole.candystorev1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class InsertActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        // clear data
        editTextName.getText().clear();
        editTextPrice.getText().clear();
    }

    public void returnMain(View view) {
        this.finish();
    }

}
