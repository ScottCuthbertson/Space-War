package com.mcvicar.spacewar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Player {
    Context context;
    Bitmap player;
    int x, y;
    Random random;

    public Player(Context context) {
        this.context = context;
        player = BitmapFactory.decodeResource(context.getResources(), R.drawable.player_ship);
        random = new Random();
        x = random.nextInt(SpaceWar.screenWidth);
        y = SpaceWar.screenHeight - player.getHeight();
    }

    public Bitmap getPlayerShip(){
        return player;
    }

    int getPlayerShipWidth(){
        return player.getWidth();
    }

    int getPlayerShipHeight(){
        return player.getHeight();
    }


}
