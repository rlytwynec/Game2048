package com.example.riley.game2048;

import android.util.Log;
import android.view.View;

/**
 * Project  :   Game2048 - Puzzle Game
 * Class    :   GameHandler.java
 * Function :   Accept directional moves from MainActivity and update the game board. Test for
 *              game over conditions and track points.
 * Variables:   DEBUG_TAG   - used to denote all debug logs in GameHandler
 *              board       - 4x4 integer array holding the values in each square of the board
 *              validMove   - true if a move will change the board in any way (false otherwise)
 * Methods  :   setBoard()  - initializes board with two randomly placed tiles
 *              move(int)   - accepts direction as input and updates board accordingly
 * Created by Riley on 4/21/2015.
 */
public class GameHandler{
    private static final String DEBUG_TAG = "board";    //debug tag for logs
    private static int[][] board = new int[4][4];       //contains the game board
    private static boolean validMove;                   //true if a move is valid(moves tiles)
    private static int score;
    private static int highScore = 0;


    /**
     * Method       :   setBoard()
     * Function     :   Initializes game board, clearing the board then placing two tiles randomly
     * Parameters   :   None
     * Returns      :   void
     */
    public static void resetBoard(){
        for(int x = 0; x < 4; x++)      //increment through columns
            for(int y = 0; y < 4; y++)  //increment through rows
                board[x][y] = 0;        //set value to 0
        score = 0;

        addTile();                     //add tile of value 2 to random location
        addTile();                     //add tile of value 2 to random location
        drawBoard();                    //draw the board
        draw();
    }


    /**
     * Method       :   move(int dir)
     * Function     :   Accepts directional input from MainActivity (0-UP 1-RIGHT 2-DOWN 3-LEFT
     * Parameters   :   @param dir - specifies direction of movement
     * Returns      :   void
     */
    public static void move(int dir){
        int loopCount;                          //counter for while loop (sets maximum)
        validMove = false;                      //true if move is valid (results in moved tiles)

        switch(dir){                            //switch for handling each direction
            case 0  :                           //case 0: UP
                for(int x = 0; x < 4; x++){     //scan left to right
                    for(int y = 0; y < 3; y++){ //scan top to bottom (slides tiles up)
                        if(board[x][y] == 0){   //test for empty space on board
                            loopCount = 0;
                            while (board[x][y] == 0 && loopCount < 3){ //shifts tiles up one space
                                for(int w = y; w < 3; w++) {
                                    if(board[x][(w + 1)] != 0 && board[x][w] == 0)
                                        validMove = true; //true if nonzero value is moved
                                    board[x][w] = board[x][(w + 1)];

                                }
                                board[x][3] = 0;//insert zero at top

                                loopCount++;
                            }
                        }
                    }

                    for(int y = 0; y < 3; y++){ //second scan checks for pairs of same number
                        if(board[x][y] == board[x][(y + 1)] && board[x][y] != 0){
                            validMove = true;   //move is valid if two matching tiles collide
                            for(int w = y; w < 3; w++) {    //combines pair & slides other tiles up
                                board[x][w] = board[x][(w + 1)];
                            }
                            board[x][3] = 0;   //sets bottom tile in column to zero
                            board[x][y] = board[x][y] * 2; //doubles tile
                            score += board[x][y];
                        }
                    }
                    draw();
                }
                break;

            case 1:                             //case 1: RIGHT
                for(int y = 0; y < 4; y++){     //scan rows top to bottom
                    for(int x = 3; x > 0; x--){ //scan columns right to left (slide tiles right)
                        if(board[x][y] == 0){   //if 0 tile found, slide left tiles right one space
                            loopCount = 0;
                            while (board[x][y] == 0 && loopCount < 3){
                                for(int w = x; w > 0; w--) {
                                    if(board[(w - 1)][y] != 0 && board[w][y] == 0)
                                        validMove = true;
                                    board[w][y] = board[(w - 1)][y];

                                }
                                board[0][y] = 0;

                                loopCount++;
                            }
                        }
                    }

                    for(int x = 3; x > 0; x--){ //second R-L scan checks for pairs
                        if(board[x][y] == board[(x - 1)][y] && board[x][y] != 0){
                            validMove = true;
                            for(int w = x; w > 0; w--) {
                                board[w][y] = board[(w - 1)][y];

                            }
                            board[0][y] = 0;
                            board[x][y] = board[x][y] * 2;
                            score += board[x][y];
                        }
                    }
                    draw();
                }
                break;

            case 2  :                           //case 2: DOWN
                for(int x = 0; x < 4; x++){     //scan columns left to right
                    for(int y = 3; y > 0; y--){ //scan rows bottom to top (move tiles down)
                        if(board[x][y] == 0){   //if empty space, move above tiles down one
                            loopCount = 0;
                            while (board[x][y] == 0 && loopCount < 3){
                                for(int w = y; w > 0; w--) {
                                    if(board[x][(w - 1)] != 0 && board[x][w] == 0) {
                                        validMove = true;
                                    }
                                    board[x][w] = board[x][(w - 1)];

                                }
                                board[x][0] = 0;

                                loopCount++;
                            }
                        }
                    }

                    for(int y = 3; y > 0; y--){ //second scan checks for adjacent nonzero pairs
                        if(board[x][y] == board[x][(y - 1)] && board[x][y] != 0){
                            validMove = true;
                            for(int w = y; w > 0; w--) {
                                board[x][w] = board[x][(w - 1)];

                            }
                            board[x][0] = 0;
                            board[x][y] = board[x][y] * 2;
                            score += board[x][y];
                        }
                    }
                    draw();
                }
                break;

            case 3  :                           //case 3: LEFT
                for(int y = 0; y < 4; y++){     //outer loop scans rows top to bottom
                    for(int x = 0; x < 3; x++){ //scans columns left to right (slide tiles left)
                        if(board[x][y] == 0){   //if empty slide all right tiles left one space
                            loopCount = 0;
                            while (board[x][y] == 0 && loopCount < 3){
                                for(int w = x; w < 3; w++) {
                                    if(board[(w + 1)][y] != 0 && board[w][y] == 0) {
                                        validMove = true;
                                    }
                                    board[w][y] = board[(w + 1)][y];

                                }
                                board[3][y] = 0;

                                loopCount++;
                            }
                        }
                    }

                    for(int x = 0; x < 3; x++){ //second scan checks for pairs
                        if(board[x][y] == board[(x + 1)][y] && board[x][y] != 0){
                            validMove = true;
                            for(int w = x; w < 3; w++) {
                                board[w][y] = board[(w + 1)][y];

                            }
                            board[3][y] = 0;
                            board[x][y] = board[x][y] * 2;
                            score += board[x][y];
                        }
                    }
                    draw();
                }
                break;
        }
        if(validMove)       //if the move was valid (tiles moved or pair made)
            addTile();     //add new tile in random empty space
        if(score > highScore){
            highScore = score;
        }
        drawBoard();
        draw();
    }


    /**
     * Method       :   addTile
     * Function     :   Add a tile in a random empty space
     * Parameters   :   @param val - integer value to be added (2 or 4)
     * Returns      :   void
     */
    private static void addTile(){
        int val;
        int[] empty = new int[16];  //holds the integer values of empty spaces (left-right, top-bot)
        int countCells = 0;         //tracks current cell number during scan
        int countZeros = 0;         //tracks amount of empty cells on board
        int selectCell;             //randomly selected cell

        if(Math.random() > 0.9)
            val = 4;
        else
            val = 2;
        for(int y = 0; y < 4; y++)      //scan rows top to bottom
            for(int x = 0; x < 4; x++)  //scan columns left to right
            {
                if(board[x][y] == 0) {  //if empty cell found
                    empty[countZeros] = countCells; //add cell number to empty list
                    countZeros++;       //increment amount of zeros
                }
                countCells++;
            }
        if(countZeros > 0) {            //if there are empty spaces available
            selectCell = empty[(int) (Math.random() * countZeros)]; //randomly select cell
            board[selectCell % 4][selectCell / 4] = val;            //populate with value
        }
    }


    /**
     * Method       :   isGameOver
     * Function     :   Returns true if game is over (board filled and no remaining pairs available)
     * Parameters   :   None
     * Returns      :   @return gameOver - true if game is over
     */
    public static boolean isGameOver(){
        for(int y = 0; y < 4; y++)      //scan board for empty spaces
            for(int x = 0; x < 4; x++)
                if(board[x][y] == 0)
                    return false;       //if empty space found, game not over
        for(int y = 0; y < 4; y++)      //scan rows for adjacent pairs
            for(int x = 0; x < 3; x++)
                if(board[x][y] == board[(x + 1)][y])
                    return false;       //if pair found, game not over
        for(int x = 0; x < 4; x++)      //scan columns for adjacent pairs
            for(int y = 0; y < 3; y++)
                if(board[x][y] == board[x][(y + 1)])
                    return false;       //if pair found, game not over
        return true;                    //if none of the above tests returned true, GAME OVER MAN!
    }


    /**
     * Method       :   drawBoard()
     * Function     :   Currently outputs board to debug log - will animate screen later
     * Parameters   :   None
     * Returns      :   void
     */
    private static void drawBoard(){
        for(int i = 0; i < 4; i++)
            Log.d(DEBUG_TAG, "" + board[0][i] + "\t" + board[1][i] + "\t" + board[2][i] + "\t"
                    + board[3][i]);
        Log.d(DEBUG_TAG, "----");
    }


    public static void setBoard(int[][] b){
        board = b;
    }


    public static void setScore(int s){
        score = s;
    }


    public static void setHighScore(int h){
        highScore = h;
    }


    /**
     * Method       :   getBoard()
     * Function     :   Returns board array
     * Parameters   :   None
     * Returns      :   @return board
     */
    public static int[][] getBoard(){
        return board;
    }


    public static int getScore() { return score; }


    public static int getHighScore() { return highScore; }


    public static void draw(){
        MainActivity.customCanvas.invalidate();
        Log.d("board", "DRAW REQUEST SENT");
    }
}
