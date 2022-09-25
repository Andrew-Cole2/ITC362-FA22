package com.cole.tictactoe;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button [][] inputButtons;
    private TextView output;

    private TicTacToe gameBoard;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        gameBoard = new TicTacToe();
        buildGui();
    }

    public void buildGui() {
        // Get screen size
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);

        // Create the GridLayout manager
        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setColumnCount(TicTacToe.SIDE);
        gridLayout.setRowCount(TicTacToe.SIDE+1);

        // Setup Buttons
        inputButtons = new Button[TicTacToe.SIDE][TicTacToe.SIDE];
        ButtonHandler buttonHandler = new ButtonHandler();

        int dimen = size.x / TicTacToe.SIDE;
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
        gridLayout.addView(output);

        // Set gridLayout to Activity View
        setContentView(gridLayout);
    }

    private void update(int row, int col) {
        Log.i("update", "Updating: [" + row + "," + col + "]");
        int play = gameBoard.play(row, col);
        // Perform player 1 action
        if (play == 1) {
            inputButtons[row][col].setText("X");
        }
        // Perform player 2 action
        if (play == 2) {
            inputButtons[row][col].setText("O");
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