package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

public class ExperiTeacher extends Activity {
    ImageView gps;
    ImageView diary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.experi_teacher);
        gps=findViewById(R.id.gps);
        diary=findViewById(R.id.diary);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.gps:
                Intent intent=new Intent(ExperiTeacher.this,gps.class);
                startActivity(intent);
                break;

            case R.id.diary:
                Intent dintent=new Intent(ExperiTeacher.this,diary.class);
                startActivity(dintent);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            applyColors();
        }
    }

    // Apply the title/navigation bar color
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void applyColors() {
        getWindow().setStatusBarColor(Color.parseColor("#efc675"));
    }
}
