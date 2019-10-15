package com.example.project;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
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

public class MembershipPopup extends Activity implements View.OnClickListener {
    FirebaseAuth firebaseAuth;
    DatabaseReference mDatabase;
    EditText register_id;
    EditText register_pw;
    EditText register_name;
    RadioButton reg_Parent;
    RadioButton reg_Teacher;
    RadioButton reg_Head;
    Button register_checkBtn;
    ProgressDialog progressDialog;
    /*
        ArrayAdapter<String> arrayAdapter;
        static ArrayList<String> arrayIndex =  new ArrayList<String>();
        static ArrayList<String> arrayData = new ArrayList<String>();

        String id;
        String pw;
        String name;
        String job;
        String gender = "";
        String sort = "id";
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_membership_popup);


        Display display=((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        // 윈도우 매니저 객체 얻어오고 디스플레이 객체 얻어오기
        int width= (int) (display.getWidth() *0.9);
        // 얻어온 화면의 폭의 90프로만큼 width에 지정
        int height = (int) (display.getHeight()*0.7);
        // 얻어온 화면의 높이의 70프로 만큼 height에 지정
        getWindow().getAttributes().width=width;
        //멤버십팝업 레이아웃의 폭을 width로 지정
        getWindow().getAttributes().height=height;
        //멤버십팝업 레이아웃의 폭을 height 지정
        getWindow().setGravity(Gravity.CENTER);
        //멤버십팝업 레이아웃 센터지정

        firebaseAuth = FirebaseAuth.getInstance();
        register_id = (EditText)findViewById(R.id.register_id);
        register_pw = (EditText)findViewById(R.id.register_pw);
        register_name = (EditText)findViewById(R.id.register_name);
        reg_Parent = (RadioButton)findViewById(R.id.reg_Parent);
        reg_Teacher = (RadioButton)findViewById(R.id.reg_Teacher);
        reg_Head = (RadioButton)findViewById(R.id.reg_Head);
        register_checkBtn = (Button)findViewById(R.id.register_checkBtn);
        progressDialog = new ProgressDialog(this);

        register_checkBtn.setOnClickListener(this);
    }
/*
    public void setInsertMode(){
        register_id.setText("");
        register_pw.setText("");
        register_name.setText("");
        reg_Male.setChecked(false);
        reg_Female.setChecked(false);
    }

    private AdapterView.OnItemClickListener onClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.e("On Click", "position = " + position);
            Log.e("On Click", "Data: " + arrayData.get(position));
            String[] tempData = arrayData.get(position).split("\\s+");
            Log.e("On Click", "Split Result = " + tempData);
            register_id.setText(tempData[0].trim());
            register_pw.setText(tempData[1].trim());
            register_name.setText(tempData[2].trim());
        }
    };

    public boolean IsExistID(){
        boolean IsExist = arrayIndex.contains(ID);
        return IsExist;
    }
    */



    //사용자등록
    private void registerUser(){
        //사용자가 입력하는 email, password를 가져온다.
        String email = register_id.getText().toString().trim();
        String password = register_pw.getText().toString().trim();
        //email과 password가 비었는지 아닌지를 체크 한다.
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Password를 입력해 주세요.", Toast.LENGTH_SHORT).show();
        }

        //email과 password가 제대로 입력되어 있다면 계속 진행된다.
        progressDialog.setMessage("등록중입니다. 기다려 주세요...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String email = register_id.getText().toString().trim();
                        String pwd = register_pw.getText().toString().trim();

                        if(task.isSuccessful()){
                            Toast.makeText(MembershipPopup.this, "가입성공", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        } else {
                            //에러발생시
                            Toast.makeText(MembershipPopup.this, "가입실패", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view == register_checkBtn) {
            //TODO
            registerUser();
        }

    }
}