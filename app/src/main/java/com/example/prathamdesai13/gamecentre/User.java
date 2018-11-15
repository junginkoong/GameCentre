package com.example.prathamdesai13.gamecentre;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Collections;
import java.io.Serializable;

public class User implements Serializable{

    /**
     * The maximum number of scores stored for a user.
     */
    final static int SIZE_OF_SCORE = 10;

    /**
     * User selected profile name.
     */
    private String username;

    /**
     * User selected login password.
     */
    private String password;

    /**
     * User's board manager
     */
    private BoardManager boardManager;


    /**
     * Games and scores of each game played by a user.
     */
    private HashMap<String, ArrayList<Integer>> gameInfo = new HashMap<>();

    /**
     * List of usernames  of all users.
     */
    static ArrayList<String> usernameList = new ArrayList<>();

    public User (String username, String password){
        this.username = username;
        this.password = password;
        usernameList.add(username);
        updateGameInfo("tile_game", 0);
    }

    /**
     * Return username of user.
     * @return username
     */
    public String getUsername(){
        return username;
    }

    /**
     * Return password of user.
     * @return password
     */
    public String getPassword(){
        return password;
    }

    /**
     * Return board Manager of user.
     * @return boardManager
     */
    public BoardManager getBoardManager(){
        return boardManager;
    }

    /**
     * Set the users boardManager
     */
    public void setBoardManager(BoardManager boardManager){
        this.boardManager = boardManager;
    }

    /**
     * Return usernames of all users.
     * @return usernameList list of usernames
     */
    public ArrayList<String> getUsernameList(){
        return usernameList;
    }

    /**
     * Return population of users.
     * @return size of usernameList
     */
    public int getUserPopulation() {
        return usernameList.size();
    }

    /**
     * Return games and scores in those games for a user.
     * @return gameInfo HashMap of games and scores
     */
    public HashMap<String, ArrayList<Integer>> getGameInfo(){
        return gameInfo;
    }

    /**
     * Update GameInfo.
     *
     * @param gameName name of game
     * @param score score in game
     */
    public void updateGameInfo(String gameName, Integer score){
        if (gameInfo.containsKey(gameName)){
            //Assuming the size of arraylist is always 10
            if (gameInfo.get(gameName).get(SIZE_OF_SCORE - 1) < score){
                gameInfo.get(gameName).remove(SIZE_OF_SCORE - 1);
                gameInfo.get(gameName).add(score);
                Collections.sort(gameInfo.get(gameName));
            }
        } else{
            ArrayList<Integer> list_score = new ArrayList<>(SIZE_OF_SCORE);
            list_score.add(score);
            gameInfo.put(gameName, list_score);
        }
    }

}