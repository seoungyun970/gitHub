package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project.Model.Album;
import java.util.ArrayList;

public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.VerticalViewHolder>{

    private ArrayList<ArrayList<Album>> AllAlbumList;
    private Context context;


    public VerticalAdapter(Context context, ArrayList<ArrayList<Album>> data)
    {
        this.context = context;
        this.AllAlbumList = data;
    }

    public class VerticalViewHolder extends RecyclerView.ViewHolder{
        protected RecyclerView recyclerView;
        protected TextView album_date;

        public VerticalViewHolder(View view)
        {
            super(view);
            this.album_date=(TextView)view.findViewById(R.id.album_date);
            this.recyclerView = (RecyclerView)view.findViewById(R.id.recyclerViewVertical);
        }

    }


    @NonNull
    @Override
    public VerticalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.album_vertical, null);
        return new VerticalAdapter.VerticalViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull VerticalViewHolder verticalViewHolder, int position) {
        HorizontalAdapter adapter = new HorizontalAdapter(AllAlbumList.get(position));

        verticalViewHolder.recyclerView.setHasFixedSize(true);
        verticalViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context
                , LinearLayoutManager.HORIZONTAL
                ,false));
        verticalViewHolder.recyclerView.setAdapter(adapter);
        //verticalViewHolder.album_date.setText();
        //여기 앨범 날짜 넣기
    }


    @Override
    public int getItemCount() {
        return AllAlbumList.size();
    }
}