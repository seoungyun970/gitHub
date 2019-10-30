package com.example.project;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;


public class TeacherDiary extends Activity {
    ListView listView;
    Tdiary_list_adapter myListAdapter;
    ArrayList<Tdiary_list_item> list_itemArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_diary);

        listView = (ListView)findViewById(R.id.tdiary_listview);

        list_itemArrayList=new ArrayList<Tdiary_list_item>();
        list_itemArrayList.add(new Tdiary_list_item(R.mipmap.ic_launcher,"보라돌이","제목1","내용1",new Date(System.currentTimeMillis())));

        myListAdapter = new Tdiary_list_adapter(TeacherDiary.this,list_itemArrayList);
        listView.setAdapter(myListAdapter);

    }
}
