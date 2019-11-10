package com.example.project;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class NoticeWriteActivity  extends AppCompatActivity implements View.OnClickListener {
    private Spinner noticeWriteSpinner;
    private EditText noticeWriteTitleText;
    private EditText noticeWriteContentsText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_notice_activity);

        noticeWriteSpinner=findViewById(R.id.grade_spinner);
        noticeWriteTitleText=findViewById(R.id.notice_write_title_text);
        noticeWriteContentsText=findViewById(R.id.notice_write_content_text);

        findViewById(R.id.notice_upload_btn).setOnClickListener(this);

        String[] str=getResources().getStringArray(R.array.notice_grade_array);
        noticeWriteSpinner.setPrompt("반을 선택해주세요.");
        ArrayAdapter gradeAdapter= new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,str);

        noticeWriteSpinner.setAdapter(gradeAdapter);
        noticeWriteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
