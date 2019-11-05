package com.example.project;

import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DiaryWriteActivity extends AppCompatActivity {
    private EditText mWriteTitleText;
    private EditText mWriteContentsText;
    private EditText mWriteNameText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_diary_activity);

        mWriteTitleText=findViewById(R.id.write_title_text);
        mWriteContentsText=findViewById(R.id.write_contents_text);
        mWriteNameText=findViewById(R.id.write_name_text);


    }
}
