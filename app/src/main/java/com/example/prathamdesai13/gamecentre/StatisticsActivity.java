package com.example.prathamdesai13.gamecentre;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Locale;

public class StatisticsActivity extends Activity {
    /**
     * The name of the game
     */
    final static String TILEGAME = "tile_game";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);

        //For multiple games, receive game name through intent and will display based on game name
        //Enable once we get sign in and sign up working
        //displayStat(TILEGAME);
    }

//    /**
//     * Display statistics.
//     *
//     * @param gameName Name of the game
//     */
//    private void displayStat(String gameName){
//
//        ArrayList<Integer> score = stats(user.getUsername(), gameName);
//
//        //Display Top 10 Score
//        TextView score_display = findViewById(R.id.Score);
//
//        String string_username = "";
//        for (int i = 0; i < score.size(); i++){
//            string_username += String.format("%-25s %n%n", Integer.toString(i + 1) + ". " + score.get(i));
//        }
//        score_display.setText(string_username);
//
//        //Display Average Score
//        TextView average_display = findViewById(R.id.Average);
//        Integer average = 0;
//        for (int i = 0; i < score.size(); i++){
//            average += score.get(i);
//        }
//
//        average_display.setText(String.format(Locale.CANADA, "%04d", average/score.size()));
//    }
//
//    /**
//     * Gets user stats
//     * @param user_name, user's name
//     * @param game_name Name of the game
//     * @return the stats
//     */
//    public ArrayList<Integer> stats (String user_name, String game_name){
//
//        for (User U: userList){
//            if (U.getUsername().equals(user_name)){
//                return U.getGameInfo().get(game_name);
//            }
//        }
//        return null;
//    }
}


