package com.example.dhanraj.microphonecustomview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
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
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

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
    String label;
    Paint strokePaint,linePaint;
    int width=0;
    int height=0;
    int min=0;
    int imageSize=0;
    //Handler handler;
    static int timer=0;
    int topdegree = -90;
    int bottomdegree = 90;
    int degree1 = -90,degree2=220,degree1Old=50,degree2Old=50;
    boolean checkDown = false,first =true,rotate = false;

    int oldX,oldY,newX,newY,mX=250,mY=250;
    final Handler handler = new Handler();
    int ImageID,labalColor,mainCircleColor,labelSize,sweepAngle,rotatingBarColor;
    float strokeWidth,mainCirCleRadius;


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
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.GREEN);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(20);

       // handler = new Handler();
        mPaint = new Paint();

        animation = ValueAnimator.ofFloat(0, 180);
        animation.setDuration(2000l); //one second

    }

    private void initXMLAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircularProgressBar);
        final int N = a.getIndexCount();
        for (int i = 0; i < N; ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.CircularProgressBar_label) {
                setLabel(a.getString(attr));
            } else if (attr == R.styleable.CircularProgressBar_label_color) {
                setLabalColor(a.getColor(attr, Color.parseColor("#222222")));
            } else if (attr == R.styleable.CircularProgressBar_main_circle_color) {
                setMainCircleColor(a.getColor(attr, Color.parseColor("#000000")));
            } else if (attr == R.styleable.CircularProgressBar_label_size) {
                setLabelSize(a.getInteger(attr, 40));
            } else if (attr == R.styleable.CircularProgressBar_stroke_width) {
                setStrokeWidth(a.getFloat(attr, 25));
            } else if (attr == R.styleable.CircularProgressBar_sweep_angle) {
                setSweepAngle(a.getInt(attr, -1));
            } else if (attr == R.styleable.CircularProgressBar_main_circle_radius) {
                setMainCirCleRadius(a.getFloat(attr, -1));
            } else if (attr == R.styleable.CircularProgressBar_rotating_bar_color) {
                setRotatingBarColor(a.getColor(attr, Color.parseColor("#FFA036")));
            } else if (attr == R.styleable.CircularProgressBar_Image) {
                setImageID(a.getInt(attr, -1));
            }
        }
        a.recycle();
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

                    topdegree+=5;
                    bottomdegree+=5;

                    if(topdegree>=360)
                        topdegree=0;

                    if(bottomdegree>=360)
                        bottomdegree=0;



            //   canvasAnimate(canvas);


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

            animation = ValueAnimator.ofFloat(0.0f, 5.0f);
            //animationDuration specifies how long it should take to animate the entire graph, so the
            //actual value to use depends on how much the value needs to change
//            int changeInValue = Math.abs(currentValue - previousValue);
//            long durationToUse = (long) (animationDuration * ((float) changeInValue / (float) maxValue));
            animation.setDuration(20l);
            animation.setInterpolator(new AccelerateInterpolator());

            animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    invalidate();
                }
            });

            animation.start();


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


    public int getImageID() {
        return ImageID;
    }

    public void setImageID(int imageID) {
        ImageID = imageID;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getLabalColor() {
        return labalColor;
    }

    public void setLabalColor(int labalColor) {
        this.labalColor = labalColor;
    }

    public int getMainCircleColor() {
        return mainCircleColor;
    }

    public void setMainCircleColor(int mainCircleColor) {
        this.mainCircleColor = mainCircleColor;
    }

    public int getLabelSize() {
        return labelSize;
    }

    public void setLabelSize(int labelSize) {
        this.labelSize = labelSize;
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public int getSweepAngle() {
        return sweepAngle;
    }

    public void setSweepAngle(int sweepAngle) {
        this.sweepAngle = sweepAngle;
    }

    public float getMainCirCleRadius() {
        return mainCirCleRadius;
    }

    public void setMainCirCleRadius(float mainCirCleRadius) {
        this.mainCirCleRadius = mainCirCleRadius;
    }

    public int getRotatingBarColor() {
        return rotatingBarColor;
    }

    public void setRotatingBarColor(int rotatingBarColor) {
        this.rotatingBarColor = rotatingBarColor;
    }
}
