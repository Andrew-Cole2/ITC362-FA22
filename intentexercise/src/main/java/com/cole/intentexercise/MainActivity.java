package com.cole.intentexercise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    Counter counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        counter = new Counter();
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setBtnText();
    }

    public void setBtnText() {
        AppCompatButton counterBtn = findViewById(R.id.btn_increment);
        counterBtn.setText("" + counter.getIncrementer());
    }

    public void increment(View view) {
        counter.count();
        Intent returnActivity = new Intent(this, Return.class);
        this.startActivity(returnActivity);

    }
}