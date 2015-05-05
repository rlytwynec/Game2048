package com.example.riley.game2048;


import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.StringTokenizer;


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
    private final int thresh = 10;
    private float x1, x2, y1, y2;
    public static CanvasView customCanvas;
    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;
    private static final int PREFERENCE_MODE_PRIVATE = 0;
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customCanvas = (CanvasView) findViewById(R.id.signature_canvas);
        preferenceSettings = getPreferences(PREFERENCE_MODE_PRIVATE);
        preferenceEditor = preferenceSettings.edit();



        if (preferenceSettings.getInt("high", -1) > 0)
        {
            GameHandler.setHighScore(preferenceSettings.getInt("high", -1));
            GameHandler.setScore(preferenceSettings.getInt("score", -1));
            GameHandler.setBoard(decodeBoard(preferenceSettings.getString("board", "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0")));
            GameHandler.draw();
        }
        else
            GameHandler.setBoard();

        updateScore(GameHandler.getScore(), GameHandler.getHighScore());
        x1 = 0;
        y1 = 0;

    }


    @Override
    protected void onPause(){
        super.onPause();
        preferenceEditor.putInt("high", GameHandler.getHighScore());
        preferenceEditor.putInt("score", GameHandler.getScore());
        preferenceEditor.putString("board", encodeBoard(GameHandler.getBoard()));
        preferenceEditor.commit();
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
            updateScore(GameHandler.getScore(), GameHandler.getHighScore());
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
                updateScore(GameHandler.getScore(), GameHandler.getHighScore());
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

    private void updateScore(int s, int h){
        TextView score;
        TextView high;
        score = (TextView) findViewById(R.id.score);
        high = (TextView) findViewById(R.id.high);
        score.setText("Score:\n" + s);
        high.setText("High Score:\n" + h);
    }


    private String encodeBoard(int[][] b){
        String s = "";
        for(int x = 0; x < 4; x++)
            for(int y = 0; y < 4; y++)
                s += b[x][y] + ",";
        return s;
    }


    private int[][] decodeBoard(String s){
        int[][] b = new int[4][4];
        StringTokenizer st = new StringTokenizer(s, ",");
        for(int x = 0; x < 4; x++)
            for(int y = 0; y < 4; y++)
                b[x][y] = Integer.parseInt(st.nextToken());
        return b;
    }
}