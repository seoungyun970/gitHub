package com.example.project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class ExperiencePopup extends Activity {
    TextView experiText;
    Button parent_experiBtn;
    Button teacher_experiBtn;
    Button director_experiBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.experience_popup);

        experiText=findViewById(R.id.experiText);    //체험 텍스트
        parent_experiBtn=findViewById(R.id.parent_experiBtn);  //체험 부모님 버튼
        teacher_experiBtn=findViewById(R.id.teacher_experiBtn); //체험 선생님 버튼
        director_experiBtn=findViewById(R.id.director_experiBtn); //체험 원장님 버튼
        //화면 비율에 따른 팝업창
        Display display=((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay(); //디스플레이 지정
        int width= (int) (display.getWidth() *0.9);   //기기 화면 넓이의 90퍼센트 만큼 width 변수에 값을 입력
        int height = (int) (display.getHeight()*0.5);  //기기 화면 높이의 40프로 만큼 height 변수에 값을 입력

        getWindow().getAttributes().width=width;    //experience_popup 넓이를 width변수로 지정
        getWindow().getAttributes().height=height;  //experience_popup 높이를 height변수로 지정
        getWindow().setGravity(Gravity.CENTER);    // experience_popup 중앙으로 배치

        int extention_height= (int) (height*0.25); // 팝업 높이의 25프로 만큼 extention_height 변수에 입력

        //팝업 구성요소들의 높이를 extention_height로 지정
        experiText.setHeight(extention_height);
        parent_experiBtn.setHeight(extention_height);
        teacher_experiBtn.setHeight(extention_height);
        director_experiBtn.setHeight(extention_height);
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.parent_experiBtn: //학부모 체험하기가 눌려졌을때

                break;
            case R.id.teacher_experiBtn: //교사 체험하기가 눌려졌을때
                Intent teacherintent = new Intent(ExperiencePopup.this, TeacherMain.class);
                startActivity(teacherintent);
                break;
            case R.id.director_experiBtn: //원장 체험하기가 눌려졌을때

                break;
        }
    }
}
