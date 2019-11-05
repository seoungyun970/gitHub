package com.example.project;

import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

public class DiaryWriteActivity extends AppCompatActivity {
    private EditText mWriteTitleText;
    private EditText mWriteContentsText;
    private EditText mWriteNameText;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_diary_activity);

        mWriteTitleText=findViewById(R.id.write_title_text);
        mWriteContentsText=findViewById(R.id.write_contents_text);
        mWriteNameText=findViewById(R.id.write_name_text);

        Map<String, Object> post =new HashMap<>();
        post.put("id","");
        post.put("title",mWriteTitleText.getText().toString());
        post.put("contents",mWriteContentsText.getText().toString());
        post.put("name",mWriteNameText.getText().toString());

    }
}
