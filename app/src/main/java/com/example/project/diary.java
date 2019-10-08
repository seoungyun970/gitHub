package com.example.project;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class diary extends Activity implements View.OnClickListener {
    EditText diary_title;
    EditText diary_context;
    Button diary_insertBtn;
    View diaryView = null;

    LinearLayout diarylistlayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary);

        diary_title = (EditText) findViewById(R.id.diary_title);
        diary_context = (EditText)findViewById(R.id.diary_context);
        diary_insertBtn = (Button) findViewById(R.id.diary_insertBtn);

        diary_insertBtn.setOnClickListener(this);

        diarylistlayout=(LinearLayout)findViewById(R.id.diary_listlayout);
    }

    @Override
    public void onClick(View v) {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) this.getSystemService(infService);
        diaryView = li.inflate(R.layout.diary_item, null, true);

        DiaryModel diaryObj= new DiaryModel();
        diaryView.setTag(diaryObj);

        diaryObj.title= diary_title.getText().toString();
        diaryObj.context=diary_context.getText().toString();

        TextView diary_EchoTitle = (TextView) diaryView.findViewById(R.id.diary_EchoTitle);
        TextView diary_EchoContext = (TextView) diaryView.findViewById(R.id.diary_EchoContext);

        diary_EchoTitle.setText(diaryObj.title);
        diary_EchoContext.setText(diaryObj.context);

        diarylistlayout.addView(diaryView);
    }
}
