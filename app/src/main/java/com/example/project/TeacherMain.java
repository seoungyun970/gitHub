package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TeacherMain extends Activity {
    ImageView gps;
    ImageView diary;
    ImageView setting;
    ImageView profile;
    TextView tname;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_main);
        gps=findViewById(R.id.gps);
        diary=findViewById(R.id.diary);
        setting=findViewById(R.id.setting);
        tname = (TextView)findViewById(R.id.teacherName);
        profile = (ImageView)findViewById(R.id.face);


        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }



    public void onClick(View view) {
        switch (view.getId()){
            case R.id.gps:
                Intent intent=new Intent(TeacherMain.this,TeacherChat.class);
                startActivity(intent);
                break;
            case R.id.diary:
                Intent intentDiary=new Intent(TeacherMain.this,TeacherDiary.class);
                startActivity(intentDiary);
                break;
            case R.id.setting:
                Intent intentSetting=new Intent(TeacherMain.this,TeacherSetting.class);
                startActivity(intentSetting);
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
