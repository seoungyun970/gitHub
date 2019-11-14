package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Model.Eatting;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TeacherEatting extends AppCompatActivity  {
    private RecyclerView mEattingRecycleView;

    private EattingAdapter mEattingAdapter;
    private List<Eatting> mEattingList;

    LinearLayoutManager mLayouyManagerEatting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_eatting);
        mEattingRecycleView=findViewById(R.id.recycler_diary);
        mEattingList=new ArrayList<>();

//        mEattingList.add(new Eatting("반갑습니다 여러분","","imageView","","","","",""));
//        mEattingList.add(new Eatting("Hello","Hi","server"));
//        mEattingList.add(new Eatting("OK","Yes sir","java"));
//        mEattingList.add(new Eatting("안녕하세요","하이룽","php"));
//        mEattingList.add(new Eatting("ㅎㅅㅎ","ㅇㅅㅇ!!","css"));

        mLayouyManagerEatting = new LinearLayoutManager(this);
        mEattingRecycleView.setLayoutManager(mLayouyManagerEatting);

        mEattingAdapter=new EattingAdapter(mEattingList);
        mEattingRecycleView.setAdapter(mEattingAdapter);

        FloatingActionButton actionButton = findViewById(R.id.floatingActionButton);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.floatingActionButton:
                        Intent actionButtonIntent = new Intent(TeacherEatting.this,TeacherEattingItem.class);
                        startActivity(actionButtonIntent);
                        break;
                }
            }
        });

    }
    private class EattingAdapter extends RecyclerView.Adapter<TeacherEatting.EattingAdapter.EattingViewHolder>{
        private List<Eatting> mEattingList;

        public EattingAdapter(List<Eatting> mEattingList) {
            this.mEattingList = mEattingList;
        }
        @Override
        public EattingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new EattingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_eatting,parent,false));
        }
        @Override
        public void onBindViewHolder(EattingViewHolder holder, int position) {
            Eatting data = mEattingList.get(position);
            holder.standardTime.setText(data.getNowTime());
            holder.nowDate.setText(data.getEattingTime());
            holder.imageView.setImageResource(R.drawable.foodselect);
            holder.imageView2.setImageResource(R.drawable.foodselect);
            holder.imageView3.setImageResource(R.drawable.foodselect);
            holder.eattingFirst.setText(data.getEattingFirst());
            holder.eattingSecond.setText(data.getEattingSecond());
            holder.eattingThrid.setText(data.getEattingThird());
        }
        @Override
        public int getItemCount() {
            return mEattingList.size();
        }

        class EattingViewHolder extends RecyclerView.ViewHolder{
            private TextView standardTime;
            private TextView nowDate;
            private ImageView imageView;
            private ImageView imageView2;
            private ImageView imageView3;
            private TextView eattingFirst;
            private TextView eattingSecond;
            private TextView eattingThrid;

            public EattingViewHolder(View itemView) {
                super(itemView);
                standardTime=itemView.findViewById(R.id.standardTime);
                nowDate =itemView.findViewById(R.id.nowDate);
                imageView=itemView.findViewById(R.id.first_eat_image);
                imageView2=itemView.findViewById(R.id.imageView2);
                imageView3=itemView.findViewById(R.id.imageView3);
                eattingFirst=itemView.findViewById(R.id.eattingFirst);
                eattingSecond=itemView.findViewById(R.id.eattingSecond);
                eattingThrid=itemView.findViewById(R.id.eattingThird);

            }
        }
    }
}
