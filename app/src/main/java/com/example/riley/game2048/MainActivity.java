package com.example.riley.game2048;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.TextView;
import android.view.View;


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
    public static CanvasView customCanvas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customCanvas = (CanvasView) findViewById(R.id.signature_canvas);
        GameHandler.setBoard();
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
        if (id == R.id.action_reset) {
            GameHandler.setBoard();
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
                break;
            case (MotionEvent.ACTION_UP):
                y2 = event.getY();
                x2 = event.getX();
                Log.d(DEBUG_TAG, "Dir: " + calcDirection(x1, y1, x2, y2));
                GameHandler.move(calcDirection(x1, y1, x2, y2));
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


    public static void draw(){
        //customCanvas.invalidate();
        //Log.d("board", "DRAW REQUEST");
    }
}