package com.mcvicar.spacewar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

public class Bullet
{
    private float x;
    private float y;

    Bitmap bullet;
    Context context;
    int bX, bY;

    private final RectF rect;

    // Which way is it shooting
    public int UP = 0;
    public int DOWN = 1;
    public int RIGHT = 2;
    public int LEFT = 3;

    // Going nowhere
    int heading = -1;
    float speed = 300;

   // private final int screenY;
   // private final int screenX;

    private int width;
    private int height;

    private boolean isActive;

    public Bullet(Context context, int bX, int bY)
    {

        //  height = screenY / 20;
        isActive = false;
       /* this.screenX = screenX;
        this.screenY = screenY;*/
        this.rect = new RectF();

        this.bX = bX;
        this.bY = bY;
        this.context = context;
        bullet = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet);
    }

    public boolean shoot(float startX, float startY, int direction)
    {
        if (!isActive)
        {
            x = startX;
            y = startY;
            heading = direction;
            isActive = true;
            speed=400;

            if ((direction == RIGHT)||(direction==LEFT))
            {
                //width = screenX/20;
                height = 1;
                speed = 250;
            }

            else
            {
                //height = screenY/20;
                width = 1;
                speed = 400;
            }

            return true;
        }

        // Bullet already active
        return false;
    }

    public void update(long fps)
    {
        // Just move up or down
        if(heading == UP)
        {
            y = y - speed / fps;
        }
        else if (heading == DOWN)
        {
            y = y + speed / fps;
        }
        else if (heading == RIGHT)
        {
            x = x + speed / fps;
        }

        else
        {
            x = x - speed / fps;
        }

        // Update rect
        rect.left = x;
        rect.right = x + width;
        rect.top = y;
        rect.bottom = y + height;
    }


    public RectF getRect()
    {
        return  rect;
    }

    public boolean getStatus()
    {
        return isActive;
    }

    public void setInactive()
    {
        isActive = false;
    }

    public float getImpactPointY()
    {
        if (heading == DOWN)
        {
            return y + height;
        }

        return y;
    }

    public float getImpactPointX()
    {
        if (heading == RIGHT)
        {
            return  x + width;
        }

        return x;
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
