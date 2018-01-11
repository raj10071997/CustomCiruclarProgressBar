package com.example.dhanraj.microphonecustomview;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.customprogressbar.customView;

public class MainActivity extends AppCompatActivity {


    private customView myview;
    private Button btn;
    private final long STOPSPINNING = 8000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myview = (customView) findViewById(R.id.customBar);

//        myview.setImageID(R.drawable.microphone);
//        myview.setRotatingInverseRate(200);
//        myview.setRotatingBarColor(Color.parseColor("#6599FF"));
//        myview.setStrokeWidth(20);
//        myview.setSweepAngle(50);
//        myview.setLabel("wait");
//        myview.setLabelSize(40);
//        myview.setLabelColor(Color.parseColor("#FF9900"));
//        myview.setMainCircleColor(Color.parseColor("#097054"));


        btn = (Button) findViewById(R.id.stop);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             myview.stopProgressing();
            }
        });


        myview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        myview.stopProgressing();
                        Toast.makeText(MainActivity.this, "Spinning stopped..!!", Toast.LENGTH_SHORT).show();

                    }
                }, 8000);
            }
        });


    }

    public void stopSpinning(View view) {
        Toast.makeText(MainActivity.this, "set on click listener is working", Toast.LENGTH_SHORT).show();
    }
}
