package com.example.project;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Model.Album;

import java.util.ArrayList;


public class TeacherAlbum extends AppCompatActivity{

    private ArrayList<ArrayList<Album>> allAlbumList = new ArrayList();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_vertical);


        RecyclerView view = findViewById(R.id.recyclerViewVertical);
        VerticalAdapter verticalAdapter = new VerticalAdapter(this, allAlbumList);

        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        view.setAdapter(verticalAdapter);

        this.initializeData();
    }
    public void initializeData()
    {
        ArrayList<Album> albumList1 = new ArrayList();
        albumList1.add(new Album(R.drawable.album));
        albumList1.add(new Album(R.drawable.board));
        albumList1.add(new Album(R.drawable.calendar));
        albumList1.add(new Album(R.drawable.chat));
        albumList1.add(new Album(R.drawable.food));
        albumList1.add(new Album(R.drawable.home));
        albumList1.add(new Album(R.drawable.medicine));
        albumList1.add(new Album(R.drawable.note));
        albumList1.add(new Album(R.drawable.school_bus));
        allAlbumList.add(albumList1);

        ArrayList<Album> albumList2 = new ArrayList();
        albumList2.add(new Album(R.drawable.home));
        allAlbumList.add(albumList2);

        ArrayList<Album> albumList3 = new ArrayList();
        albumList3.add(new Album(R.drawable.medicine));
        albumList3.add(new Album(R.drawable.note));
        albumList3.add(new Album(R.drawable.school_bus));
        allAlbumList.add(albumList3);
    }
}
