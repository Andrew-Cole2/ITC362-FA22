package com.cole.candystorev3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;

public class DeleteActivity extends AppCompatActivity {

    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DatabaseManager(this);
        updateView();
    }

    // Build Db entries view
    private void updateView() {
        ArrayList<Candy> candyList = dbManager.selectAll();
        RelativeLayout layout = new RelativeLayout(this);
        ScrollView scrollView = new ScrollView(this);
        RadioGroup radioGroup = new RadioGroup(this);

        // Create candy radio group
        for (Candy candy : candyList) {
            RadioButton newButton = new RadioButton(this);
            newButton.setId(candy.getId());
            newButton.setText(candy.toString());
            radioGroup.addView(newButton);
        }

        // Setup event handling
        RadioButtonHandler buttonHandler = new RadioButtonHandler();
        radioGroup.setOnCheckedChangeListener(buttonHandler);


        // Add items to layout
        scrollView.addView(radioGroup);
        layout.addView(scrollView);

        // Build return button
        Button returnButton = new Button(this);
        returnButton.setText("Back");

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteActivity.this.finish();
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

    private class RadioButtonHandler implements  RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
            try {
                dbManager.deleteById(checkedId);
                Toast.makeText(DeleteActivity.this, "Candy Deleted", Toast.LENGTH_SHORT).show();
                updateView();
            } catch (Exception e) {
                Toast.makeText(DeleteActivity.this, "Candy could not beDeleted", Toast.LENGTH_SHORT).show();
            }
        }
    }
}