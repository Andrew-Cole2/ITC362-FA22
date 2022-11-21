package com.cole.friendmanagerv0;

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
        EditText editTextFName = findViewById(R.id.input_fname);
        EditText editTextLName = findViewById(R.id.input_lname);
        EditText editTextEmail = findViewById(R.id.input_email);

        String fname = editTextFName.getText().toString();
        String lname = editTextLName.getText().toString();
        String email = editTextEmail.getText().toString();

        // insert to DB
        Friend insertFriend = new Friend();
        insertFriend.setfName(fname);
        insertFriend.setlName(lname);
        insertFriend.setEmail(email);
        try {
            dbManager.insert(insertFriend);
        } catch (Exception e) {
            Toast.makeText(this, "Unable to add friend: " + fname + " " + lname, Toast.LENGTH_LONG).show();
            Log.e("insert", "Error inserting Friend to db: " + e.getMessage(), e.getCause());
        }
        Toast.makeText(this, "Friend Added", Toast.LENGTH_LONG).show();


        // clear data
        editTextFName.getText().clear();
        editTextLName.getText().clear();
        editTextEmail.getText().clear();
    }

    public void returnMain(View view) {
        this.finish();
    }

}
