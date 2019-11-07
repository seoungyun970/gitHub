package com.example.project;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

public class TeacherMain extends Activity {
    ImageView gps;
    ImageView diary;
    ImageView setting;
    ImageView teacherTmap;
    ImageView teacherEatting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_main);
        gps=findViewById(R.id.gps);
        diary=findViewById(R.id.diary);
        setting=findViewById(R.id.setting);
        teacherTmap=findViewById(R.id.teacherTmap);
        teacherEatting=findViewById(R.id.eatting);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            getActionBar().setBackgroundDrawable(getDrawable(R.color.colorPrimary));
        }
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.gps:
                Intent intent=new Intent(TeacherMain.this,gps.class);
                startActivity(intent);
                break;
            case R.id.diary:
                Intent dintent=new Intent(TeacherMain.this,TeacherDiary.class);
                startActivity(dintent);
                break;
            case R.id.setting:
                Intent intentSetting=new Intent(TeacherMain.this,TeacherSetting.class);
                startActivity(intentSetting);
                break;
            case R.id.teacherTmap:
                Intent intentGps=new Intent(TeacherMain.this,TeacherTmap.class);
                startActivity(intentGps);
                break;
            case R.id.eatting:
                Intent intentEatting=new Intent(TeacherMain.this,TeacherEatting.class);
                startActivity(intentEatting);
                break;
            case R.id.notice:
                Intent intentNotice=new Intent(TeacherMain.this,TeacherNotice.class);
                startActivity(intentNotice);
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
