package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class TeacherSetting extends Activity {


    LinearLayout childManger;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_setting);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            getActionBar().setBackgroundDrawable(getDrawable(R.color.colorPrimary));
        }
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.childManger:
                Intent intent=new Intent(TeacherSetting.this,TeacherManager.class);
                startActivity(intent);
                break;
            case R.id.signout:
                Intent intent1=new Intent(TeacherSetting.this,LoginActivity.class);
                Toast.makeText(getApplicationContext(), "로그아웃 되었습니다.", Toast.LENGTH_LONG).show();
                startActivity(intent1);

        }
    }
}
