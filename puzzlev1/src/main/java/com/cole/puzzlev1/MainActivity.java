package com.cole.puzzlev1;

import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    public static int STATUS_BAR_HEIGHT = 24;
    public static int ACTION_BAR_HEIGHT = 56;
    private PuzzleView puzzleView;
    private Puzzle puzzle;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        puzzle = new Puzzle();

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenHeight = size.y;
        int puzzleWidth = size.x;

        Resources res = getResources();
        DisplayMetrics metrics = res.getDisplayMetrics();
        float pixelDensity = metrics.density;

        TypedValue typeVal = new TypedValue();
        int actionBarHeight = (int) (pixelDensity * ACTION_BAR_HEIGHT);
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, typeVal, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(typeVal.data, metrics);
        }

        int statusBarHeight = (int) (pixelDensity * STATUS_BAR_HEIGHT);
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId != 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }

        int puzzleHeight = screenHeight - statusBarHeight - actionBarHeight;
        puzzleView = new PuzzleView(this, puzzleWidth, puzzleHeight, puzzle.getNumberOfParts());
        String[] scrambled = puzzle.scramble();
        puzzleView.fillGui(scrambled);
        puzzleView.enableListener(this);
        setContentView(puzzleView);
    }

    public boolean onTouch(View view, MotionEvent event) {
        int index = puzzleView.indexOfTextView(view);
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                puzzleView.updateStartPositions(index, (int) event.getY());
                puzzleView.bringChildToFront(view);
                break;
            case MotionEvent.ACTION_MOVE:
                puzzleView.moveTextViewVertically(index, (int) event.getY());
                break;
        }
        return true;
    }
}