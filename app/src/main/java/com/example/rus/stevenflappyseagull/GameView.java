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
    public static final int WIDTH = 807;
    public static final int HEIGHT = 274;


    Handler handler;
    Runnable runnable;
    final int UPDATE_MILLIS = 30;
    //later add backGround/ added
    Bitmap background;

    Background backgroundMovingImage;



    Bitmap carrotImage;
    //Bitmap bottomCarrot;

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
    int minCarrotOffset, maxCarrotOffset;
    int numberOfCarrots = 180;
    int distanceBetweenCarrots;
    int[] carrotX = new int[numberOfCarrots];
    int[] topCarrotY = new int[numberOfCarrots];

    Random random;
    int carrotVelocity = 8;


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
        backgroundMovingImage = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.testing_background));


        BitmapFactory.Options options2 = new BitmapFactory.Options();
        options2.inSampleSize = 18;
        carrotImage = BitmapFactory.decodeResource(getResources(), R.drawable.carrot, options2);
        //bottomCarrot = BitmapFactory.decodeResource(getResources(), R.drawable.carrot, options2);
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

        //distanceBetweenCarrots = dWidth* 3/4;
        //minCarrotOffset = gap/2;
        //maxCarrotOffset = dHeight- minCarrotOffset - gap;
        maxCarrotOffset = dHeight;
        random = new Random();

        for (int i = 0; i < numberOfCarrots; i++){
            distanceBetweenCarrots = random.nextInt(dWidth/4 - dWidth/10 + 1);
            carrotX[i] = dWidth + i* distanceBetweenCarrots;
            topCarrotY[i] = minCarrotOffset + random.nextInt(maxCarrotOffset - 0 + 2);
        }
        /*carrotX = dWidth/2 - carrotImage.getWidth()/2;
        topCarrotY = minCarrotOffset + random.nextInt(maxCarrotOffset-minCarrotOffset+1);*/

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //backgroundHere
        //canvas.drawBitmap(background,null,rect,null);
        final int savedState = canvas.save();
        //canvas.scale(getWidth()/WIDTH, getHeight()/HEIGHT);
        backgroundMovingImage.draw(canvas, rect);
        //canvas.restoreToCount(savedState);
        backgroundMovingImage.setVecoror(-5);
        backgroundMovingImage.update();

        if (birdFrame == 0){
            birdFrame = 1;
        } else {
            birdFrame = 0;
        }

        if (gameState) {
/*            if (birdY < dHeight - birds[0].getHeight() || velocity < 0) {
                velocity += gravity; // will it work?
                birdY += velocity;
            }*/

            //canvas.drawBitmap(carrotImage, carrotX, topCarrotY - carrotImage.getHeight(), null);
            //canvas.drawBitmap(bottomCarrot, carrotX, topCarrotY+gap, null);
            for (int i = 0; i < numberOfCarrots; i++){
                carrotX[i] -= carrotVelocity;
                canvas.drawBitmap(carrotImage, carrotX[i], topCarrotY[i] - carrotImage.getHeight(), null);
                //canvas.drawBitmap(bottomCarrot, carrotX[i], topCarrotY[i] + gap, null);
            }

        }
        //centerScreen  bird, probably
        canvas.drawBitmap(birds[birdFrame], birdX, birdY,null);
        handler.postDelayed(runnable, UPDATE_MILLIS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
 /*       if (action == MotionEvent.ACTION_DOWN){
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
