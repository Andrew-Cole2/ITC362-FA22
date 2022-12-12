package com.cole.puzzlev2;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class PuzzleView extends RelativeLayout {
    private TextView[] textViewArr;
    private LayoutParams[] params;
    private int[] colors;

    private int labelHeight;
    private int startY;
    private int startTouchY;
    private int emptyPosition;
    private int[] positions;

    public PuzzleView(AppCompatActivity activity, int width, int height, int numberOfPieces) {
        super(activity);
        buildGuiByCode(activity, width, height, numberOfPieces);
    }

    public void buildGuiByCode(AppCompatActivity activity, int width, int height,
                               int numberOfPieces) {
        positions = new int[numberOfPieces];
        textViewArr = new TextView[numberOfPieces];
        colors = new int[textViewArr.length];
        params = new LayoutParams[textViewArr.length];
        Random random = new Random();
        labelHeight = height / numberOfPieces;
        for (int i = 0; i < textViewArr.length; i++) {
            textViewArr[i] = new TextView(activity);
            textViewArr[i].setGravity(Gravity.CENTER);
            colors[i] = Color.rgb(random.nextInt(255),
                    random.nextInt(255), random.nextInt(255));
            textViewArr[i].setBackgroundColor(colors[i]);
            params[i] = new LayoutParams(width, labelHeight);
            params[i].leftMargin = 0;
            params[i].topMargin = labelHeight * i;
            addView(textViewArr[i], params[i]);
        }
    }

    public void fillGui(String[] scrambledText) {
        for (int i = 0; i < textViewArr.length; i++) {
            textViewArr[i].setText(scrambledText[i]);
            positions[i] = i;
        }
    }

    public int indexOfTextView(View tv) {
        if (!(tv instanceof TextView)) {
            return -1;
        }
        for (int i = 0; i < textViewArr.length; i++) {
            if (tv == textViewArr[i]) {
                return i;
            }
        }
        return -1;
    }

    public void updateStartPositions(int index, int y) {
        startY = params[index].topMargin;
        startTouchY = y;
        emptyPosition = tvPosition(index);
    }

    public void moveTextViewVertically(int index, int y) {
        params[index].topMargin = startY + y - startTouchY;
        textViewArr[index].setLayoutParams(params[index]);
    }

    public void enableListener(OnTouchListener listener) {
        for (int i = 0; i < textViewArr.length; i++) {
            textViewArr[i].setOnTouchListener(listener);
        }
    }

    public void disableListener() {
        for (int i = 0; i < textViewArr.length; i++) {
            textViewArr[i].setOnTouchListener(null);
        }
    }

    public int tvPosition(int tvIndex) {
        return (params[tvIndex].topMargin + labelHeight / 2) / labelHeight;
    }

    public void placeTextViewAtPosition(int tvIndex, int toPosition) {
        params[tvIndex].topMargin = toPosition * labelHeight;
        textViewArr[tvIndex].setLayoutParams(params[tvIndex]);

        int index = positions[toPosition];
        params[index].topMargin = emptyPosition * labelHeight;
        textViewArr[index].setLayoutParams(params[index]);

        positions[emptyPosition] = index;
        positions[toPosition] = tvIndex;
    }

    public String[] currentSolution() {
        String[] current = new String[textViewArr.length];
        for (int i = 0; i < current.length; i++) {
            current[i] = textViewArr[positions[i]].getText().toString();
        }

        return current;
    }
}
