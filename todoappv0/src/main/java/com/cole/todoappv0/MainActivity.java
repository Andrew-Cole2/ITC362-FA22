package com.cole.todoappv0;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
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
        ArrayList<Task> taskList = new ArrayList<>();
        RelativeLayout layout = new RelativeLayout(this);
        try {
            taskList = dbManager.selectAll();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Unable to load tasksList", Toast.LENGTH_LONG);
        }


        // Get screen size
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int width = size.x;

        if ((taskList.size() == 0)) {
            // If no tasks are found, display no results msg
            TextView noResults = new TextView(this);
            noResults.setGravity(Gravity.CENTER);
            noResults.setText("No Tasks found");

            layout.addView(noResults,(int) (width * .5), ViewGroup.LayoutParams.WRAP_CONTENT);
            setContentView(layout);
            return;
        }
        ScrollView scrollView = new ScrollView(this);

        // Create layout
        GridLayout grid = new GridLayout(this);
        grid.setRowCount(taskList.size());
        grid.setColumnCount(3);

        // Create task edit fields
        for (Task task : taskList) {
            // Create id TextView
            TextView idView = new TextView(this);
            idView.setGravity(Gravity.CENTER);
            idView.setText("" + task.getId());

            // Create first name field
            TextView titleField = new TextView(this);
            titleField.setText(task.getTitle());
            titleField.setId( 10 * task.getId());

            // Create last name field
            TextView descField = new TextView(this);
            descField.setText(task.getDescription());
            descField.setId( 10 * task.getId()+1);

            // Create email field
//            TextView emailField = new TextView(this);
//            emailField.setText(task.getDeadline());
//            emailField.setId( 10 * task.getId()+2);

            grid.addView(idView, width/10, ViewGroup.LayoutParams.WRAP_CONTENT);
            grid.addView(titleField, (int) (width * .3), ViewGroup.LayoutParams.WRAP_CONTENT);
            grid.addView(descField, (int) (width * .3), ViewGroup.LayoutParams.WRAP_CONTENT);
//            grid.addView(deadlineField, (int) (width * .3), ViewGroup.LayoutParams.WRAP_CONTENT);
        }


        // Add items to layout
        scrollView.addView(grid);
        layout.addView(scrollView);

        setContentView(layout);
    }
}