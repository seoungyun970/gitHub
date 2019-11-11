package com.example.project;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class TeacherDiary extends AppCompatActivity {
    private RecyclerView list_recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_diary);
        list_recyclerview = (RecyclerView) findViewById(R.id.recycler_diary);
        list_recyclerview.setLayoutManager(new LinearLayoutManager(TeacherDiary.this));
        int child_case = 0;
        list_recyclerview.setAdapter(new RecyclerViewAdapter(child_case));
        FloatingActionButton fab = findViewById(R.id.write_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TeacherDiary.this, DiaryWriteActivity.class);
                startActivity(intent);
            }
        });
    }

    class RecyclerViewAdapter extends RecyclerView.Adapter{

        //아래부분 static class 불러옴
        List<Diary> diaries;
        public RecyclerViewAdapter(int child_case) {
            diaries = new ArrayList<>();
            FirebaseDatabase.getInstance().getReference().child("Diary").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    diaries.clear();

                    for(DataSnapshot item : dataSnapshot.getChildren()){
                        diaries.add(item.getValue(Diary.class));
                    }
                    //메시지 갱신 위치
                    notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diary,parent,false);

            return new MessageVieHolder(view);

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((MessageVieHolder)holder).list_title.setText(diaries.get(position).title);
            ((MessageVieHolder)holder).list_content.setText(diaries.get(position).contents);
            ((MessageVieHolder)holder).list_name.setText(diaries.get(position).name);

        }

        @Override
        public int getItemCount() {
            return diaries.size();
        }

        private class MessageVieHolder extends RecyclerView.ViewHolder {
            public TextView list_title;
            public TextView list_content;
            public TextView list_name;


            public MessageVieHolder(View view) {
                super(view);
                list_title = (TextView)view.findViewById(R.id.item_title_text);
                list_content = (TextView)view.findViewById(R.id.item_contents_text);
                list_name = (TextView)view.findViewById(R.id.item_name_text);

            }
        }
    }
}
