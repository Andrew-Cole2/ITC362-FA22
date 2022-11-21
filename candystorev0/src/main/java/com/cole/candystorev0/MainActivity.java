package com.cole.candystorev0;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate Menu
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle Action Bar items

        int id = item.getItemId();

        switch(id) {
            case R.id.action_add:
                Log.w("onOptionsItemSeleted", "Add Selected");
                return true;
            case R.id.action_delete:
                Log.w("onOptionsItemSeleted", "Delete Selected");
                return true;
            case R.id.action_update:
                Log.w("onOptionsItemSeleted", "Updated Selected");
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}