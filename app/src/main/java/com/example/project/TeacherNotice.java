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

        mNoticeList.add(new Notice("원 공지",
                "특별활동 미술수업 시간 변경 안내",
                "미술 선생님의 교육 일정으로 매주 금요일마다 진행되었던 특별활동 수업이 11월부터 매주 수요일로 변경되었습니다 :) \n " +
                        "매 순간 아이들이 안전하고, " +
                        "행복한 시간을 보낼 수 있도록 노력하는 돌보미어린이집이 되도록 노력하겠습니다.",
                "2019-11-07"));

        mNoticeList.add(new Notice("꽃님반 공지",
                "미아실종예방 사전등록 신청서 작성해 주세요.",
                "미아실종예방 사전등록 신청서가 나갑니다. \n꼼꼼하게 읽으시고 체크해서 월요일까지 어린이집으로 꼭 보내주시길 바랍니다. \n \n" +
                        "사전등록은 아동 등이 실종되었을 때를 대비해 미리 경찰에 지문과 얼굴 사진, " +
                        "기타 신상 정보를 등록하고 실종 시 등록된 자료를 활용해 보다 신속히 찾아주는 제도 입니다.\n" +
                        "\n 많은 참여 부탁드립니다:-)",
                "2018-9-25"));

        mNoticeList.add(new Notice("원 공지",
                "대구시 지원 어린이집 방문 간호사서비스",
                "대구시에서 대구시간호사와회와 협력하에 영.유아 건강 관리체계를 마련하고자\n" +
                        "어린이집 방문간호사서비스를 시행하게 되었습니다. 본 사업은 방문간호사가 월2회 " +
                        "주기적으로 방문하여 영.유아의 신체계측, 건상이상 조기발견 및 예방으로 건강한 성장" +
                        " 지원 및 안심하고 맡길 수 있는 보육환경조성을 목적으로 합니다.\n\n" +
                        "- 영유아 신체계측 등을 통해 건강이상 조기발견(건강기록부 작성)\n" +
                        "-아동학대 징후 발견시 市에 보고\n\n" +
                        "-보육교직원을 대상으로 돌연사 예방법 등 응급상황시 대처법 교육\n\n" +
                        "-어린이집 영유아에게 필요한 건강.위생 등 관련정보 제공 등",
                "2018-5-4"));

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
