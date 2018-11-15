package com.example.prathamdesai13.gamecentre;

import java.util.HashMap;
import java.util.Map;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * The game activity.
 */
public class GameActivity extends AppCompatActivity implements Observer {

    /**
     * The board manager.
     */
    private BoardManager boardManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * Constants for swiping directions. Should be an enum, probably.
     * Never used => Dead code
     */
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;

    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    /**
     * Boolean for if the music is playing
     **/
    private boolean musicOn = false;


    /**
     * The main save file for user boards.
     */
    public static final String SAVE_FILENAME = "save_file.ser";

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        updateMoveCountTextView();
        updateTileButtons();
        updateUserScoreTextView();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boardManager = new BoardManager();
        createTileButtons(this);
        setContentView(R.layout.activity_game);
        addUndoButtonListener();
        addSaveButtonListener();
        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(Board.NUM_COLS);
        gridView.setBoardManager(boardManager);
        boardManager.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight() - GestureDetectGridView.HEIGHT_DIFF;

                        columnWidth = displayWidth / Board.NUM_COLS;
                        columnHeight = displayHeight / Board.NUM_ROWS;

                        display();
                    }
                });

    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        Board board = boardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != Board.NUM_ROWS; row++) {
            for (int col = 0; col != Board.NUM_COLS; col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeToastSavedText();
            }
        });
    }

    /**
     * Display that a game was saved successfully.
     */
    private void makeToastSavedText() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }
    /**
     * Updates Move count text view per move
     */
    private void updateMoveCountTextView(){
        TextView moveCount = findViewById(R.id.MoveCount);
        String disp = "Moves".concat(Integer.toString(boardManager.getNumMoves()));
        moveCount.setText(disp);
    }

    /**
     * Updates user score text per move
     */
    private void updateUserScoreTextView(){
        TextView userScore = findViewById(R.id.UserScore);
        String disp = "Score: ".concat(Integer.toString(boardManager.score));
        userScore.setText(disp);
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        Board board = boardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / Board.NUM_ROWS;
            int col = nextPos % Board.NUM_COLS;
            b.setBackgroundResource(board.getTile(row, col).getBackground());
            nextPos++;
            if(boardManager.getNumMoves() % 10 == 0){
                // autosave functionality here
                int x = 0;
            }
        }
    }

    /**
     * Activate the undo button.
     */
    private void addUndoButtonListener() {
        Button undoButton= findViewById(R.id.UndoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // undo last move
                boardManager.undoMove();
            }
        });
    }

    /**
     * Go back
     */
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
    }
}