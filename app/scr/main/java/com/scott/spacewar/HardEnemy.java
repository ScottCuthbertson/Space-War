package com.mcvicar.spacewar;

import android.content.Context;
import android.graphics.BitmapFactory;

public class HardEnemy extends Enemy{

    int enemyXVelocity;
    int enemyYVelocity;

    public HardEnemy(Context context) {
        super(context);
        enemyXVelocity = 15;
        enemyYVelocity = 15;
        enemyShip = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemyship_hard);
    }

    public int xMovement() {
        int  mvX = x + enemyXVelocity;

        return mvX;
    }

    public int yMovement() {
        int mvY = y + enemyYVelocity;

        return mvY;
    }

    public void checkX(int screenWidth){
        //if the ship hits the right wall reverse velocity
        if(x + getEnemyShipWidth() >= screenWidth){
            enemyXVelocity *= -1;
        }

        if(x <=0){
            enemyXVelocity *= -1;
        }
    }

    public void checkY(int screenHeight){
        if(y + getEnemyShipWidth() >= screenHeight){
            enemyYVelocity *= -1;
        }

        if(y <=0){
            enemyYVelocity *= -1;
        }
    }


}
