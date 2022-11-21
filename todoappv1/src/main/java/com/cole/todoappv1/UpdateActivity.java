package com.cole.todoappv1;

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

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        grid.setColumnCount(5);

        // Create task edit fields
        ButtonHandler buttonHandler = new ButtonHandler();
        for (Task task : taskList) {
            // Create id TextView
            TextView idView = new TextView(this);
            idView.setGravity(Gravity.CENTER);
            idView.setText("" + task.getId());

            // Create first name field
            EditText fNameField = new EditText(this);
            fNameField.setText(task.getTitle());
            fNameField.setId( 10 * task.getId());

            // Create last name field
            EditText lNameField = new EditText(this);
            lNameField.setText(task.getDescription());
            lNameField.setId( 10 * task.getId()+1);

            // Create email field
            EditText deadlineField = new EditText(this);
            deadlineField.setText(task.getDeadlineString());
            deadlineField.setId( 10 * task.getId()+2);

            // Create update button
            Button updateBtn = new Button(this);
            updateBtn.setText("UPDATE");
            updateBtn.setId(task.getId());
            updateBtn.setOnClickListener(buttonHandler);

            grid.addView(idView, width/15, ViewGroup.LayoutParams.WRAP_CONTENT);
            grid.addView(fNameField, (int) (width * .15), ViewGroup.LayoutParams.WRAP_CONTENT);
            grid.addView(lNameField, (int) (width * .3), ViewGroup.LayoutParams.WRAP_CONTENT);
            grid.addView(deadlineField, (int) (width * .25), ViewGroup.LayoutParams.WRAP_CONTENT);
            grid.addView(updateBtn, (int) (width * .2), ViewGroup.LayoutParams.WRAP_CONTENT);
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

        private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        @Override
        public void onClick(View view) {

            int id = view.getId();
            EditText titleField = (EditText) findViewById(10*id);
            EditText descField = (EditText) findViewById(10*id+1);
            EditText deadlineField = (EditText) findViewById(10*id+2);
            String newTitle = titleField.getText().toString();
            String newDescription = descField.getText().toString();
            String newDeadlineString = deadlineField.getText().toString();
            try {
                Date parsedDate = dateFormat.parse(newDeadlineString);
                Timestamp newDeadline = new java.sql.Timestamp(parsedDate.getTime());
                Log.i("ReadTime: ", newDeadline.toString());
                dbManager.updateById(id, newTitle, newDescription, newDeadline);
            } catch (Exception e) {
                Toast.makeText(UpdateActivity.this, "Unable to update task", Toast.LENGTH_LONG).show();
                Log.e("insert", "Error inserting task to db: " + e.getMessage(), e.getCause());
            }
            Toast.makeText(UpdateActivity.this, "Task Updated", Toast.LENGTH_LONG).show();

            updateView();
        }
    }
}