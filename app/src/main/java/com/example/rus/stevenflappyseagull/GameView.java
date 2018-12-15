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

import java.util.Random;

public class GameView extends View {

    Handler handler;
    Runnable runnable;
    final int UPDATE_MILLIS = 30;
    //later add backGround/ added
    Bitmap background;

    Bitmap topTube;
    //Bitmap bottomTube;

    Display display;
    Point point;
    int dWidth, dHeight; //device parameters
    Rect rect;
    Bitmap[] birds;
    int birdFrame;
    int velocity = 0, gravity = 5; // testing
    int birdX, birdY;


    boolean gameState = false; //testing
    int gap = 400;
    int minTubeOffset, maxTubeOffset;
    int numberOfTubes = 180;
    int distanceBetweenTubes;
    int[] tubeX = new int[numberOfTubes];
    int[] topTubeY = new int[numberOfTubes];

    Random random;
    int tubeVelocity = 8;


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

        BitmapFactory.Options options2 = new BitmapFactory.Options();
        options2.inSampleSize = 18;
        topTube = BitmapFactory.decodeResource(getResources(), R.drawable.carrot, options2);
        //bottomTube = BitmapFactory.decodeResource(getResources(), R.drawable.carrot, options2);
        // code below for background too
        display = ((AppCompatActivity)getContext()).getWindowManager().getDefaultDisplay();
        point = new Point();
        display.getSize(point);
        dWidth = point.x;
        dHeight = point.y;
        rect = new Rect(0,0,dWidth,dHeight);


        birds = new Bitmap[2];

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 6;
        birds[0] = BitmapFactory.decodeResource(getResources(), R.drawable.steavenflappyseagullstand, options);
        birds[1] = BitmapFactory.decodeResource(getResources(), R.drawable.steavenflappyseagull, options);
        birdX = dWidth/8 - birds[0].getWidth()/2;
        birdY = dHeight/2-birds[0].getHeight()/2;

        //distanceBetweenTubes = dWidth* 3/4;
        //minTubeOffset = gap/2;
        //maxTubeOffset = dHeight- minTubeOffset - gap;
        maxTubeOffset = dHeight;
        random = new Random();

        for (int i = 0; i < numberOfTubes; i++){
            distanceBetweenTubes = random.nextInt(dWidth/4 - dWidth/10 + 1);
            tubeX[i] = dWidth + i*distanceBetweenTubes;
            topTubeY[i] = minTubeOffset + random.nextInt(maxTubeOffset - 0 + 2);
        }
        /*tubeX = dWidth/2 - topTube.getWidth()/2;
        topTubeY = minTubeOffset + random.nextInt(maxTubeOffset-minTubeOffset+1);*/

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

        if (gameState) {
           /* if (birdY < dHeight - birds[0].getHeight() || velocity < 0) {
                velocity += gravity; // will it work?
                birdY += velocity;
            }*/

            //canvas.drawBitmap(topTube, tubeX, topTubeY - topTube.getHeight(), null);
            //canvas.drawBitmap(bottomTube, tubeX, topTubeY+gap, null);
            for (int i = 0; i < numberOfTubes; i++){
                tubeX[i] -= tubeVelocity;
                canvas.drawBitmap(topTube, tubeX[i], topTubeY[i] - topTube.getHeight(), null);
                //canvas.drawBitmap(bottomTube, tubeX[i], topTubeY[i] + gap, null);
            }

        }
        //centerScreen  bird, probably
        canvas.drawBitmap(birds[birdFrame], birdX, birdY,null);
        handler.postDelayed(runnable, UPDATE_MILLIS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
/*        if (action == MotionEvent.ACTION_DOWN){
            velocity = -40; //testing
            gameState = true;
        }*/

        if (action == MotionEvent.ACTION_MOVE){
            birdY = (int) event.getY();
            gameState = true;

        }

        return true;
    }
}
