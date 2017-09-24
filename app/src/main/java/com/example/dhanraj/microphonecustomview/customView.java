package com.example.dhanraj.microphonecustomview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;
import static java.sql.Types.NULL;

/**
 * Created by dhanraj on 24/8/17.
 */

public class customView extends View {


    Drawable micro;
    Paint circlePaint,mPaint;
    Paint strokePaint,linePaint;
    int width=0;
    int height=0;
    int min=0;
    int imageSize=0;
    Handler handler;
    static int timer=0;
    int topdegree = -90;
    int bottomdegree = 90;
    int degree1 = -90,degree2=220,degree1Old=50,degree2Old=50;
    boolean checkDown = false,first =true,rotate = false;

    int oldX,oldY,newX,newY,mX=250,mY=250;


   // ValueAnimator animation;

    private boolean animated,animated2;
    private long animationDuration = 4000l; //default duration
    ValueAnimator animation = null;

    public void setAnimated(boolean animated) {
        this.animated = animated;
    }

    public void setAnimationDuration(long animationDuration) {
        this.animationDuration = animationDuration;
    }


    Runnable r  = new Runnable() {
        @Override
        public void run() {
            change();
        }
    };


    public customView(Context context) {
        super(context);
        init();
    }


    public customView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public customView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public customView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(Color.YELLOW);

        strokePaint = new Paint();
        strokePaint.setAntiAlias(true);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setColor(Color.BLUE);
        strokePaint.setStrokeWidth(20);

        linePaint = new Paint();
        strokePaint.setAntiAlias(true);
        linePaint.setColor(Color.GREEN);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(20);

        handler = new Handler();
        mPaint = new Paint();

        animation = ValueAnimator.ofFloat(0, 180);
        animation.setDuration(2000l); //one second

    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


            //canvas.drawCircle(width/2,height/2,(float)(min-100),circlePaint);
            canvas.drawCircle(width/2,height/2,(float)(Math.sqrt(2)*imageSize)/2,strokePaint);

            if(micro == null){
                micro = ContextCompat.getDrawable(getContext(), R.drawable.microphone);
                micro.setFilterBitmap(true);
                micro.setBounds((width - imageSize) / 2, (height - imageSize) / 2, width - ((width - imageSize) / 2), height - ((height - imageSize) / 2));
                // micro.setBounds(0, 0, width , height );
            }

            micro.draw(canvas);
            first=false;



        if(checkDown)
        {

            /*animation.start();
                for(;degree1!=90;)
                {
                    canvas.drawArc((float)(width/2-(Math.sqrt(2)*imageSize)/2),(float)((height/2)-(Math.sqrt(2)*imageSize)/2),
                            (float)((width/2)+(Math.sqrt(2)*imageSize)/2),(float)((height/2)+(Math.sqrt(2)*imageSize)/2),
                            (float)degree1,(float)1,false,linePaint);
                    degree1+=0.5;
                }*/

            if(degree1<=40 && degree2>=90)
            {
                canvas.drawArc((float)(width/2-(Math.sqrt(2)*imageSize)/2),(float)((height/2)-(Math.sqrt(2)*imageSize)/2),
                        (float)((width/2)+(Math.sqrt(2)*imageSize)/2),(float)((height/2)+(Math.sqrt(2)*imageSize)/2),
                        (float)degree1,(float)degree1Old,false,linePaint);
                canvas.drawArc((float)(width/2-(Math.sqrt(2)*imageSize)/2),(float)((height/2)-(Math.sqrt(2)*imageSize)/2),
                        (float)((width/2)+(Math.sqrt(2)*imageSize)/2),(float)((height/2)+(Math.sqrt(2)*imageSize)/2),
                        (float)degree2,(float)degree2Old,false,linePaint);

            }
            else
                animated=false;

            canvasAnimate(canvas);

           // canvas.drawLine();

        }

        if(rotate)
        {




                canvas.drawArc((float)(width/2-(Math.sqrt(2)*imageSize)/2),(float)((height/2)-(Math.sqrt(2)*imageSize)/2),
                        (float)((width/2)+(Math.sqrt(2)*imageSize)/2),(float)((height/2)+(Math.sqrt(2)*imageSize)/2),
                        (float)topdegree,(float)degree1Old,false,linePaint);

                canvas.drawArc((float)(width/2-(Math.sqrt(2)*imageSize)/2),(float)((height/2)-(Math.sqrt(2)*imageSize)/2),
                        (float)((width/2)+(Math.sqrt(2)*imageSize)/2),(float)((height/2)+(Math.sqrt(2)*imageSize)/2),
                        (float)bottomdegree,(float)degree2Old,false,linePaint);

                topdegree+=10;
                bottomdegree+=10;

                if(topdegree>=360)
                    topdegree=0;

                if(bottomdegree>=360)
                    bottomdegree=0;

            canvasAnimate(canvas);


        }


    }

    private void canvasAnimate(final Canvas canvas) {

        if(animation != null) {
            animation.cancel();
        }

        if(animated) {

                    invalidate();
                   Log.d("dhanrajanimated", String.valueOf(animation.getAnimatedFraction()));
                    if(degree1<40)
                    degree1+=10;

                    if(degree2>90)
                       degree2-=10;


        }

        if(animated2)
        {

                    invalidate();

        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventAction = event.getAction();
        float x = event.getX();
        float y = event.getY();
        switch(eventAction)
        {
            case MotionEvent.ACTION_DOWN:
                Log.d("dhanraj","sahu");
                /*if(timer==0)
                {
                    strokePaint.setColor(Color.RED);
                    invalidate();
                }*/
                if(degree1!=40)
                {
                    checkDown = true;
                    //drawLine();
                    animated=true;
                    invalidate();
                }
                    break;
            case MotionEvent.ACTION_MOVE:
                Log.d("dhanraj2","sahu2");
               /* if(timer==0)
                {
                    strokePaint.setColor(Color.RED);
                    invalidate();
                }*/
                break;
            case MotionEvent.ACTION_UP:
                Log.d("dhanraj3","sahu3");
                checkDown = false;
                degree1=-90;
                degree2=220;
                animated=false;
                animated2=true;
                rotate=true;
               // rotateProgress();

                invalidate();
               // change();

                break;
        }


       // postInvalidate();
        return true;
    }

    private void rotateProgress() {

        rotate = true;
        invalidate();
       /* try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/



    }

    private void drawLine() {


        //degree2Old=degree2;
        invalidate();
        degree1+=5;
        degree2-=5;
        degree1Old+=5;




        /*if(degree1==360)
            degree1=0;*/

        if(degree1!=90)
            drawLine();
        else
        {
            degree1=-90;
            degree1Old=0;
            checkDown=false;
        }

    }

    private void change()
    {
        if(timer<500) {

            handler.postDelayed(r, 1);
            timer++;
            if (timer % 2 == 0)
                strokePaint.setColor(Color.YELLOW);
            else
                strokePaint.setColor(Color.GREEN);

            invalidate();

        }
        else
        {
           // timer =0;
            handler.removeCallbacks(r);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = MeasureSpec.getSize((widthMeasureSpec));
        height = MeasureSpec.getSize(heightMeasureSpec);
        /*  width = getMeasuredWidth();
        height = getMeasuredHeight();*/

        Log.d("width Height",String.valueOf(width)+" "+String.valueOf(height));
        min = Math.min(width,height);

        imageSize = (int)(min*0.5);
    }


    //code for changing the coordiantes in circle
   /* onClipEvent (load) {
        var radius = 10 + Math.random() * 50;
        var speed = 5 + Math.random() * 20;

        var xcenter = this._x;

        var ycenter = this._y;

        var degree = 0;
        var radian;
    }
    onClipEvent (enterFrame) {
        degree += speed;
        radian = (degree/180)*Math.PI;
        this._x = xcenter+Math.cos(radian)*radius;
        this._y = ycenter-Math.sin(radian)*radius;
    }*/
}
