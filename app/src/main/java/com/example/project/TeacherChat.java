package com.example.project;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.fragment.PeopleFragment;

public class TeacherChat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_chat);

        getSupportFragmentManager().beginTransaction().replace(R.id.teacherchat_framelayout,new PeopleFragment()).commit();
    }
}