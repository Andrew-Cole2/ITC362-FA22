package com.cole.tictactoe1_0;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    private Button [][] inputButtons;
    private TextView output;
    private int dimen;
    private boolean horizontal;

    private TicTacToe gameBoard;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        // Get screen size
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        super.onCreate( savedInstanceState );
        gameBoard = new TicTacToe();
        horizontal = false;
        buildGui();
        Resources res = getResources();
        DisplayMetrics metrics = res.getDisplayMetrics();
        dimen = size.x / TicTacToe.SIDE;
        Configuration config = getResources().getConfiguration();
        modifyLayout(config);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i("ConfigChange", "Config changed");
        modifyLayout(newConfig);
    }

    public void buildGui() {
        // Get screen size
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        dimen = size.x / TicTacToe.SIDE;

        // Create the GridLayout manager
        gridLayout = new GridLayout(this);
        gridLayout.setColumnCount(TicTacToe.SIDE);
        gridLayout.setRowCount(TicTacToe.SIDE+1);

        // Setup Buttons
        inputButtons = new Button[TicTacToe.SIDE][TicTacToe.SIDE];
        ButtonHandler buttonHandler = new ButtonHandler();

        for(int row = 0; row < TicTacToe.SIDE; row++) {
            for(int col = 0; col < TicTacToe.SIDE; col++) {
                // Build button component
                Button button = new Button( this);
                button.setTextSize((int)(dimen * .2));
                button.setOnClickListener(buttonHandler);
                gridLayout.addView(button, dimen, dimen);
                inputButtons[row][col] = button;
            }
        }

        // Setup Output Layout
        output = new TextView(this);
        GridLayout.Spec rowSpec = GridLayout.spec(TicTacToe.SIDE, 1);
        GridLayout.Spec colSpec = GridLayout.spec(0, TicTacToe.SIDE);
        GridLayout.LayoutParams outputParams = new GridLayout.LayoutParams(rowSpec, colSpec);

        // Setup Output Component
        output.setLayoutParams(outputParams);
        output.setWidth(TicTacToe.SIDE * dimen);
        output.setHeight(dimen);
        output.setGravity(Gravity.CENTER);
        output.setBackgroundColor(Color.GREEN);
        output.setTextSize((int)(dimen * .1));
        output.setText(gameBoard.result());
        output.setWidth(TicTacToe.SIDE * dimen);
        output.setHeight(dimen);
        gridLayout.addView(output);

        // Set gridLayout to Activity View
        setContentView(gridLayout);
    }

    private void update(int row, int col) {
        Log.i("update", "Updating: [" + row + "," + col + "]");
        int play = gameBoard.play(row, col);
        String p1, p2;
        if (horizontal) {
            p1 = "A";
            p2 = "Z";
        }
        else {
            p1 = "X";
            p2 = "O";
        }
        // Perform player 1 action
        if (play == 1) {
            inputButtons[row][col].setText(p1);
        }
        // Perform player 2 action
        if (play == 2) {
            inputButtons[row][col].setText(p2);
        }
        // Disable game board buttons
        if (gameBoard.isGameOver()) {
            enableButtons(false);
            output.setBackgroundColor(Color.RED);
            showNewGameDialog();
        }
        output.setText(gameBoard.result());
    }

    private void showNewGameDialog() {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Game Ended");
        alert.setMessage("Play again?");
        PlayDialog playAgain = new PlayDialog();
        alert.setPositiveButton("YES", playAgain);
        alert.setNegativeButton("NO", playAgain);
        alert.show();
    }

    private void enableButtons(boolean enable) {
        Log.i("enableButtons", (enable) ? "Enabling All Buttons":"Disabling All Buttons");
        for(int row = 0; row < TicTacToe.SIDE; row++) {
            for(int col = 0; col < TicTacToe.SIDE; col++) {
                inputButtons[row][col].setEnabled(enable);
                // If buttons are being re-enabled, reset button text
                if (enable) {
                    inputButtons[row][col].setText("");
                }
            }
        }
    }

    public void setLayoutMargins(int layoutSpacing, int dimen) {
        Log.i("setLayoutMargin", "Setting dimentions to: " + dimen);
        for(int row = 0; row < TicTacToe.SIDE; row++) {
            for (int col = 0; col < TicTacToe.SIDE; col++) {
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) inputButtons[row][col].getLayoutParams();
                params.width = dimen;
                params.height = dimen;
                inputButtons[row][col].setTextSize((int)(dimen * .2));
                inputButtons[row][col].setLayoutParams(params);
            }
        }
        Log.i("setLayoutMargin", "Setting grid layout");
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) gridLayout.getLayoutParams();
        params.setMargins(layoutSpacing, 0, layoutSpacing, 0);
        gridLayout.setLayoutParams(params);
        Log.i("setLayoutMargin", "Setting options layout ");
        params = (ViewGroup.MarginLayoutParams) output.getLayoutParams();
        params.width = (TicTacToe.SIDE * dimen);
        params.height = (dimen);
        output.setLayoutParams(params);
        output.setTextSize((int)(dimen * .1));
    }

    public void modifyLayout(Configuration config) {
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setLayoutMargins(size.x/3, dimen/3);
            horizontal = true;
        }
        else if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setLayoutMargins(0, dimen);
            horizontal = false;
        }
    }

    // Button Handler for player action
    private class ButtonHandler implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Log.i("onClick", "Handling click event for " + view);
            for(int row = 0; row < TicTacToe.SIDE; row++) {
                for(int col = 0; col < TicTacToe.SIDE; col++) {
                    if(view == inputButtons[row][col]) {
                        update(row,col);
                    }
                }
            }
        }
    }

    private class PlayDialog implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int id) {
            // Play Again
            if (id == -1) {
                gameBoard.resetGame();
                output.setBackgroundColor(Color.GREEN);
                enableButtons(true);
                output.setText(gameBoard.result());
            }
            // Quit
            if (id == -2) {
                MainActivity.this.finish();
            }

        }
    }
}