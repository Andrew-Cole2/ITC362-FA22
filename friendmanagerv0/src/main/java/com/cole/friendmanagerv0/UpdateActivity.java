package com.cole.friendmanagerv0;

import android.graphics.Point;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class UpdateActivity extends AppCompatActivity {

    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DatabaseManager(this);
        updateView();
    }

    // Build Db entries view
    private void updateView() {
        ArrayList<Friend> friendList = new ArrayList<>();
        try {
            friendList = dbManager.selectAll();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Unable to open Update view", Toast.LENGTH_LONG);
            UpdateActivity.this.finish();
        }
        Log.i("update", String.valueOf(friendList.size()));
        if (!(friendList.size() >= 0)) {
            Log.i("Update View", "You have no friends (yet)");
            UpdateActivity.this.finish();
        }

        ScrollView scrollView = new ScrollView(this);

        // Create layout
        RelativeLayout layout = new RelativeLayout(this);
        GridLayout grid = new GridLayout(this);
        grid.setRowCount(friendList.size());
        grid.setColumnCount(5);

        // Get screen size
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int width = size.x;

        // Create friend edit fields
        ButtonHandler buttonHandler = new ButtonHandler();
        for (Friend friend : friendList) {
            // Create id TextView
            TextView idView = new TextView(this);
            idView.setGravity(Gravity.CENTER);
            idView.setText("" + friend.getId());

            // Create first name field
            EditText fNameField = new EditText(this);
            fNameField.setText(friend.getfName());
            fNameField.setId( 10 * friend.getId());

            // Create last name field
            EditText lNameField = new EditText(this);
            lNameField.setText(friend.getlName());
            lNameField.setId( 10 * friend.getId()+1);

            // Create email field
            EditText emailField = new EditText(this);
            emailField.setText(friend.getEmail());
            emailField.setId( 10 * friend.getId()+2);

            // Create update button
            Button updateBtn = new Button(this);
            updateBtn.setText("UPDATE");
            updateBtn.setId(friend.getId());
            updateBtn.setOnClickListener(buttonHandler);

            grid.addView(idView, width/10, ViewGroup.LayoutParams.WRAP_CONTENT);
            grid.addView(fNameField, (int) (width * .2), ViewGroup.LayoutParams.WRAP_CONTENT);
            grid.addView(lNameField, (int) (width * .2), ViewGroup.LayoutParams.WRAP_CONTENT);
            grid.addView(emailField, (int) (width * .35), ViewGroup.LayoutParams.WRAP_CONTENT);
            grid.addView(updateBtn, (int) (width * .25), ViewGroup.LayoutParams.WRAP_CONTENT);
        }


        // Add items to layout
        scrollView.addView(grid);
        layout.addView(scrollView);

        // Build return button
        Button returnButton = new Button(this);
        returnButton.setText("Back");

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateActivity.this.finish();
            }
        });

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.setMargins(0, 0, 0, 50);
        layout.addView(returnButton, params);

        // Load widget

        setContentView(layout);
    }

    private class ButtonHandler implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            int id = view.getId();
            EditText fNameField = (EditText) findViewById(10*id);
            EditText lNameField = (EditText) findViewById(10*id+1);
            EditText emailField = (EditText) findViewById(10*id+2);
            String newfName = fNameField.getText().toString();
            String newlName = lNameField.getText().toString();
            String newEmail = emailField.getText().toString();
            try {
                dbManager.updateById(id, newfName, newlName, newEmail);
            } catch (Exception e) {
                Toast.makeText(UpdateActivity.this, "Unable to update friend", Toast.LENGTH_LONG).show();
                Log.e("insert", "Error inserting friend to db: " + e.getMessage(), e.getCause());
            }
            Toast.makeText(UpdateActivity.this, "Friend Updated", Toast.LENGTH_LONG).show();

            updateView();
        }
    }
}