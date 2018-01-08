package com.example.customprogressbar;

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import static java.lang.Thread.sleep;

/**
 * Created by dhanraj on 24/8/17.
 */

public class customView extends View {

    private Drawable micro;
    private CustomDialog cd;
    private Paint strokePaint,linePaint,textPaint;
    private String label,dialogText,dialogText2="wait.";
    int width=0;
    int height=0;
    int min=0;
    int imageSize=0;
    long rotatingInverseRate=0;
    int topdegree = -90;
    int bottomdegree = 90;
    int degree1 = -90,degree2=220;
    boolean checkDown = false,rotate = false,dialog_animation,wantTextOverTheImage=true;
    int labelColor,mainCircleColor,labelSize,rotatingBarColor;
    float strokeWidth,mainCirCleRadius,sweepAngle;
    int ImageID;
    private Dialog overlayDialog;
    private Context mContext;
    private boolean animated,animated2;
    private float fingerX,fingerY;


    public customView(Context context) {
        super(context);
        mContext=context;
        init(context);
    }

    public customView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        initXMLAttrs(context, attrs);
        init(context);
    }

    public customView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        initXMLAttrs(context, attrs);
        init(context);
    }

    public customView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext=context;
        initXMLAttrs(context, attrs);
        init(context);
    }

    private void init(Context context) {
        strokePaint = new Paint();
        strokePaint.setAntiAlias(true);
        strokePaint.setStyle(Paint.Style.STROKE);

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setStrokeWidth(2);
        textPaint.setTextAlign(Paint.Align.CENTER);
        //set the size with respect to the radius of the circle
        //textPaint.setTextSize((float)(Math.sqrt(2)*imageSize)/2);

        cd = new CustomDialog(getContext());
        //change the value below
        if(true) //dialog_animation
        cd.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

       // cd.textView.setText("wait...");

    }

    private void initXMLAttrs(final Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircularProgressBar);
        final int N = a.getIndexCount();
        for (int i = 0; i < N; ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.CircularProgressBar_label) {
                setLabel(a.getString(attr));
            } else if (attr == R.styleable.CircularProgressBar_label_color) {
                setLabelColor(a.getColor(attr, Color.parseColor("#FF9900")));
            } else if (attr == R.styleable.CircularProgressBar_main_circle_color) {
                setMainCircleColor(a.getColor(attr, Color.parseColor("#097054")));
            } else if (attr == R.styleable.CircularProgressBar_label_size) {
                setLabelSize(a.getInteger(attr, 40));
            } else if (attr == R.styleable.CircularProgressBar_stroke_width) {
                setStrokeWidth(a.getFloat(attr, 20));
            } else if (attr == R.styleable.CircularProgressBar_sweep_angle) {
                setSweepAngle(a.getFloat(attr, (float) 50.0));
            } else if (attr == R.styleable.CircularProgressBar_main_circle_radius) //no use of it
            {
                setMainCirCleRadius(a.getFloat(attr, -1));
            } else if (attr == R.styleable.CircularProgressBar_rotating_bar_color) {
                setRotatingBarColor(a.getColor(attr, Color.parseColor("#6599FF")));
            } else if (attr == R.styleable.CircularProgressBar_Image) {
                setImageID(a.getResourceId(attr,-1));
            }else if(attr==R.styleable.CircularProgressBar_dialog_text)
            {
                setDialogText(a.getString(attr));
            }else if(attr==R.styleable.CircularProgressBar_dialog_animation){
                setDialog_animation(a.getBoolean(attr,false));
            }else if(attr==R.styleable.CircularProgressBar_rotatingInverseRate){
                setRotatingInverseRate(a.getInt(attr,200));
            }
        }
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        strokePaint.setColor(getMainCircleColor());
        strokePaint.setStrokeWidth(getStrokeWidth());

        linePaint.setColor(getRotatingBarColor());
        linePaint.setStrokeWidth(getStrokeWidth());

        textPaint.setColor(getLabelColor());
        textPaint.setTextSize((float)getLabelSize());

            canvas.drawCircle(width/2,height/2,(float)(Math.sqrt(2)*imageSize)/2,strokePaint);

            if(micro == null){
                micro = ContextCompat.getDrawable(getContext(),getImageID());
                micro.setFilterBitmap(true);
                micro.setBounds((width - imageSize) / 2, (height - imageSize) / 2, width - ((width - imageSize) / 2), height - ((height - imageSize) / 2));
                // micro.setBounds(0, 0, width , height );
            }

            micro.draw(canvas);

        if(checkDown)
        {

            if(degree1<=40 && degree2>=90)
            {
                canvas.drawArc((float)(width/2-(Math.sqrt(2)*imageSize)/2),(float)((height/2)-(Math.sqrt(2)*imageSize)/2),
                        (float)((width/2)+(Math.sqrt(2)*imageSize)/2),(float)((height/2)+(Math.sqrt(2)*imageSize)/2),
                        (float)degree1,(float)getSweepAngle(),false,linePaint);
                canvas.drawArc((float)(width/2-(Math.sqrt(2)*imageSize)/2),(float)((height/2)-(Math.sqrt(2)*imageSize)/2),
                        (float)((width/2)+(Math.sqrt(2)*imageSize)/2),(float)((height/2)+(Math.sqrt(2)*imageSize)/2),
                        (float)degree2,(float)getSweepAngle(),false,linePaint);

                canvas.drawCircle(fingerX,fingerY,(float)30.0,textPaint);
            }
            else
                animated=false;

            canvasAnimate(canvas);
        }

        if(rotate)
        {
                canvas.drawArc((float)(width/2-(Math.sqrt(2)*imageSize)/2),(float)((height/2)-(Math.sqrt(2)*imageSize)/2),
                        (float)((width/2)+(Math.sqrt(2)*imageSize)/2),(float)((height/2)+(Math.sqrt(2)*imageSize)/2),
                        (float)topdegree,(float)getSweepAngle(),false,linePaint);

                canvas.drawArc((float)(width/2-(Math.sqrt(2)*imageSize)/2),(float)((height/2)-(Math.sqrt(2)*imageSize)/2),
                        (float)((width/2)+(Math.sqrt(2)*imageSize)/2),(float)((height/2)+(Math.sqrt(2)*imageSize)/2),
                        (float)bottomdegree,(float)getSweepAngle(),false,linePaint);

                if(wantTextOverTheImage)
                {
                    canvas.drawText(dialogText2,width/2,height/2,textPaint);

                    if(dialogText2.length()==getLabel().length()+3)
                        dialogText2=getLabel();
                    else
                        dialogText2=dialogText2+".";
                }

                topdegree+=5;
                bottomdegree+=5;

                if(topdegree>=360)
                    topdegree=0;

                if(bottomdegree>=360)
                    bottomdegree=0;

                canvasAnimate(canvas);
        }
    }

    private void canvasAnimate(final Canvas canvas) {

        if(animated) {

            invalidate();

            if(degree1<40)
            degree1+=10;

            if(degree2>90)
               degree2-=10;
        }

        if(animated2)
        {

            try {
                sleep(getRotatingInverseRate());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            invalidate();

        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int eventAction = event.getAction();
         fingerX = event.getX();
         fingerY = event.getY();
        switch(eventAction)
        {
            case MotionEvent.ACTION_DOWN:
                if(degree1!=40)
                {
                    checkDown = true;
                    animated=true;
                    animated2=false;
                    rotate=false;
                    invalidate();
                }
                    break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                checkDown = false;
                degree1=-90;
                degree2=220;
                animated=false;
                animated2=true;
                rotate=true;
                invalidate();
              //  cd.show();
                showDialog(mContext);
                break;
        }

        return true;
    }

    public void showDialog(Context context) {

        if (overlayDialog == null) {
            overlayDialog = new Dialog(context, android.R.style.Theme_Panel);
            overlayDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED);
        }
        overlayDialog.show();
    }

    public void hideDialog() {
        if (overlayDialog == null ) {
            return;
        }
        overlayDialog.cancel();
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

    public void stopProgressing()
    {

        checkDown=false;
        rotate=false;
        hideDialog();
        try {
            sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        invalidate();

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
        invalidate();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
        invalidate();
    }

    public int getLabelColor() {
        return labelColor;
    }

    public void setLabelColor(int labelColor) {
        this.labelColor = labelColor;
        invalidate();
    }

    public int getMainCircleColor() {
        return mainCircleColor;
    }

    public void setMainCircleColor(int mainCircleColor) {
        this.mainCircleColor = mainCircleColor;

        invalidate();
    }

    public int getLabelSize() {
        return labelSize;
    }

    public void setLabelSize(int labelSize) {
        this.labelSize = labelSize;
        invalidate();
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        invalidate();
    }

    public float getSweepAngle() {
        return sweepAngle;
    }

    public void setSweepAngle(float sweepAngle) {
        this.sweepAngle = sweepAngle;
        invalidate();
    }

    public float getMainCirCleRadius() {
        return mainCirCleRadius;
    }

    public void setMainCirCleRadius(float mainCirCleRadius) {
        this.mainCirCleRadius = mainCirCleRadius;
        invalidate();
    }

    public int getRotatingBarColor() {
        return rotatingBarColor;
    }

    public void setRotatingBarColor(int rotatingBarColor) {
        this.rotatingBarColor = rotatingBarColor;
        invalidate();
    }

    public String getDialogText() {
        return dialogText;
    }

    public void setDialogText(String dialogText) {
        this.dialogText = dialogText;
        invalidate();
    }

    public boolean isDialog_animation() {
        return dialog_animation;
    }

    public void setDialog_animation(boolean dialog_animation) {
        this.dialog_animation = dialog_animation;
        invalidate();
    }
    public void setAnimated(boolean animated) {
        this.animated = animated;
        invalidate();
    }



    public boolean isWantTextOverTheImage() {
        return wantTextOverTheImage;
    }

    public void setWantTextOverTheImage(boolean wantTextOverTheImage) {
        this.wantTextOverTheImage = wantTextOverTheImage;
        invalidate();
    }

    public long getRotatingInverseRate() {
        return rotatingInverseRate;
    }

    public void setRotatingInverseRate(long rotatingInverseRate) {
        this.rotatingInverseRate = rotatingInverseRate;
    }
}
