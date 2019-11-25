package com.example.project.Holder;

import android.view.View;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project.R;

public class AlbumViewHolder extends RecyclerView.ViewHolder {
    public ImageView mAlbumImageView;

    public AlbumViewHolder(View itemview){
        super(itemview);
        mAlbumImageView=(ImageView)itemview.findViewById(R.id.album_imageView);

    }
}
