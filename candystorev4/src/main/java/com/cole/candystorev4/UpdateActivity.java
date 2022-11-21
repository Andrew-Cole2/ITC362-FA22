package com.cole.candystorev4;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
        ArrayList<Candy> candyList = new ArrayList<>();
        try {
            candyList = dbManager.selectAll();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Unable to open Update view", Toast.LENGTH_LONG);
            UpdateActivity.this.finish();
        }
        if (!(candyList.size() >= 0)) {
            UpdateActivity.this.finish();
        }

        ScrollView scrollView = new ScrollView(this);

        // Create layout
        RelativeLayout layout = new RelativeLayout(this);
        GridLayout grid = new GridLayout(this);
        grid.setRowCount(candyList.size());
        grid.setColumnCount(4);

        // Get screen size
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int width = size.x;

        // Create candy edit fields
        ButtonHandler buttonHandler = new ButtonHandler();
        for (Candy candy : candyList) {
            // Create id TextView
            TextView idView = new TextView(this);
            idView.setGravity(Gravity.CENTER);
            idView.setText("" + candy.getId());

            // Create names field
            EditText nameField = new EditText(this);
            nameField.setText(candy.getName());
            nameField.setId( 10 * candy.getId());

            // Create price field
            EditText priceField = new EditText(this);
            priceField.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            priceField.setText(candy.getPrice().toString());
            priceField.setId( 10 * candy.getId() + 1);

            // Create update button
            Button updateBtn = new Button(this);
            updateBtn.setText("UPDATE");
            updateBtn.setId(candy.getId());
            updateBtn.setOnClickListener(buttonHandler);

            grid.addView(idView, width/10, ViewGroup.LayoutParams.WRAP_CONTENT);
            grid.addView(nameField, (int) (width * .4), ViewGroup.LayoutParams.WRAP_CONTENT);
            grid.addView(priceField, (int) (width * .15), ViewGroup.LayoutParams.WRAP_CONTENT);
            grid.addView(updateBtn, (int) (width * .35), ViewGroup.LayoutParams.WRAP_CONTENT);
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
            EditText nameField = (EditText) findViewById(10*id);;
            EditText priceField = (EditText) findViewById(10*id+1);
            try {
                String newName = nameField.getText().toString();
                Double newPrice = (Double.parseDouble(priceField.getText().toString()));
                try {
                    dbManager.updateById(id, newName, newPrice);
                } catch (Exception e) {
                    Toast.makeText(UpdateActivity.this, "Unable to update candy", Toast.LENGTH_LONG).show();
                    Log.e("insert", "Error inserting candy to db: " + e.getMessage(), e.getCause());
                }
                Toast.makeText(UpdateActivity.this, "Candy Updated", Toast.LENGTH_LONG).show();
            } catch (NumberFormatException e) {
                Toast.makeText(UpdateActivity.this, "Unable to update", Toast.LENGTH_LONG).show();
                Log.e("insert", "Error converting number to a double: " + e.getMessage(), e.getCause());
            }
            updateView();
        }
    }
}