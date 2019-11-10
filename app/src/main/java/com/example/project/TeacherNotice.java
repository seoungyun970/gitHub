package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TeacherNotice extends AppCompatActivity {

    private RecyclerView mNoticeRecycleView;
    private NoticeAdapter mNoticeAdapter;
    private List<Notice> mNoticeList;

    LinearLayoutManager nLayouyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_notice);
        mNoticeRecycleView=findViewById(R.id.recycler_notice);

        FloatingActionButton fab = findViewById(R.id.notice_fab);

        mNoticeList=new ArrayList<>();

        mNoticeList.add(new Notice("원관리",
                "특별환동 미술수업 시간 변경 안내",
                "미술 선생님의 교육 일정으로 매주 금요일마다 진행되었던 특별활동 수업이 7월부터 매주 수요일로 변경되었습니다",
                "2019-11-07"));

        nLayouyManager = new LinearLayoutManager(this);
        mNoticeRecycleView.setLayoutManager(nLayouyManager);
        mNoticeAdapter=new NoticeAdapter(mNoticeList);
        mNoticeRecycleView.setAdapter(mNoticeAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TeacherNotice.this, NoticeWriteActivity.class);
                startActivity(intent);
            }
        });
    }

    private class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder>{

        private List<Notice> mNoticeList;

        public NoticeAdapter(List<Notice> mNoticeList) {
            this.mNoticeList = mNoticeList;
        }
        @Override
        public NoticeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new NoticeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notice,parent,false));
        }

        @Override
        public void onBindViewHolder(NoticeViewHolder holder, int position) {
            Notice data = mNoticeList.get(position);

            holder.mMenuTextView.setText(data.getNoticemenu());
            holder.mTitleTextView.setText(data.getTitle());
            holder.mContentsTextView.setText(data.getContents());
            holder.mDateTextView.setText(data.getDate());
        }

        @Override
        public int getItemCount() {
            return mNoticeList.size();
        }

        class NoticeViewHolder extends RecyclerView.ViewHolder{
            private TextView mMenuTextView;
            private TextView mTitleTextView;
            private TextView mContentsTextView;
            private TextView mDateTextView;

            public NoticeViewHolder(View noticeView) {
                super(noticeView);
                mMenuTextView=(TextView)noticeView.findViewById(R.id.notice_menu_text);
                mTitleTextView=(TextView)noticeView.findViewById(R.id.notice_title_text);
                mContentsTextView=(TextView)noticeView.findViewById(R.id.notice_contents_text);
                mDateTextView=(TextView)noticeView.findViewById(R.id.notice_date_text);
            }
        }
    }
}
