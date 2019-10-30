package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Tdiary_list_adapter extends BaseAdapter {
    Context context;
    ArrayList<Tdiary_list_item> list_itemArrayList;

    TextView nickname;
    TextView title;
    TextView subject;
    TextView date;
    ImageView profile_imageview;


    public Tdiary_list_adapter(Context context, ArrayList<Tdiary_list_item> list_itemArrayList) {
        this.context = context;
        this.list_itemArrayList = list_itemArrayList;
    }

    @Override
    public int getCount() {
        return this.list_itemArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return list_itemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.teacher_diary, null);
            nickname = (TextView) convertView.findViewById(R.id.Nickname);
            title = (TextView) convertView.findViewById(R.id.Title);
            subject = (TextView) convertView.findViewById(R.id.Subject);
            date = (TextView) convertView.findViewById(R.id.Date);
            profile_imageview = (ImageView) convertView.findViewById(R.id.Profile_imageview);
        }

        nickname.setText(list_itemArrayList.get(position).getNickname());
        title.setText(list_itemArrayList.get(position).getTitle());
        subject.setText(list_itemArrayList.get(position).getSubject());
        date.setText((CharSequence) list_itemArrayList.get(position).getDate());
        profile_imageview.setImageResource(list_itemArrayList.get(position).getProfile_image());

        return convertView;
    }
}
