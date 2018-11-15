package com.example.prathamdesai13.gamecentre;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

/**
 * The sliding tiles board.
 */
public class Board extends Observable implements Serializable, Iterable<Tile>{

    /**
     * The number of rows.
     */
    static int NUM_ROWS = 4; // 4 by default

    /**
     * The number of rows.
     */
    static int NUM_COLS = 4; // 4 by default

    /**
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles;

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    Board(List<Tile> tiles) {
        this.tiles = new Tile[NUM_ROWS][NUM_COLS];
        Iterator<Tile> iter = tiles.iterator();

        for (int row = 0; row != Board.NUM_ROWS; row++) {
            for (int col = 0; col != Board.NUM_COLS; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }

    /**
     * Return the number of tiles on the board.
     * @return the number of tiles on the board
     */
    private int numTiles() {

        return NUM_ROWS * NUM_COLS;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    void swapTiles(int row1, int col1, int row2, int col2) {
        // swaps pointers to two specific tiles
        if (row1 != row2 || col1 != col2){
            Tile tile1 = this.getTile(row1, col1);
            Tile tile2 = this.getTile(row2, col2);
            this.tiles[row1][col1] = tile2;
            this.tiles[row2][col2] = tile1;
        }

        setChanged();
        notifyObservers();
    }

    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @NonNull
    @Override
    public Iterator<Tile> iterator() {
        return new TileIterator();
    }

    /*
    * Iterator class over <Tile> objects, implements next and hasNext functions
    */
    private class TileIterator implements Iterator<Tile>{

        int pointer = 0; // points to tile in Tiles
        /**
         * Returns True if the iteration has more elements.
         */
        @Override
        public boolean hasNext() {
            return pointer != numTiles();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Tile next() {
            Tile tile = tiles[pointer / NUM_ROWS][pointer % NUM_COLS];
            pointer ++;
            return tile;
        }

    }
}


