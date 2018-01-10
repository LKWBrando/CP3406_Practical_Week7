package com.cp3406test.photoeditor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Display extends View {
    private String message;
    Paint mPaint;
    Canvas  mCanvas;
    Bitmap canvasBitmap;
    Path mPath;
    Paint mBitmapPaint;
    String newColor;

    public Display(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.newColor="#FF0000";
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.parseColor(newColor));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(20);

        mPath = new Path();
        mBitmapPaint = new Paint();
        mBitmapPaint.setColor(Color.RED);
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(canvasBitmap, 0, 0, null);
        canvas.drawPath(mPath, mPaint);
    }

    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;

    private void touch_start(float x, float y) {
        //mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }
    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
            mX = x;
            mY = y;
        }
    }
    private void touch_up() {
        mPath.lineTo(mX, mY);
        // commit the path to our offscreen
        mCanvas.drawPath(mPath, mPaint);
        //mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
        // kill this so we don't double draw
        mPath.reset();
        // mPath= new Path();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        //update paint color

        try{
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touch_start(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    touch_move(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    touch_up();
                    invalidate();
                    break;
            }
        }catch(Exception e){}
        return true;
    }

    public void setCanvasBitmap(Bitmap bitmap) { //storing bitmap object from MainActivity
        canvasBitmap = bitmap;
        invalidate();
    }
}


/*
* public void setMessage(String message){
        this.message = message;
        invalidate(); //updating layout
    }

    private PointF getCenter(){
            return new PointF(getWidth() / 2f, getHeight() / 2f);
        }

    public Position getPosition(float x, float y){
        PointF center = getCenter();
        if(x < center.x && y < center.y){
            return Position.TOP_LEFT;
        }
        else if(x < center.x && y > center.y){
            return Position.BOTTOM_LEFT;
        }
        else if(x > center.x && y < center.y){
            return Position.TOP_RIGHT;
        }
        else if(x > center.x && y > center.y){
            return Position.BOTTON_RIGHT;
        }
        else{
            return Position.MIDDLE;
        }
    }*/
