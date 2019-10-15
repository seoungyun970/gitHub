package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public  void onClick(View v){
        switch (v.getId()){
            case R.id.loginBtn: //로그인 버튼

                break;
            case R.id.signupBtn: //회원가입 버튼
                Intent memintent = new Intent(LoginActivity.this, MembershipPopup.class);
                startActivity(memintent);
                break;
        }
    }
}
