package com.cole.stoplightv1;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    Button button;
    TextView light;

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
        light = (TextView) findViewById(R.id.tv_label);
    }

    public void changeState(View view) {
        if (stopLight.getState() == StopLight.State.GO) {
            stopLight.setState(StopLight.State.WAIT);
            light.setBackground(new ColorDrawable(getResources().getColor(R.color.yellow)));
        }
        else if (stopLight.getState() == StopLight.State.WAIT) {
            stopLight.setState(StopLight.State.STOP);
            light.setBackground(new ColorDrawable(getResources().getColor(R.color.red)));
        }
        else if (stopLight.getState() == StopLight.State.STOP) {
            stopLight.setState(StopLight.State.GO);
            light.setBackground(new ColorDrawable(getResources().getColor(R.color.green)));
        }

        button.setText(stopLight.getState().msg());
    }
}