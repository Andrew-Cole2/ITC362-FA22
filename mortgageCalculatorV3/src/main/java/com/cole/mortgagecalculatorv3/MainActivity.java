package com.cole.mortgagecalculatorv4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static Mortgage mortgage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mortgage = new Mortgage();
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateView();
    }

    private void updateView() {
        TextView tvAmount = findViewById(R.id.tv_amount);
        tvAmount.setText(mortgage.getFormattedAmount());
        TextView tvYears = findViewById(R.id.tv_years);
        tvYears.setText("" + mortgage.getYears());
        TextView tvInterest = findViewById(R.id.tv_interest);
        tvInterest.setText(mortgage.getFormattedRate());
        TextView tvPaymentM = findViewById(R.id.tv_payment_monthly);
        tvPaymentM.setText(mortgage.formattedMonthlyPayment());
        TextView tvPaymentT = findViewById(R.id.tv_payment_total);
        tvPaymentT.setText(mortgage.formattedTotalPayment());
    }

    public void modifyData(View view) {
        Intent dataActivity = new Intent(this, DataActivity.class);
        this.startActivity(dataActivity);
        overridePendingTransition(R.anim.slide_from_left, 0);
    }
}