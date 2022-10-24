package com.cole.orientationv1;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public final static String Activity = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.w(Activity, "Height: " + newConfig.screenHeightDp);
        Log.w(Activity, "Width: " + newConfig.screenWidthDp);

        Log.w(Activity, "Orientation: " + newConfig.orientation);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.w(Activity, "Horizontal position");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.w(Activity, "Vertical position");
        } else {
            Log.w(Activity, "Undetermined position");
        }
    }
}