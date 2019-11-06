package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
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

    private RecyclerView mDiaryRecycleView;

    private DiaryAdapter mDiaryAdapter;
    private List<Diary> mDiaryList;

    LinearLayoutManager mLayouyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_diary);
        mDiaryRecycleView=findViewById(R.id.recycler_diary);

        FloatingActionButton fab = findViewById(R.id.write_fab);

        mDiaryList=new ArrayList<>();
        mDiaryList.add(new Diary(null,"날짜 작성","준비물","수연쌤"));
        mDiaryList.add(new Diary(null,"날짜 작성","준비물","수연쌤"));
        mDiaryList.add(new Diary(null,"날짜 작성","준비물","수연쌤"));
        mDiaryList.add(new Diary(null,"날짜 작성","준비물","수연쌤"));
        mDiaryList.add(new Diary(null,"날짜 작성","준비물","수연쌤"));
        mDiaryList.add(new Diary(null,"날짜 작성","준비물","수연쌤"));
        mDiaryList.add(new Diary(null,"날짜 작성","준비물","수연쌤"));
        //여기에 디비에서 불러오는거 작성해야댐 ㅇㅅㅇ!

        mLayouyManager = new LinearLayoutManager(this);
        mDiaryRecycleView.setLayoutManager(mLayouyManager);

        mDiaryAdapter=new DiaryAdapter(mDiaryList);
        mDiaryRecycleView.setAdapter(mDiaryAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TeacherDiary.this, DiaryWriteActivity.class);
                startActivity(intent);
            }
        });

    }

    private class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>{

        private List<Diary> mDiaryList;

        public DiaryAdapter(List<Diary> mDiaryList) {
            this.mDiaryList = mDiaryList;
        }
        @Override
        public DiaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new DiaryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diary,parent,false));
        }

        @Override
        public void onBindViewHolder(DiaryViewHolder holder, int position) {
            Diary data = mDiaryList.get(position);
            holder.mTitleTextView.setText(data.getTitle());
            holder.mContentsView.setText(data.getContent());
            holder.mNameTextView.setText(data.getName());
        }

        @Override
        public int getItemCount() {
            return mDiaryList.size();
        }

        class DiaryViewHolder extends RecyclerView.ViewHolder{
            private TextView mTitleTextView;
            private TextView mContentsView;
            private TextView mNameTextView;

            public DiaryViewHolder(View itemView) {
                super(itemView);
                mContentsView=itemView.findViewById(R.id.item_contents_text);
                mTitleTextView=itemView.findViewById(R.id.item_title_text);
                mNameTextView =itemView.findViewById(R.id.item_name_text);
            }
        }
    }

}
