package com.example.prathamdesai13.gamecentre;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 * Creates a new boardmanager for each new user
 */
class BoardManager implements Serializable {

    /**
     * The board being managed.
     */
    private Board board;

    private Stack<Integer> moveHistory;

    private int numMoves = 0;
    private boolean undid;
    public int score = 150;
    private int blankId;
    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    BoardManager(Board board) {
        this.board = board;
    }

    /**
     * Return the current board.
     */
    Board getBoard() {
        return board;
    }

    /**
     * Manage a new shuffled board.
     */
    BoardManager() {
        this.moveHistory = new Stack<>();
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = Board.NUM_ROWS * Board.NUM_COLS;
        for (int tileNum = 0; tileNum != numTiles - 1; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        tiles.add(new Tile(-1)); // adding the blank tile
        this.blankId = tiles.get(tiles.size() - 1).getId();
        //Collections.shuffle(tiles); // uncomment to test puzzleSolved() function
        this.board = new Board(tiles);
    }

    void undoMove(){

        if (this.moveHistory.size() > 0){
            undid = true;
            this.touchMove(this.moveHistory.pop());
        }

    }

    /**
     * Sets board size using complexity argument chosen by user
     */
    void setBoardSizes(int complexity){

        Board.NUM_ROWS = complexity;
        Board.NUM_COLS = complexity;
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    boolean puzzleSolved() {

        int id = 1; // ids start at 1s
        int n = Board.NUM_COLS * Board.NUM_ROWS;
        final Iterator<Tile> tileIterator = board.iterator();
        while (tileIterator.hasNext()) {
            Tile tile = tileIterator.next();
            if (tile.getId() != id && id != n) {
                return false;
            }
            id++;
        }
        return true;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    boolean isValidTap(int position) {

        int row = position / Board.NUM_ROWS;
        int col = position % Board.NUM_COLS;

        // Are any of the 4 the blank tile?
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == Board.NUM_ROWS - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == Board.NUM_COLS - 1 ? null : board.getTile(row, col + 1);

        return (below != null && below.getId() == this.blankId)
                || (above != null && above.getId() == this.blankId)
                || (left != null && left.getId() == this.blankId)
                || (right != null && right.getId() == this.blankId);

    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    void touchMove(int position) {

        int row = position / Board.NUM_ROWS;
        int col = position % Board.NUM_COLS;

        int rowToBeSwapped = 0;
        int colToBeSwapped = 0;

        // tiles is the blank tile, swap by calling Board's swap method.
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == Board.NUM_ROWS - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == Board.NUM_COLS - 1 ? null : board.getTile(row, col + 1);
        // If any of the tiles is the blank one, then it will validate a swap

        if (above != null && above.getId() == this.blankId) {
            this.board.swapTiles(row, col, row - 1, col);
            rowToBeSwapped = row - 1;
            colToBeSwapped = col;

        } else if (below != null && below.getId() == this.blankId) {
            this.board.swapTiles(row, col, row + 1, col);
            rowToBeSwapped = row + 1;
            colToBeSwapped = col;

        } else if (right != null && right.getId() == this.blankId) {
            this.board.swapTiles(row, col, row, col + 1);
            rowToBeSwapped = row;
            colToBeSwapped = col + 1;

        } else if (left != null && left.getId() == this.blankId) {
            this.board.swapTiles(row, col, row, col - 1);
            rowToBeSwapped = row;
            colToBeSwapped = col - 1;
        }

        if(undid){
            undid = false;
        }else{
            this.moveHistory.push(Board.NUM_ROWS * rowToBeSwapped + colToBeSwapped);
        }

        this.numMoves ++;
        this.score --;
    }

    int getNumMoves(){
        return this.numMoves;
    }

}
