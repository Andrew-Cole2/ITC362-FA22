package com.cole.burgercaloriecounter;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Declare class variables
    private RadioGroup pattyRG;
    private CheckBox extraOption;
    private RadioGroup cheeseRG;
    private SeekBar sauceSBR;
    private TextView caloriesTV;

    // Declare Calculator Instance
    private CalorieCalculator calculator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Calculator
        calculator = new CalorieCalculator();

        // Load Option Inputs
        loadOptions();

        // Register listeners on UI components
        registerChangeListener();

    }

    private void loadOptions() {
        pattyRG = findViewById(R.id.rbg_patties);
        extraOption = findViewById(R.id.cb_extra_option);
        cheeseRG = findViewById(R.id.rbg_cheese);
        sauceSBR = findViewById(R.id.sb_sauce);
        caloriesTV = findViewById(R.id.tv_calories);

        displayCalories();
    }

    private void registerChangeListener() {
        pattyRG.setOnCheckedChangeListener(foodListener);
        extraOption.setOnClickListener(extraOptionListener);
        cheeseRG.setOnCheckedChangeListener(foodListener);
        sauceSBR.setOnSeekBarChangeListener(sauceListener);
    }

    private RadioGroup.OnCheckedChangeListener foodListener = new RadioGroup.OnCheckedChangeListener() {
        public void onCheckedChanged(RadioGroup rbGroup, int radioId) {
            RadioButton rb = findViewById(radioId);
            String btn = rb.getText().toString();
            if (btn.equals(getString(R.string.patty_one)))
                calculator.setPattyCalories(CalorieCalculator.PATTY_ONE);
            if (btn.equals(getString(R.string.patty_two)))
                calculator.setPattyCalories(CalorieCalculator.PATTY_TWO);
            if (btn.equals(getString(R.string.patty_three)))
                calculator.setPattyCalories(CalorieCalculator.PATTY_THREE);
            if (btn.equals(getString(R.string.cheese_one)))
                calculator.setPattyCalories(CalorieCalculator.CHEESE_ONE);
            if (btn.equals(getString(R.string.cheese_two)))
                calculator.setPattyCalories(CalorieCalculator.CHEESE_TWO);
            displayCalories();
        }
    };

    private View.OnClickListener extraOptionListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (((CheckBox) v).isChecked())
                calculator.setProsciuttoCalories(CalorieCalculator.EXTRA);
            else
                calculator.clearProsciuttoCalories();

            displayCalories();
        }
    };

    private SeekBar.OnSeekBarChangeListener sauceListener = new SeekBar.OnSeekBarChangeListener() {
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            calculator.setSauceCalories(seekBar.getProgress());
            // sauceCal = seekBar.getProgress();
            displayCalories();
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    private void displayCalories() {
        String calorieText = "Calories: " + calculator.getTotalCalories();
        caloriesTV.setText(calorieText);
    }
}