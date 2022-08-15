package com.mcvicar.spacewar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class SpaceWar extends View{
    Context context;
    Bitmap background, lifeImage;
    Handler handler;
    long UPDATE_MILLIS =30;
    static int screenWidth, screenHeight;
    boolean paused = false;
    HardEnemy enemyShip;
    NormalEnemy enemyNormal;
    Player player;
    Random random;
    Explosion explosion;
    ArrayList<Explosion> explosions;
    ArrayList<Bullet> enemyBullets, playerBullets;
    boolean enemyBulletFired = false;
    int points = 0;
    int lives = 3;
    int TEXT_SIZE = 80;
    Paint scorePaint;

    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    public SpaceWar(Context context){
        super(context);
        this.context = context;
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        random = new Random();
        explosions = new ArrayList<>();
        enemyShip = new HardEnemy(context);
        enemyNormal = new NormalEnemy(context);
        player = new Player(context);
        handler = new Handler();

        enemyBullets = new ArrayList<>();
        playerBullets = new ArrayList<>();

        lifeImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.life);
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);

        scorePaint = new Paint();
        scorePaint.setColor(Color.RED);
        scorePaint.setTextSize(TEXT_SIZE);
        scorePaint.setTextAlign(Paint.Align.LEFT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //draw background lives and points
        canvas.drawBitmap(background, 0, 0, null);
        canvas.drawText("Pt: " + points, 0, TEXT_SIZE, scorePaint);
        for(int i=lives; i>=1; i--){
            canvas.drawBitmap(lifeImage, screenWidth - lifeImage.getWidth() * i, 0, null);
        }

        //when player loses all their lives the game stops and shows GameOver Screen
        if(lives == 0){
            paused = true;
            handler = null;
            Intent intent = new Intent(context, GameOver.class);
            intent.putExtra("points", points);
            context.startActivity(intent);
            ((Activity) context).finish();
        }


        //move hard enemy ship
        enemyShip.x = enemyShip.xMovement();
        enemyShip.y = enemyShip.yMovement();
        enemyShip.checkX(screenWidth);
        enemyShip.checkY(screenHeight);

        //move normal enemy ship
        enemyNormal.x = enemyNormal.xMovement();
        enemyNormal.y = enemyNormal.yMovement();
        enemyNormal.checkX(screenWidth);
        enemyNormal.checkY(screenHeight);

        // Draw the enemy Spaceship
        canvas.drawBitmap(enemyShip.getEnemyShip(), enemyShip.x, enemyShip.y, null);
        canvas.drawBitmap(enemyNormal.getEnemyShip(), enemyNormal.x, enemyNormal.y, null);

        //canvas.drawBitmap(enemyShip.getEnemyShip(), enemyShip.x, enemyShip.y, null);

        //place player at bottom of screen
        if(player.x > screenWidth - player.getPlayerShipWidth()){
            player.x = screenWidth - player.getPlayerShipWidth();
        }else if(player.x < 0){
            player.x = 0;
        }
        //Draw player
        canvas.drawBitmap(player.getPlayerShip(), player.x, player.y, null);


        // Till enemyBulletAction is false, enemy should fire shots from random travelled distance
        if(enemyBulletFired == false){
            if(enemyShip.x >= 200 + random.nextInt(400)){
                Bullet enemyBullet = new Bullet(context, enemyShip.x + enemyShip.getEnemyShipWidth() / 2, enemyShip.y );
                enemyBullets.add(enemyBullet);
                // We're making enemyShotAction to true so that enemy can take a short at a time
                enemyBulletFired = true;
            }
            if(enemyShip.x >= 400 + random.nextInt(800)){
                Bullet enemyBullet = new Bullet(context, enemyShip.x + enemyShip.getEnemyShipWidth() / 2, enemyShip.y );
                enemyBullets.add(enemyBullet);
                // We're making enemyShotAction to true so that enemy can take a short at a time
                enemyBulletFired = true;
            }
            else{
                Bullet enemyBullet = new Bullet(context, enemyShip.x + enemyShip.getEnemyShipWidth() / 2, enemyShip.y );
                enemyBullets.add(enemyBullet);
                // We're making enemyShotAction to true so that enemy can take a short at a time
                enemyBulletFired = true;
            }
        }


        //if the enemy ship collides with the player or a bullet hits the player, remove a life and show an explosion other wise
        //if the bullet leaves the screen fire another bullet
        for(int i = 0; i < enemyBullets.size(); i++){
            enemyBullets.get(i).bY += 15;
            canvas.drawBitmap(enemyBullets.get(i).getBullet(), enemyBullets.get(i).bX, enemyBullets.get(i).bY, null);
            if((enemyBullets.get(i).bX >= player.x)
                    && enemyBullets.get(i).bX <= player.x + player.getPlayerShipWidth()
                    && enemyBullets.get(i).bY >= player.y
                    && enemyBullets.get(i).bY <= player.y + player.getPlayerShipHeight()){
                lives--;
                enemyBullets.remove(i);
                explosion = new Explosion(context, player.x, player.y);
                explosions.add(explosion);
            }else if(enemyBullets.get(i).bY >= screenHeight){
                enemyBullets.remove(i);
            }
            if(enemyBullets.size() < 1){
                enemyBulletFired = false;
            }
        }

        //damage if player collides with a ship
        if((enemyShip.x >= player.x)
                && enemyShip.x <= player.x + player.getPlayerShipWidth()
                && enemyShip.y >= player.y
                && enemyShip.y <= player.y + player.getPlayerShipHeight()){
            lives--;
            explosion = new Explosion(context, player.x, player.y);
            explosions.add(explosion);
        }

        if((enemyNormal.x >= player.x)
                && enemyNormal.x <= player.x + player.getPlayerShipWidth()
                && enemyNormal.y >= player.y
                && enemyNormal.y <= player.y + player.getPlayerShipHeight()){
            lives--;
            explosion = new Explosion(context, player.x, player.y);
            explosions.add(explosion);
        }

        //points scored if player bullet hits enemy
        for(int i=0; i < playerBullets.size(); i++){
            playerBullets.get(i).bY -= 50;
            canvas.drawBitmap(playerBullets.get(i).getBullet(), playerBullets.get(i).bX, playerBullets.get(i).bY, null);
            if((playerBullets.get(i).bX >= enemyShip.x)
                    && playerBullets.get(i).bX <= enemyShip.x + enemyShip.getEnemyShipWidth()
                    && playerBullets.get(i).bY <= enemyShip.y + enemyShip.getEnemyShipHeight()
                    && playerBullets.get(i).bY >= enemyShip.y){
                points++;
                playerBullets.remove(i);
                explosion = new Explosion(context, enemyShip.x, enemyShip.y);
                explosions.add(explosion);
            }else if(playerBullets.get(i).bY <=0){
                playerBullets.remove(i);
            }
        }

        for(int i=0; i < playerBullets.size(); i++){
            playerBullets.get(i).bY -= 50;
            canvas.drawBitmap(playerBullets.get(i).getBullet(), playerBullets.get(i).bX, playerBullets.get(i).bY, null);
            if((playerBullets.get(i).bX >= enemyNormal.x)
                    && playerBullets.get(i).bX <= enemyNormal.x + enemyNormal.getEnemyShipWidth()
                    && playerBullets.get(i).bY <= enemyNormal.y + enemyNormal.getEnemyShipHeight()
                    && playerBullets.get(i).bY >= enemyNormal.y){
                points++;
                playerBullets.remove(i);
                explosion = new Explosion(context, enemyNormal.x, enemyNormal.y);
                explosions.add(explosion);
            }else if(playerBullets.get(i).bY <=0){
                playerBullets.remove(i);
            }
        }


        //show the explosion
        for(int i=0; i < explosions.size(); i++){
            canvas.drawBitmap(explosions.get(i).getExplosion(explosions.get(i).explosionFrame), explosions.get(i).x, explosions.get(i).y, null);
            explosions.get(i).explosionFrame++;
            if(explosions.get(i).explosionFrame > 8){
                explosions.remove(i);
            }
        }


        if(!paused)
            handler.postDelayed(runnable, UPDATE_MILLIS);
    }

    //This function controls player movement with an on touch event, while the player is moving the ship it will fire
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchX = (int)event.getX();
        int touchY = (int)event.getY();

        if(event.getAction() == MotionEvent.ACTION_MOVE){
            player.x = touchX - 150;
            player.y = touchY - 150;

            if(playerBullets.size() < 1){
                Bullet playerBullet = new Bullet(context, player.x + player.getPlayerShipWidth() / 2, player.y);

                playerBullets.add(playerBullet);
            }
        }

        return true;
    }


}
