package com.cole.todoappv0;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
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
        RadioGroup radioGroup = new RadioGroup(this);

        // Create task radio group
        for (Task task : taskList) {
            RadioButton newButton = new RadioButton(this);
            newButton.setId(task.getId());
            newButton.setText(task.toString());
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
                Toast.makeText(DeleteActivity.this, "Task Deleted", Toast.LENGTH_SHORT).show();
                updateView();
            } catch (Exception e) {
                Toast.makeText(DeleteActivity.this, "Task could not be Deleted", Toast.LENGTH_SHORT).show();
            }
        }
    }
}