package com.example.riley.game2048;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import java.util.Arrays;

public class CanvasView extends View {

    public int width;
    public int height;
    private Bitmap image;
    Context context;


    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;
    }

    // override onDraw
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int board[][] = GameHandler.getBoard();
        int stepSize = canvas.getWidth()/4;
        int imgSize = stepSize - (stepSize/10);
        int xCoord = stepSize/20;
        int yCoord = stepSize/20;

        int[] value = {0, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192};
        int[] img = {R.drawable.i0, R.drawable.i2, R.drawable.i4, R.drawable.i8, R.drawable.i16
                , R.drawable.i32, R.drawable.i64, R.drawable.i128, R.drawable.i256, R.drawable.i512
                , R.drawable.i1024, R.drawable.i2048, R.drawable.i4096, R.drawable.i8192};



        Paint paint=new Paint();
        // Use Color.parseColor to define HTML colors
        paint.setColor(Color.parseColor("#CD5C5C"));

        Log.d("board", "DRAWING");
        canvas.drawColor(Color.parseColor("#CBCCC5"));
        for(int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for(int i = 0; i < 14; i++) {
                    if (board[x][y] == value[i]) {
                        image = BitmapFactory.decodeResource(getResources(), img[i]);
                        image = Bitmap.createScaledBitmap(image, imgSize, imgSize, false);
                        canvas.drawBitmap(image, xCoord, yCoord, paint);
                        break;
                    }
                }
                yCoord += stepSize;
            }
            xCoord += stepSize;
            yCoord = stepSize/20;
        }
    }
}
