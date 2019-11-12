package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Holder.NoticeViewHolder;
import com.example.project.Model.Notice;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TeacherNotice extends AppCompatActivity {
    private RecyclerView list_recyclerview;
    FirebaseDatabase database;
    DatabaseReference noticedb;



    FirebaseRecyclerOptions<Notice> options;
    FirebaseRecyclerAdapter<Notice, NoticeViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_notice);

        database = FirebaseDatabase.getInstance();
        noticedb = database.getReference("Notice");

        list_recyclerview = (RecyclerView) findViewById(R.id.recycler_notice);
        list_recyclerview.setLayoutManager(new LinearLayoutManager(TeacherNotice.this));
        FloatingActionButton fab = findViewById(R.id.notice_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TeacherNotice.this, NoticeWriteActivity.class);
                startActivity(intent);
            }
        });

        showTask();
    }

    private void showTask() {
        options = new FirebaseRecyclerOptions.Builder<Notice>()
                .setQuery(noticedb, Notice.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Notice, NoticeViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull NoticeViewHolder holder, int i, @NonNull Notice notice) {
                holder.mMenuTextView.setText(notice.getNoticemenu());
                holder.mTitleTextView.setText(notice.getTitle());
                holder.mContentsTextView.setText(notice.getContents());
                holder.mDateTextView.setText(notice.getDate());
            }

            @NonNull
            @Override
            public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_notice,parent,false);

                return new NoticeViewHolder(view);
            }
        };
        list_recyclerview.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
