package com.cole.todoappv1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;

public class InsertActivity extends AppCompatActivity {

    private DatabaseManager dbManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DatabaseManager(this);
        setContentView(R.layout.activity_insert);
    }

    public void insert(View view) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Log.i("insert", "Inserting form data");
        // Get User Data
        EditText editTextTitle = findViewById(R.id.input_title);
        EditText editTextDesc = findViewById(R.id.input_description);
        EditText editTextDeadline = findViewById(R.id.input_deadline);

        String title = editTextTitle.getText().toString();
        String description = editTextDesc.getText().toString();
        String deadline = editTextDeadline.getText().toString();

        try {
            // insert to DB
            Task insertTask = new Task();
            insertTask.setTitle(title);
            insertTask.setDescription(description);
            Log.i("insert", "deadline: " + deadline);
            insertTask.setDeadline(deadline);
            dbManager.insert(insertTask);
            Toast.makeText(this, "Task Added", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Unable to add task: " + title, Toast.LENGTH_LONG).show();
            Log.e("insert", "Error inserting Task to db: " + e.getMessage(), e.getCause());
        }


        // clear data
        editTextTitle.getText().clear();
        editTextDesc.getText().clear();
        editTextDeadline.getText().clear();
    }

    public void returnMain(View view) {
        this.finish();
    }

}
