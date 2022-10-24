package com.cole.orientationv0;

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

        Configuration config = getResources().getConfiguration();
        Log.w(Activity, "screen dp height: " + config.screenHeightDp);
        Log.w(Activity, "screen dp width: " + config.screenWidthDp);

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;
        Log.w(Activity, "screen height in pixels = " + screenHeight);
        Log.w(Activity, "screen width in pixels = " + screenWidth);

        Resources res = getResources();
        DisplayMetrics metrics = res.getDisplayMetrics();
        float pixelDensity = metrics.density;
        Log.w(Activity, "logical pixel density = " + pixelDensity);

        if (config.isLayoutSizeAtLeast(Configuration.SCREENLAYOUT_SIZE_XLARGE)) {
            Log.w(Activity, "Extra large size screen");
        }
        else if (config.isLayoutSizeAtLeast(Configuration.SCREENLAYOUT_SIZE_LARGE)) {
            Log.w(Activity, "Large size screen");
        }
        else if (config.isLayoutSizeAtLeast(Configuration.SCREENLAYOUT_SIZE_NORMAL)) {
            Log.w(Activity, "Normal size screen");
        }
        else if (config.isLayoutSizeAtLeast(Configuration.SCREENLAYOUT_SIZE_SMALL)) {
            Log.w(Activity, "Small size screen");
        }
        else {
            Log.w(Activity, "Unknown size screen");
        }
        Log.w(Activity, "Landscape constant: "
                + Configuration.ORIENTATION_LANDSCAPE);
        Log.w(Activity, "Portrait constant: "
                + Configuration.ORIENTATION_PORTRAIT);
        Log.w(Activity, "Orientation: " + config.orientation);
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE)
            Log.w(Activity, "Horizontal position");
        else if (config.orientation == Configuration.ORIENTATION_PORTRAIT)
            Log.w(Activity, "Vertical position");
        else
            Log.w(Activity, "Undetermined position");
    }
}