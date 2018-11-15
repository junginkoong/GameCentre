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
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class ScoreboardActivity extends Activity {
    /**
     * The name of the game
     */
    final static String TILEGAME = "tile_game";

    /**
     * The list of users
     */
    private ArrayList<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreboard);
        loadFromFile("save_file_tmp.ser");
        displayScoreboard(TILEGAME);
    }

    /**
     * Display the scoreboard.
     *
     * @param gamename Name of the game
     */
    public void displayScoreboard(String gamename){
        HashMap<String, Integer> list_scoreboard = (HashMap<String, Integer>)
                display(gamename);

        TextView username_display = findViewById(R.id.DisplayUsername);

        ArrayList<Integer> scores = new ArrayList<>();
        ArrayList<String> username = new ArrayList<>();
        sortMap(list_scoreboard, scores, username);
        String string_username = "";

        for (int i = 0; i < scores.size(); i++){
            string_username += String.format("%-25s %25s %n%n", Integer.toString(i + 1) + ". " + username.get(i), scores.get(i));
        }

        username_display.setText(string_username);


    }

    /**
     * Sort the HashMap containing users and their scores.
     * Helper function for displayScoreboard.
     *
     * @param list_scoreboard HashMap of username and scores
     * @param scores list of scores
     * @param username list of usernames
     */
    private void sortMap(HashMap<String, Integer> list_scoreboard, ArrayList<Integer> scores,
                         ArrayList<String> username){

        //temporary arraylist
        ArrayList<String> username_temp = new ArrayList<>();

        //assign values
        Set<String> set = list_scoreboard.keySet();
        for (String s: set){
            username_temp.add(s);
            scores.add(list_scoreboard.get(s));
        }

        //Sort the ArrayList
        Collections.sort(scores);

        //remove username and store it in another list
        for (int i = 0; i < scores.size(); i++) {
            for (String s : set) {
                if (list_scoreboard.get(s).equals(scores.get(i))){
                    username_temp.remove(s);
                    username.add(s);
                }
            }
        }
        //after these progress, score is in order as well as the username
    }

    /**
     * Statistic of a single user, per-Game scores.
     * i.e ordered scores for each game the user has played.
     *
     * @param gameName the name of each game
     */
    public HashMap<String, Integer> display (String gameName) {

        HashMap<String, Integer> Scoreboard = new HashMap<>();

        for (User U : userList) {

            if (U.getGameInfo().containsKey(gameName)) {
                Scoreboard.put(U.getUsername(),U.getGameInfo().get(gameName).get(0));
            }

        }
        return Scoreboard;
    }


    /**
     * Load the list of users from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                userList = (ArrayList<User>)input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

}



