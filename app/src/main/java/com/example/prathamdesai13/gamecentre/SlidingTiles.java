package com.example.prathamdesai13.gamecentre;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class SlidingTiles extends AppCompatActivity {

    /**
     * The board manager.
     */
    Firebase firebaseRef = new Firebase("https://gamecentre-c0c28.firebaseio.com/");
    Firebase slidingTilesRef;
    private BoardManager boardManager;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_starting_);
        slidingTilesRef = firebaseRef.child("Sliding Tile");
        loadBoardManager();
        addStartButtonListener();
        addLoadButtonListener();
        addComplexityButtonListener();
        addScoreboardButtonListener();
    }
    private void loadUser(){
        Intent i = getIntent();
        Bundle b = i.getExtras();
        user = (String) b.get("USERNAME");
    }
    private void loadBoardManager(){

        // load board manager if it exists for user, else create new board manager
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager = new BoardManager();
                switchToGame();
            }
        });
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeToastLoadedText();
                switchToGame();
            }
        });
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate complexity button
     */
    private void addComplexityButtonListener(){
        final Button complexityButton = findViewById(R.id.ComplexityButton);
        complexityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] seq = {"3", "4", "5"};
                AlertDialog.Builder listOfOptions = new AlertDialog.Builder(SlidingTiles.this);
                listOfOptions.setTitle(R.string.complexity_list);
                listOfOptions.setItems(seq, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int complexityChosen = which + 3;
                        boardManager.setBoardSizes(complexityChosen);
                    }

                });
                listOfOptions.show();
            }
        });
    }


    /**
     * Activate scoreboard button
     */
    private void addScoreboardButtonListener() {
        Button scorebutton = findViewById(R.id.ScoreButton);
        scorebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToScoreboardActivity();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, GameActivity.class);
        startActivity(tmp);
    }

    /**
     * Switch to the ScoreboardActivity to view scores
     */
    private void switchToScoreboardActivity(){
        Intent tmp = new Intent(this, ScoreboardActivity.class);
        startActivity(tmp);
    }

}