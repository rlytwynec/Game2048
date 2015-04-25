package com.example.riley.game2048;

import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.TextView;

/**
 * Project  :   Game2048 - Puzzle Game
 * Class    :   MainActivity.java
 * Function :   Hosts the main activity as well as code for accepting user input.
 * Variables:   DEBUG_TAG   - used to denote all debug logs in GameHandler
 *              thresh      - sets threshold in pixels. swipe distance must exceed threshold to
     *                      register as a directional command
 *              x1, y1      - x and y coordinates of starting position of swipe
 *              x2, y2      - x and y coordinates at end position of swipe
 * Methods  :   onCreate()  - initializes board with two randomly placed tiles
 *              onCreateOptionsMenu()   - expands options menu (...) when selected
 *              onOptionsItemSelected(MenuItem) - contains code for reset button
 *              onTouchEvent- calculates direction of swipe
 * Created by Riley on 4/21/2015.
 */
public class MainActivity extends ActionBarActivity {
    private final String DEBUG_TAG = "motion";
    private final int thresh = 4;
    private float x1, x2, y1, y2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GameHandler.setBoard();
        updateText();
        x1 = 0;
        y1 = 0;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            GameHandler.setBoard();
            updateText();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                y1 = event.getY();
                x1 = event.getX();
                //Log.d(DEBUG_TAG, "DOWN");
                //Log.d(DEBUG_TAG, "Y: " + event.getY());
                //Log.d(DEBUG_TAG, "X: " + event.getX());

                break;
            case (MotionEvent.ACTION_UP):
                y2 = event.getY();
                x2 = event.getX();
                Log.d(DEBUG_TAG, "Dir: " + calcDirection(x1, y1, x2, y2));
                GameHandler.move(calcDirection(x1, y1, x2, y2));
                updateText();
                //Log.d(DEBUG_TAG, "UP");
                //Log.d(DEBUG_TAG, "Y: " + event.getY());
                //Log.d(DEBUG_TAG, "X: " + event.getX());


                break;
        }
        return true;
    }

    private int calcDirection(float x1, float y1, float x2, float y2) {
        float dy = y2 - y1;
        float dx = x2 - x1;
        int dir;

        if (Math.abs(dx) > Math.abs(dy)) {
            if (dx > 0 && dx > thresh)
                dir = 1;
            else if (dx * -1 > thresh)
                dir = 3;
            else
                dir = -1;
        } else {
            if (dy > 0 && dy > thresh)
                dir = 2;
            else if (dy * -1 > thresh)
                dir = 0;
            else
                dir = -1;
        }
        return dir;
    }


    public void updateText() {
        int[][] board = GameHandler.getBoard();

        String row1 = String.format("%5d\n%5d\n%5d\n%5d",
                board[0][0], board[0][1], board[0][2], board[0][3]);
        String row2 = String.format("%5d\n%5d\n%5d\n%5d",
                board[1][0], board[1][1], board[1][2], board[1][3]);
        String row3 = String.format("%5d\n%5d\n%5d\n%5d",
                board[2][0], board[2][1], board[2][2], board[2][3]);
        String row4 = String.format("%5d\n%5d\n%5d\n%5d",
                board[3][0], board[3][1], board[3][2], board[3][3]);


        TextView textView = (TextView) findViewById(R.id.textView);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        TextView textView3 = (TextView) findViewById(R.id.textView3);
        TextView textView4 = (TextView) findViewById(R.id.textView4);

        textView.setText(row1);
        textView2.setText(row2);
        textView3.setText(row3);
        textView4.setText(row4);
    }



}