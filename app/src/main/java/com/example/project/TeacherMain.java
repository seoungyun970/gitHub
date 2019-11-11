package com.example.project;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeacherMain extends Activity {
    ImageView gps;
    ImageView diary;
    ImageView setting;
    ImageView teacherTmap;
    ImageView teacherEatting;
    ImageView Userface;
    TextView Username;
    private String uid;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_main);
        gps=findViewById(R.id.gps);
        diary=findViewById(R.id.diary);
        setting=findViewById(R.id.setting);
        teacherTmap=findViewById(R.id.teacherTmap);
        teacherEatting=findViewById(R.id.eatting);
        Userface=findViewById(R.id.face);
        Username=findViewById(R.id.teacherName);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/dolbomi1.appspot.com/o/userImages%2FKakaoTalk_20191110_175159882.jpg?alt=media&token=b2324ed4-cbe2-493d-a980-99d6c371f7fe").into(Userface);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            getActionBar().setBackgroundDrawable(getDrawable(R.color.colorPrimary));
        }

        FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Username.setText(value);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("profileImageUrl").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public TeacherMain() {

    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.gps:
                Intent intent=new Intent(TeacherMain.this,TeacherChat.class);
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
            case R.id.calendar:
                Intent intentCalendar=new Intent(TeacherMain.this,Teacher_Calendar.class);
                startActivity(intentCalendar);
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