package com.example.dhanraj.microphonecustomview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    private customView myview;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myview = (customView) findViewById(R.id.dhanraj);
        btn = (Button) findViewById(R.id.stop);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             myview.stopProgressing();
            }
        });


    }
}
