package com.example.project;

import android.os.Bundle;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TeacherAlbum extends AppCompatActivity {

    private int[] imageIDs = new int[] {
            R.drawable.schoolbus,
            R.drawable.schoolprogram,
            R.drawable.setting,
            R.drawable.note,
            R.drawable.medicine,
            R.drawable.home,
            R.drawable.food,
            R.drawable.calendar,
            R.drawable.board,
            R.drawable.album
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_album);

        GridView gridViewImages = (GridView)findViewById(R.id.imageGridview);
        AlbumGridAdapter AlbumGridAdapter = new AlbumGridAdapter(this, imageIDs);
        gridViewImages.setAdapter(AlbumGridAdapter);
    }
}
