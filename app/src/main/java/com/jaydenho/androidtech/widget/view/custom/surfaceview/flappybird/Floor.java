package com.jaydenho.androidtech.widget.view.custom.surfaceview.flappybird;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;

/**
 * Created by hedazhao on 2017/11/13.
 */

public class Floor {
    private int x = 0;
    private int y = 0;

    private int gameWidth = 0;
    private int gameHeight = 0;

    public static final float FLOOR_Y_POSITION_RATIO = 4 / 5F;
    private Bitmap brickBitmap = null;
    private BitmapShader bricksBS = null;

    public Floor() {

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setGamePanelSize(int width, int height) {
        this.gameWidth = width;
        this.gameHeight = height;
        y = (int) (gameHeight * FLOOR_Y_POSITION_RATIO);
    }

    public void setBrickBitmap(Bitmap brickBitmap) {
        this.brickBitmap = brickBitmap;
        //x方向上无限重复，y方向上将最后一个像素拉伸
        bricksBS = new BitmapShader(brickBitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        paint.setShader(bricksBS);
        canvas.translate(x, y);
        //x是负值，canvas向左平移后，坐标系也会向左平移，-x位置就是屏幕左边缘，-x+gameWidth就是屏幕右边缘。相当于将一块超大的画往左拉动，就产生了背景向右移动的效果。
        canvas.drawRect(-x, 0, -x + gameWidth, gameHeight - y, paint);
        canvas.restore();
        paint.setShader(null);
    }
}
