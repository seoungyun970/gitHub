package com.example.project;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.Model.Notice;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NoticeWriteActivity  extends AppCompatActivity implements View.OnClickListener {
    private Spinner noticeWriteSpinner;
    private EditText noticeWriteTitleText;
    private EditText noticeWriteContentsText;
    private ImageView mWriteImageView;
    ImageView noticebtn;
    private String uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_notice_activity);


        noticeWriteSpinner=findViewById(R.id.grade_spinner);
        noticeWriteTitleText=findViewById(R.id.notice_write_title_text);
        noticeWriteContentsText=findViewById(R.id.notice_write_content_text);
        mWriteImageView = findViewById(R.id.notice_write_image);



        findViewById(R.id.notice_upload_btn).setOnClickListener(this);

        String[] str=getResources().getStringArray(R.array.notice_grade_array);
        noticeWriteSpinner.setPrompt("반을 선택해주세요.");
        ArrayAdapter gradeAdapter= new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,str);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        noticeWriteSpinner.setAdapter(gradeAdapter);
        noticeWriteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        findViewById(R.id.notice_upload_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String noticemenu = noticeWriteSpinner.getSelectedItem().toString();
        String title = noticeWriteTitleText.getText().toString().trim();
        String contents = noticeWriteContentsText.getText().toString().trim();
        SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy년 MM월dd일 HH시mm분ss초");
        Date time = new Date();
        String time1 = format1.format(time);



        //제목, 내용, 이름이 비었는지 아닌지를 체크 한다.
        if (TextUtils.isEmpty(noticemenu)) {
            Toast.makeText(this, "반을 선택해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, "제목을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(contents)) {
            Toast.makeText(this, "내용을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        Notice notice = new Notice();
        notice.noticemenu = noticeWriteSpinner.getSelectedItem().toString();
        notice.title = noticeWriteTitleText.getText().toString();
        notice.contents = noticeWriteContentsText.getText().toString();
        notice.date = time1;



        FirebaseDatabase.getInstance().getReference().child("Notice").push().setValue(notice);
        Toast.makeText(this,"공지사항이 추가되었습니다.",Toast.LENGTH_SHORT).show();
        NoticeWriteActivity.this.finish();
    }
}
