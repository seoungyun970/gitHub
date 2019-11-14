package com.example.project;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.Model.Album;

import java.util.ArrayList;

public class TeacherAlbum extends AppCompatActivity implements View.OnClickListener{
    GridView mGridView;
    AlbumListAdapter albumListAdapter;
    ArrayList<Album> list_gridArrayList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_album);

        findViewById(R.id.album_fab).setOnClickListener(this);
        mGridView=(GridView) findViewById(R.id.grid_listview);
        list_gridArrayList=new ArrayList<Album>();

        list_gridArrayList.add(new Album(R.drawable.album));
        list_gridArrayList.add(new Album(R.drawable.album));
        list_gridArrayList.add(new Album(R.drawable.album));
        list_gridArrayList.add(new Album(R.drawable.album));
        list_gridArrayList.add(new Album(R.drawable.album));
        list_gridArrayList.add(new Album(R.drawable.album));
        list_gridArrayList.add(new Album(R.drawable.album));

        albumListAdapter=new AlbumListAdapter(TeacherAlbum.this,list_gridArrayList);
        mGridView.setAdapter(albumListAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TeacherAlbum.this ,list_gridArrayList.get(position).toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) { //앨범 플로팅버튼 눌렸을 때

    }
}
