package com.cole.friendmanagerv0;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DatabaseManager(this);
        updateView();
    }

    @Override
    protected void onResume() {
        updateView();
        super.onResume();
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
                Log.w("onOptionsItemSelected", "Add Selected");
                Intent insertIntent = new Intent(this, InsertActivity.class);
                this.startActivity(insertIntent);
                return true;
            case R.id.action_delete:
                Log.w("onOptionsItemSelected", "Delete Selected");
                Intent deleteIntent = new Intent(this, DeleteActivity.class);
                this.startActivity(deleteIntent);
                return true;
            case R.id.action_update:
                Log.w("onOptionsItemSelected", "Updated Selected");
                Intent updateIntent = new Intent(this, UpdateActivity.class);
                this.startActivity(updateIntent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateView() {
        ArrayList<Friend> friendList = new ArrayList<>();
        RelativeLayout layout = new RelativeLayout(this);
        try {
            friendList = dbManager.selectAll();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Unable to load friendsList", Toast.LENGTH_LONG);
        }


        // Get screen size
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int width = size.x;

        if ((friendList.size() == 0)) {
            // If no friends are found, display no results msg
            TextView noResults = new TextView(this);
            noResults.setGravity(Gravity.CENTER);
            noResults.setText("No Friends found");

            layout.addView(noResults,(int) (width * .5), ViewGroup.LayoutParams.WRAP_CONTENT);
            setContentView(layout);
            return;
        }
        ScrollView scrollView = new ScrollView(this);

        // Create layout
        GridLayout grid = new GridLayout(this);
        grid.setRowCount(friendList.size());
        grid.setColumnCount(4);

        // Create friend edit fields
        for (Friend friend : friendList) {
            // Create id TextView
            TextView idView = new TextView(this);
            idView.setGravity(Gravity.CENTER);
            idView.setText("" + friend.getId());

            // Create first name field
            TextView fNameField = new TextView(this);
            fNameField.setText(friend.getfName());
            fNameField.setId( 10 * friend.getId());

            // Create last name field
            TextView lNameField = new TextView(this);
            lNameField.setText(friend.getlName());
            lNameField.setId( 10 * friend.getId()+1);

            // Create email field
            TextView emailField = new TextView(this);
            emailField.setText(friend.getEmail());
            emailField.setId( 10 * friend.getId()+2);

            grid.addView(idView, width/10, ViewGroup.LayoutParams.WRAP_CONTENT);
            grid.addView(fNameField, (int) (width * .3), ViewGroup.LayoutParams.WRAP_CONTENT);
            grid.addView(lNameField, (int) (width * .3), ViewGroup.LayoutParams.WRAP_CONTENT);
            grid.addView(emailField, (int) (width * .3), ViewGroup.LayoutParams.WRAP_CONTENT);
        }


        // Add items to layout
        scrollView.addView(grid);
        layout.addView(scrollView);

        setContentView(layout);
    }
}