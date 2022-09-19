package com.cole.tipcalculator;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private TipCalculator tipCalculator;
    private NumberFormat money = NumberFormat.getCurrencyInstance();
    private EditText billEditText;
    private EditText tipEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tipCalculator = new TipCalculator(0, 0);
        setContentView(R.layout.activity_main);


        billEditText = findViewById(R.id.edt_Bill);
        tipEditText = findViewById(R.id.edt_Tip);

        TextChangeHandler tch = new TextChangeHandler();
        billEditText.addTextChangedListener(tch);
        tipEditText.addTextChangedListener(tch);

    }

    public void calculate() {

        String billString = billEditText.getText().toString();
        String tipString = tipEditText.getText().toString();

        TextView tipTextView = findViewById(R.id.tv_Tip_Amount);
        TextView billTextView = findViewById(R.id.tv_Bill_Amount);

        try {
            // Convert input strings to float values
            float billAmount = Float.parseFloat(billString);
            int tipPercent = Integer.parseInt(tipString);

            // Set calculator model values
            tipCalculator.setBill(billAmount);
            tipCalculator.setTip(.01f * tipPercent);

            // Retrieve output values
            float tip = tipCalculator.tipAmount();
            float total = tipCalculator.totalAmount();

            // Set output Text Fields
            tipTextView.setText(money.format(tip));
            billTextView.setText(money.format(total));

        } catch (NumberFormatException nfe) {
            Log.e("calculate", "Error calculating tip", nfe);
        }


    }

    private class TextChangeHandler implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            calculate();
        }
    }
}