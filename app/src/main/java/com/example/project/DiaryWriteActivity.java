package com.example.project;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.FirebaseDatabase;

public class DiaryWriteActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mWriteTitleText;
    private EditText mWriteContentsText;
    private EditText mWriteNameText;
    private Diary mDiary;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_diary_activity);

        mWriteTitleText=findViewById(R.id.write_title_text);
        mWriteContentsText=findViewById(R.id.write_contents_text);
        mWriteNameText=findViewById(R.id.write_name_text);
        findViewById(R.id.write_upload_btn).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        //사용자가 입력하는 제목,내용,이름을 가져온다
        String title = mWriteTitleText.getText().toString().trim();
        String contents = mWriteContentsText.getText().toString().trim();
        String name= mWriteNameText.getText().toString().trim();

        //제목, 내용, 이름이 비었는지 아닌지를 체크 한다.
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, "제목을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(contents)) {
            Toast.makeText(this, "내용을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "이름을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        mDiary= new Diary(
                mWriteTitleText.getText().toString(),
                mWriteContentsText.getText().toString(),
                mWriteNameText.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("Diary").push().setValue(mDiary);
        Toast.makeText(this,"알림장이 추가되었습니다.",Toast.LENGTH_SHORT).show();
    }
}
