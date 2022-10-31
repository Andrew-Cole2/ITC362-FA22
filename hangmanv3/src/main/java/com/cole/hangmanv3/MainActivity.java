package com.cole.hangmanv3;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private Hangman game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (game == null) {
            game = new Hangman(Hangman.DEFAULT_GUESSES);
        }
        setContentView(R.layout.activity_main);
        TextView status = (TextView) findViewById(R.id.status);
        status.setText("" + game.getGuessesLeft());

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentById(R.id.game_state) == null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            GameStateFragment fragment = new GameStateFragment();
            transaction.add(R.id.game_state, fragment);
            transaction.commit();
        }

        if (fragmentManager.findFragmentById(R.id.game_result) == null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            GameResultFragment fragment = new GameResultFragment();
            transaction.add(R.id.game_result, fragment);
            transaction.commit();
        }

    }

    public Hangman getGame() {
        return game;
    }

    public void play(View view) {
    }
}