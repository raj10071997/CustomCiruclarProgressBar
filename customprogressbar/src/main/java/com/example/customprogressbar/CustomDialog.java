package com.example.customprogressbar;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by dhanraj on 26/9/17.
 */

public class CustomDialog extends Dialog {


    public TextView textView;
    public Button replaceFiles,copyFiles,cancel;

    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    public CustomDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected CustomDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        textView = (TextView) findViewById(R.id.mytext);
        textView.setText("Wait for sometime..!!");
       // replaceFiles = (Button) findViewById(R.id.replaceFiles);
        //copyFiles = (Button) findViewById(R.id.copyFiles);
        //cancel = (Button) findViewById(R.id.cancel);


    }
}
