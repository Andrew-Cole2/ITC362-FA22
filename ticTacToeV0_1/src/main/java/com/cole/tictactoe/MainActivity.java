package com.cole.tictactoe;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
        ButtonHandler buttonHandler = new ButtonHandler();

        int dimen = size.x / TicTacToe.SIDE;
        for(int row = 0; row < TicTacToe.SIDE; row++) {
            for(int col = 0; col < TicTacToe.SIDE; col++) {
                Button button = new Button( this);
                button.setTextSize((int)(dimen * .2));
                button.setOnClickListener(buttonHandler);
                gridLayout.addView(button, dimen, dimen);
                inputButtons[row][col] = button;
            }
        }

        // Set gridLayout to Activity View
        setContentView(gridLayout);
    }

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

        private void update(int row, int col) {
            Log.i("update", "Updating: [" + row + "," +col + "]");
            inputButtons[row][col].setText("X");
        }
    }
}