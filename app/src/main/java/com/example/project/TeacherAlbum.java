package com.example.project;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TeacherAlbum extends AppCompatActivity{
    final int PICTURE_REQUEST_CODE=1;

    FirebaseDatabase database;
    DatabaseReference albumdb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_album);

        final int img[] = {
                R.drawable.board,R.drawable.album,R.drawable.calendar,
                R.drawable.food,R.drawable.home,R.drawable.medicine,
                R.drawable.note,R.drawable.setting,R.drawable.schoolprogram
        };

        AlbumAdapter adapter = new AlbumAdapter(
                getApplicationContext(), // 현재 화면의 제어권자
                R.layout.item_album,
                img);

        Gallery g = (Gallery)findViewById(R.id.gallery1);
        g.setAdapter(adapter);
        final ImageView iv = (ImageView)findViewById(R.id.imageView1);

        g.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) { // 선택되었을 때 콜백메서드
                iv.setImageResource(img[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }//onCreate end

    class AlbumAdapter extends BaseAdapter {
        Context context;
        int layout;
        int img[];
        LayoutInflater inf;

        public AlbumAdapter(Context context, int layout, int[] img) {
            this.context = context;
            this.layout = layout;
            this.img = img;
            inf = (LayoutInflater) context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() { // 보여줄 데이터의 총 개수 - 꼭 작성해야 함
            return img.length;
        }

        @Override
        public Object getItem(int position) { // 해당행의 데이터- 안해도 됨
            return null;
        }

        @Override
        public long getItemId(int position) { // 해당행의 유니크한 id - 안해도 됨
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 보여줄 해당행의 row xml 파일의 데이터를 셋팅해서 뷰를 완성하는 작업
            if (convertView == null) {
                convertView = inf.inflate(layout, null);
            }

            ImageView iv = (ImageView)convertView.findViewById(R.id.imageView1);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setLayoutParams(new Gallery.LayoutParams(300,400));
            iv.setImageResource(img[position]);
            return iv;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //앨범 타이틀바에 album_btn_titlebar 메뉴를 이용해 추가버튼 추가
        getMenuInflater().inflate(R.menu.album_btn_titlebar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.album_write: //추가 버튼이 클릭됬을 때

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                    try {
                        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent,"사진 선택"),PICTURE_REQUEST_CODE);
                    }catch (Exception e){
                        Intent photoPickerIntent = new Intent(this,TeacherAlbum.class);
                        startActivityForResult(photoPickerIntent,PICTURE_REQUEST_CODE);
                    }
                }else{
                    Intent photoPickerIntent = new Intent(this,TeacherAlbum.class);
                    startActivityForResult(photoPickerIntent,PICTURE_REQUEST_CODE);
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICTURE_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {

                //ClipData 또는 Uri를 가져온다
                Uri uri = data.getData();
                ClipData clipData = data.getClipData();
                //data.getClipdata() api 오류시 빌드그래들에서 defaultConfig{} 안 minSdkVersion 16으로 수정

                //이미지 URI 를 이용하여 이미지뷰에 순서대로 세팅한다.
                if(clipData!=null)
                {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHH");
                    Date now = new Date();
                    FirebaseStorage storage = FirebaseStorage.getInstance();

                    for(int i = 0; i < clipData.getItemCount(); i++)
                    {
                        String filename = formatter.format(now)+"_"+(i+1)+ ".png";
                        StorageReference storageRef1 = storage.getReferenceFromUrl("gs://dolbomi1.appspot.com/").child("albumImages/"+filename);
                        Uri urione =  clipData.getItemAt(i).getUri();
                        storageRef1.putFile(urione);

                        FirebaseDatabase.getInstance().getReference().child("Album").push().setValue(filename);
                    } //포문end

                    Toast.makeText(this, "사진업로드 성공", Toast.LENGTH_SHORT).show();
                }
            }//리절트 ok end
        }//픽 리퀘스트 코드 end
    }//액티비티 리절트 end

}
