package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.project.Model.Album;

import java.util.ArrayList;

public class AlbumListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Album> list_albumArrayList;
    ViewHolder viewholder;


    public AlbumListAdapter(Context context, ArrayList<Album> list_albumArrayList) {
        this.context = context;
        this.list_albumArrayList = list_albumArrayList;
    }

    @Override
    public int getCount() {
       return this.list_albumArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return list_albumArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_album,null);
            viewholder=new ViewHolder();
            viewholder.gridImage=(ImageView) convertView.findViewById(R.id.item_gridImage);
            convertView.setTag(viewholder);
        }else{
            viewholder=(ViewHolder)convertView.getTag();
        }
        viewholder.gridImage.setImageResource(list_albumArrayList.get(position).getGridimage());

        return convertView;
    }

    class ViewHolder{
        ImageView gridImage;
    }
}
