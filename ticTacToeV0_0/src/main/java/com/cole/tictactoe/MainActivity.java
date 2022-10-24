package com.cole.tictactoe;

import android.graphics.Point;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button [][] inputButtons;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        buildGui();
    }

    public void buildGui() {
        // Get screen size
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);

        // Create the GridLayout manager
        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setColumnCount(TicTacToe.SIDE);
        gridLayout.setRowCount(TicTacToe.SIDE);

        // Setup Buttons
        inputButtons = new Button[TicTacToe.SIDE][TicTacToe.SIDE];
        int dimen = size.x / TicTacToe.SIDE;
        for(int row = 0; row < TicTacToe.SIDE; row++) {
            for(int col = 0; col < TicTacToe.SIDE; col++) {
                inputButtons[row][col] = new Button( this);
                gridLayout.addView(inputButtons[row][col], dimen, dimen);
            }
        }

        // Set gridLayout to ActivityView
        setContentView(gridLayout);
    }
}