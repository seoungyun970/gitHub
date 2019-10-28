package com.example.project;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class LoginActivity extends Activity implements View.OnClickListener {
    FirebaseAuth firebaseAuth;
    DatabaseReference mDatabase;
    EditText login_id;
    EditText login_pw;
    Button loginBtn;
    ProgressDialog progressDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_id = (EditText)findViewById(R.id.login_id);
        login_pw = (EditText)findViewById(R.id.login_pw);
        loginBtn = (Button)findViewById(R.id.loginBtn);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(this);





    }
    private void userLogin(){
        String email = login_id.getText().toString().trim();
        String password = login_pw.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "email을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "password를 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("로그인중입니다. 잠시 기다려 주세요...");
        progressDialog.show();

        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "로그인 성공!", Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), TeacherMain.class));

                        } else {
                            Toast.makeText(getApplicationContext(), "로그인 실패!", Toast.LENGTH_LONG).show();

                        }
                    }
                });

    }




    public  void onClick(View v){
        if(v == loginBtn) {
            userLogin();
        }

        switch (v.getId()){
            case R.id.loginBtn: //로그인 버튼

                break;
            case R.id.signupBtn: //회원가입 버튼
                Intent memintent = new Intent(LoginActivity.this, MembershipPopup.class);
                startActivity(memintent);
                break;

            case R.id.naverBtn: //네이버 버튼

                break;
            case R.id.kakaoBtn: //카카오 버튼

                break;
        }
    }

}
