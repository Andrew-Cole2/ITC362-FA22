package com.cole.duckhuntv0;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;

public class MainActivity extends Activity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Resources res = getResources();
        int statusBarHeight = 0;
        int statusBarId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (statusBarId > 0) {
            statusBarHeight = res.getDimensionPixelSize(statusBarId);
        }

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        gameView = new GameView(this, size.x, size.y - statusBarHeight);
        setContentView(gameView);
    }
}