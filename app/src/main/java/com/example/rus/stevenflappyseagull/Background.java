package com.example.rus.stevenflappyseagull;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Background {

    private Bitmap image;
    private int x, y, dx;

    public Background(Bitmap image) {
        this.image = image;
    }

    public void update(){
        x+=dx;
        if (x<-GameView.WIDTH){
            x=0;
        }
    }

    public void draw(Canvas canvas, Rect rect){
        canvas.drawBitmap(image, x, y, null);
        if (x<0){
            canvas.drawBitmap(image, x + GameView.WIDTH, y, null);
            //canvas.drawBitmap(image,null, rect, null);
        }
    }

    public void setVecoror(int dx){
        this.dx = dx;
    }
}
