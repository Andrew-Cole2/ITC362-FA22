package com.cole.todoappv0;

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
        EditText editTextTitle = findViewById(R.id.input_title);
        EditText editTextDesc = findViewById(R.id.input_description);

        String title = editTextTitle.getText().toString();
        String description = editTextDesc.getText().toString();

        // insert to DB
        Task insertTask = new Task();
        insertTask.setTitle(title);
        insertTask.setDescription(description);
        try {
            dbManager.insert(insertTask);
        } catch (Exception e) {
            Toast.makeText(this, "Unable to add task: " + title, Toast.LENGTH_LONG).show();
            Log.e("insert", "Error inserting Task to db: " + e.getMessage(), e.getCause());
        }
        Toast.makeText(this, "Task Added", Toast.LENGTH_LONG).show();


        // clear data
        editTextTitle.getText().clear();
        editTextDesc.getText().clear();
    }

    public void returnMain(View view) {
        this.finish();
    }

}
