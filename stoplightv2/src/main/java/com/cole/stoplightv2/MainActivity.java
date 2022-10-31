package com.cole.stoplightv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView greenLight;
    TextView yellowLight;
    TextView redLight;

    private StopLight stopLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadComponents();

        stopLight = new StopLight(StopLight.State.STOP);
    }

    public void loadComponents() {
        button = findViewById(R.id.btn_change);
        greenLight = (TextView) findViewById(R.id.tv_label_green);
        yellowLight = (TextView) findViewById(R.id.tv_label_yellow);
        redLight = (TextView) findViewById(R.id.tv_label_red);
    }

    public void changeState(View view) {
        if (stopLight.getState() == StopLight.State.GO) {
            stopLight.setState(StopLight.State.WAIT);
            greenLight.setVisibility(View.INVISIBLE);
            yellowLight.setVisibility(View.VISIBLE);
        }
        else if (stopLight.getState() == StopLight.State.WAIT) {
            stopLight.setState(StopLight.State.STOP);
            yellowLight.setVisibility(View.INVISIBLE);
            redLight.setVisibility(View.VISIBLE);
        }
        else if (stopLight.getState() == StopLight.State.STOP) {
            stopLight.setState(StopLight.State.GO);
            redLight.setVisibility(View.INVISIBLE);
            greenLight.setVisibility(View.VISIBLE);
        }

        button.setText(stopLight.getState().msg());
    }
}