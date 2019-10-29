package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class TeacherSetting extends Activity {
    LinearLayout childManger;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_setting);
        childManger=findViewById(R.id.childManger);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.childManger:
                Intent intent=new Intent(TeacherSetting.this,TeacherManager.class);
                startActivity(intent);
                break;
        }
    }
}
