package com.mcvicar.spacewar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BulletOld {
    Bitmap bullet;
    Context context;
    int bX, bY;

    public BulletOld(Context context, int bX, int bY){
        this.context = context;
        bullet = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet);
        this.bX = bX;
        this.bY = bY;
    }

    public Bitmap getBullet() {
        return bullet;
    }

    public int getBulletWidth() {
        return bullet.getWidth();
    }

    public int getBulletHeight() {
        return bullet.getHeight();
    }
}
