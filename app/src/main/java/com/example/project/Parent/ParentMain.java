package com.example.project.Parent;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.project.CustomCalendarActivity;
import com.example.project.Model.User;
import com.example.project.R;
import com.example.project.TeacherChat;
import com.example.project.TeacherTmap;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class ParentMain extends Activity {

    ImageView gps;
    ImageView diary;
    ImageView teacherTmap;
    ImageView teacherEatting;
    ImageView Userface;
    ImageView calendar;
    ImageView attendance;
    TextView Username;
    private String uid;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_main);
        gps = findViewById(R.id.gps);
        diary = findViewById(R.id.diary);
        teacherTmap = findViewById(R.id.teacherTmap);
        teacherEatting = findViewById(R.id.eatting);
        Userface = findViewById(R.id.face);
        Username = findViewById(R.id.teacherName);
        calendar = findViewById(R.id.calendar);
        attendance = findViewById(R.id.attendance);


        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User userModel = dataSnapshot.getValue(User.class);
                Glide.with(ParentMain.this)
                        .load(userModel.profileImageUrl)
                        .apply(new RequestOptions().circleCrop())
                        .into(Userface);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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
        passPushTokenToServer();
    }

    void passPushTokenToServer() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String token = FirebaseInstanceId.getInstance().getToken();
        Map<String, Object> nap = new HashMap<>();
        nap.put("pushToken", token);
        FirebaseDatabase.getInstance().getReference().child("users").child(uid).updateChildren(nap);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gps:
                Intent intent = new Intent(ParentMain.this, TeacherChat.class);
                startActivity(intent);
                break;
            case R.id.diary:
                Intent dintent = new Intent(ParentMain.this, ParentDiary.class);
                startActivity(dintent);
                break;
            case R.id.teacherTmap:
                Intent intentGps = new Intent(ParentMain.this, TeacherTmap.class);
                startActivity(intentGps);
                break;
            case R.id.eatting:
                Intent intentEatting = new Intent(ParentMain.this, ParentEatting.class);
                startActivity(intentEatting);
                break;
            case R.id.notice:
                Intent intentNotice = new Intent(ParentMain.this, ParentNotice.class);
                startActivity(intentNotice);
                break;
            case R.id.calendar:
                Intent intentCalendar = new Intent(ParentMain.this, ParentCustomCalendarActivity.class);
                startActivity(intentCalendar);
                break;
            case R.id.album:
                Intent intentAlbum = new Intent(ParentMain.this, ParentAlbum.class);
                startActivity(intentAlbum);
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