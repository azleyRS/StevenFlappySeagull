package com.example.rus.stevenflappyseagull;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {

    Handler handler;
    Runnable runnable;
    final int UPDATE_MILLIS = 30;
    //later add backGround/ added
    Bitmap background;
    Display display;
    Point point;
    int dWidth, dHeight; //device parameters
    Rect rect;
    Bitmap[] birds;
    int birdFrame;
    int velocity = 0, gravity = 5; // testing
    int birdX, birdY;


    public GameView(Context context) {
        super(context);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate(); //onDraw() call
            }
        };

        background = BitmapFactory.decodeResource(getResources(), R.drawable.testbackground);
        // code below for background too
        display = ((AppCompatActivity)getContext()).getWindowManager().getDefaultDisplay();
        point = new Point();
        display.getSize(point);
        dWidth = point.x;
        dHeight = point.y;
        rect = new Rect(0,0,dWidth,dHeight);


        birds = new Bitmap[2];

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        birds[0] = BitmapFactory.decodeResource(getResources(), R.drawable.steavenflappyseagullstand, options);
        birds[1] = BitmapFactory.decodeResource(getResources(), R.drawable.steavenflappyseagull, options);
        birdX = dWidth/4 - birds[0].getWidth()/2;
        birdY = dHeight/2-birds[0].getHeight()/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //backgroundHere
        canvas.drawBitmap(background,null,rect,null);

        if (birdFrame == 0){
            birdFrame = 1;
        } else {
            birdFrame = 0;
        }

        if (birdY < dHeight - birds[0].getHeight() || velocity < 0) {
            velocity += gravity; // will it work?
            birdY += velocity;
        }

        //centerScreen  bird, probably
        canvas.drawBitmap(birds[birdFrame], birdX, birdY,null);
        handler.postDelayed(runnable, UPDATE_MILLIS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN){
            velocity = -40; //testing
        }


        return true;
    }
}
