package com.cole.hangmanv2;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private Hangman game;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        if ( game == null )
            game = new Hangman( Hangman.DEFAULT_GUESSES );
        setContentView( R.layout.activity_main );
        TextView status = ( TextView ) findViewById( R.id.status );
        status.setText( "" + game.getGuessesLeft( ) );

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        GameStateFragment fragment = new GameStateFragment();
        ft.add(R.id.game_state, fragment);
        ft.commit();


    }


    public Hangman getGame( ) {
        return game;
    }

    public void play( View view ) {
    }
}