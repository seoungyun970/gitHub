package com.example.project;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;

public class MembershipPopup extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_membership_popup);

        Display display=((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width= (int) (display.getWidth() *0.9);
        int height = (int) (display.getHeight()*0.7);
        getWindow().getAttributes().width=width;
        getWindow().getAttributes().height=height;
        getWindow().setGravity(Gravity.CENTER);

    }
}
