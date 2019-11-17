package com.example.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Model.Album;

import java.util.ArrayList;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.HorizontalViewHolder> {

    private ArrayList<Album> dataList;

    public HorizontalAdapter(ArrayList<Album> data)
    {
        this.dataList = data;
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder{
        protected ImageView image;

        public HorizontalViewHolder(View view)
        {
            super(view);
            image = view.findViewById(R.id.album_image);
        }
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.album_horizontal, null);

        return new HorizontalAdapter.HorizontalViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HorizontalViewHolder horizontalViewHolder, int position)
    {
        horizontalViewHolder
                .image
                .setImageResource(dataList.get(position).getResourceID());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}