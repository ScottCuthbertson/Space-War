package com.mcvicar.spacewar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.Random;

public class Enemy {
    Context context;
    Bitmap enemyShip;
    int x, y;
    int enemyXVelocity;
    int enemyYVelocity;
    Random random;
    ArrayList<Bullet> enemyBullets;
    boolean enemyBulletFired = false;

    public Enemy(Context context) {
        this.context = context;
        random = new Random();
        x = 200 + random.nextInt(400);
        y = 0;
        Bullet bullet;
        enemyBullets = new ArrayList<>();
    }

    public Bitmap getEnemyShip() {
        return enemyShip;
    }

    int getEnemyShipWidth(){
        return enemyShip.getWidth();
    }

    int getEnemyShipHeight(){
        return enemyShip.getHeight();
    }


}
