package com.cole.stoplightv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Button button;
    ImageView redLight;
    ImageView greenLight;
    ImageView yellowLight;

    private StopLight stopLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stopLight = new StopLight(StopLight.State.GO);
    }

    public void loadComponents() {
        button = findViewById(R.id.button);
        redLight = findViewById(R.id.img_red);
        greenLight = findViewById(R.id.img_green);
        yellowLight = findViewById(R.id.img_yellow);
    }

    public void changeState(View view) {
        if (stopLight.getState() == StopLight.State.GO) {
            stopLight.setState(StopLight.State.WAIT);
            button.setBackground(new ColorDrawable(getResources().getColor(R.color.yellow)));
            greenLight.setVisibility(View.INVISIBLE);
            yellowLight.setVisibility(View.VISIBLE);
        }
        else if (stopLight.getState() == StopLight.State.WAIT) {
            stopLight.setState(StopLight.State.STOP);
            button.setBackground(new ColorDrawable(getResources().getColor(R.color.red)));
            yellowLight.setVisibility(View.INVISIBLE);
            redLight.setVisibility(View.VISIBLE);
        }
        else if (stopLight.getState() == StopLight.State.STOP) {
            stopLight.setState(StopLight.State.GO);
            button.setBackground(new ColorDrawable(getResources().getColor(R.color.green)));
            redLight.setVisibility(View.INVISIBLE);
            greenLight.setVisibility(View.VISIBLE);
        }

        button.setText(stopLight.getState().msg());


    }
}