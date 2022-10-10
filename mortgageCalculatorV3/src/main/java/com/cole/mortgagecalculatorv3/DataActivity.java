package com.cole.mortgagecalculatorv4;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_data);

        updateData();
    }

    private void updateData() {
        Mortgage mortgage = MainActivity.mortgage;
        RadioButton activeButton = null;
        if (mortgage.getYears() == 10) {
            activeButton = findViewById(R.id.rb_years_1);
        }
        if (mortgage.getYears() == 15) {
            activeButton = findViewById(R.id.rb_years_2);
        }
        if (mortgage.getYears() == 30) {
            activeButton = findViewById(R.id.rb_years_3);
        }
        if(activeButton != null) {
            activeButton.setChecked(true);
        }

        EditText etAmount = findViewById(R.id.data_amount);
        etAmount.setText("" + mortgage.getAmount());
        EditText etInterest = findViewById(R.id.data_interest);
        etInterest.setText("" + mortgage.getRate());
    }
    private void updateMortgage() {
        Mortgage mortgage = MainActivity.mortgage;

        RadioButton rb1 = findViewById(R.id.rb_years_1);
        RadioButton rb2 = findViewById(R.id.rb_years_2);
        RadioButton rb3 = findViewById(R.id.rb_years_3);
        int years = 1;
        if (rb1.isChecked()) {
            years = 10;
        }
        if (rb2.isChecked()) {
            years = 15;
        }
        if (rb3.isChecked()) {
            years = 30;
        }
        mortgage.setYears(years);

        EditText etAmount = findViewById(R.id.data_amount);
        String amountString = etAmount.getText().toString();
        EditText etInterest = findViewById(R.id.data_interest);
        String interestString = etInterest.getText().toString();

        try {
            float amount = Float.parseFloat(amountString);
            mortgage.setAmount(amount);
            float interest = Float.parseFloat(interestString);
            mortgage.setRate(interest);
        } catch (NumberFormatException e) {
            mortgage.setAmount(0.0f);
            mortgage.setRate(.035f);
        }
    }

    public void goBack(View view) {
        updateMortgage();
        this.finish();
        overridePendingTransition(R.anim.fade_in_and_scale, 0);
    }
}
