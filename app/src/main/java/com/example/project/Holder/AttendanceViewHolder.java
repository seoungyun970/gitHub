package com.example.project.Holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.project.Model.User;
import com.example.project.R;

import java.util.List;

public class AttendanceViewHolder {

    private Context mContext;
    private UserAdapter userAdapter;


    public void setConfig(RecyclerView recyclerView,Context context,List<User> users,List<String> keys){
        mContext=context;
        userAdapter=new UserAdapter(users,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(userAdapter);
    }
    class UserItemView extends RecyclerView.ViewHolder {
        public ImageView attendanceitem_imageview;
        private TextView attendanceitem_textview;
        private CheckBox attendance_checkBox;
        private String key;


        public UserItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.item_attendance_bottom, parent, false));
            attendanceitem_imageview = (ImageView) itemView.findViewById(R.id.attendanceitem_imageview);
            attendanceitem_textview = (TextView) itemView.findViewById(R.id.attendanceitem_textview);
//                    attendance_checkBox =(CheckBox) itemView.findViewById(R.id.attendance_checkBox);
        }

        public void bind(User user, String key) {
//            attendanceitem_imageview.setImageURI(user.getProfileImageUrl());

            attendanceitem_textview.setText(user.getUsername());

            this.key = key;
        }

    }
    class UserAdapter extends RecyclerView.Adapter<UserItemView> {
            private List<User> userList;
            private  List<String> mkeys;
            public UserAdapter(List<User> userList, List<String> mkeys) {
                this.userList = userList;
                this.mkeys = mkeys;
            }

            @NonNull
            @Override
            public UserItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new UserItemView(parent);
            }

            @Override
            public void onBindViewHolder(@NonNull UserItemView holder, int position) {
                holder.bind(userList.get(position),mkeys.get(position));
                Glide.with(holder.attendanceitem_imageview.getContext())
                        .load(userList.get(position).profileImageUrl)
                        .apply(new RequestOptions())
                        .into(holder.attendanceitem_imageview);
            }

            @Override
            public int getItemCount() {
                return userList.size();
            }
        }





}
