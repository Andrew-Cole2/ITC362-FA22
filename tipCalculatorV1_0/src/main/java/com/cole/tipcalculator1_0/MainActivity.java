package com.cole.tipcalculator1_0;

import android.content.res.Configuration;
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
    private EditText guestEditText;
    private EditText tipEditText;

    public final static String Activity = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration config = getResources().getConfiguration();
        modifyLayout(config);
        tipCalculator = new TipCalculator(0, 0, 0);

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        modifyLayout(newConfig);
    }

    public void modifyLayout(Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main_landscape);
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main);
        }
        billEditText = findViewById(R.id.edt_Bill);
        guestEditText = findViewById(R.id.edt_Guest);
        tipEditText = findViewById(R.id.edt_Tip);

        TextChangeHandler tch = new TextChangeHandler();
        billEditText.addTextChangedListener(tch);
        guestEditText.addTextChangedListener(tch);
        tipEditText.addTextChangedListener(tch);
    }

    public void calculate() {

        String billString = billEditText.getText().toString();
        String guestString = guestEditText.getText().toString();
        String tipString = tipEditText.getText().toString();

        TextView tipTextView = findViewById(R.id.tv_Tip_Amount);
        TextView guestTipTextView = findViewById(R.id.tv_Guest_Amount);
        TextView billTextView = findViewById(R.id.tv_Bill_Amount);

        try {
            // Convert input strings to float values
            float billAmount = Float.parseFloat(billString);
            int guestCount = Integer.parseInt(guestString);
            int tipPercent = Integer.parseInt(tipString);

            // Set calculator model values
            tipCalculator.setBill(billAmount);
            tipCalculator.setGuestCount(guestCount);
            tipCalculator.setTip(.01f * tipPercent);

            // Retrieve output values
            float tip = tipCalculator.tipAmount();
            float perGuestTip = tipCalculator.guestTipAmount();
            float total = tipCalculator.totalAmount();

            // Set output Text Fields
            tipTextView.setText(money.format(tip));
            guestTipTextView.setText(money.format(perGuestTip));
            billTextView.setText(money.format(total));

        } catch (NumberFormatException nfe) {
            Log.e("TipCalculator.calculate", "Error calculating tip", nfe);
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