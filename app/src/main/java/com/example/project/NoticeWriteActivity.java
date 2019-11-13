package com.example.project;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.project.Model.Notice;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NoticeWriteActivity  extends AppCompatActivity implements View.OnClickListener {
    private Spinner noticeWriteSpinner;
    private EditText noticeWriteTitleText;
    private EditText noticeWriteContentsText;
    private ImageView mWriteImageView;
    private String uid;

    String mCurrentPhotoPath; //실제 사진 파일 경로
    Uri photoURI;
    Uri albumURI;
    Uri imageUri;

    private static final int MY_PERMISSION_CAMERA = 1111;
    private static final int REQUEST_TAKE_ALBUM = 3333;
    private static final int REQUEST_IMAGE_CROP = 4444;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_notice_activity);


        noticeWriteSpinner=findViewById(R.id.grade_spinner);
        noticeWriteTitleText=findViewById(R.id.notice_write_title_text);
        noticeWriteContentsText=findViewById(R.id.notice_write_content_text);
        mWriteImageView = findViewById(R.id.notice_write_image);


        findViewById(R.id.notice_upload_btn).setOnClickListener(this);

        String[] str=getResources().getStringArray(R.array.notice_grade_array);
        noticeWriteSpinner.setPrompt("반을 선택해주세요.");
        ArrayAdapter gradeAdapter= new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,str);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        noticeWriteSpinner.setAdapter(gradeAdapter);
        noticeWriteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        findViewById(R.id.notice_upload_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String noticemenu = noticeWriteSpinner.getSelectedItem().toString();
        String title = noticeWriteTitleText.getText().toString().trim();
        String contents = noticeWriteContentsText.getText().toString().trim();


        //제목, 내용, 이름이 비었는지 아닌지를 체크 한다.
        if (TextUtils.isEmpty(noticemenu)) {
            Toast.makeText(this, "반을 선택해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, "제목을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(contents)) {
            Toast.makeText(this, "내용을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseStorage.getInstance().getReference().child("noticeImages").child(uid).putFile(albumURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                Task<Uri> imageUrl = task.getResult().getStorage().getDownloadUrl();
                while(!imageUrl.isComplete());

                SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy년 MM월dd일 HH시mm분ss초");
                Date time = new Date();
                String time1 = format1.format(time);

                Notice notice = new Notice();
                notice.noticemenu = noticeWriteSpinner.getSelectedItem().toString();
                notice.title = noticeWriteTitleText.getText().toString();
                notice.contents = noticeWriteContentsText.getText().toString();
                notice.date = time1;
                notice.noticeImageUrl = imageUrl.getResult().toString();

                FirebaseDatabase.getInstance().getReference().child("Notice").push().setValue(notice).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "가입성공", Toast.LENGTH_LONG).show();
                        NoticeWriteActivity.this.finish();
                    }
                });
            }
        });
    }

    public void albumBtn(View view){
        checkPermission();

        DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getAlbum();
            }
        };

        DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };

        new AlertDialog.Builder(this)   //프로필 알림창 표시
                .setTitle("업로드할 이미지 선택")
                .setNeutralButton("앨범 선택", albumListener)
                .setNegativeButton("취소", cancelListener)
                .show();
    }

    public File createImageFile() throws IOException {
        //Create an image file name

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";
        File imageFile = null;
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "gyeom");

        if (!storageDir.exists()) {
            Log.i("mCurrentPhotoPath1", storageDir.toString());
            storageDir.mkdir();
        }

        imageFile = new File(storageDir, imageFileName);
        mCurrentPhotoPath = imageFile.getAbsolutePath();

        return imageFile;
    }

    private void getAlbum() {
        Log.i("getAlbum", "Call");
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_TAKE_ALBUM);
    }

    private void galleryAddPic() {
        Log.i("galleryAddPic", "Call");
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        //해당 경로에 있는 파일을 객체화 (새로 파일을 만든다는 것)

        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
        Toast.makeText(this, "사진이 앨범에 저장되었습니다.", Toast.LENGTH_SHORT).show();

    }

    public void cropImage() {
        Log.i("cropImage", "Call");
        Log.i("cropImage", "photoURI : " + photoURI + "/albumURI : " + albumURI);

        Intent cropIntent = new Intent("com.android.camera.action.CROP");

        //50x50픽셀 미만은 편집할수 없다는 문구 처리 + 갤러리, 포토 둘다 호환하는 방법
        cropIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        cropIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        cropIntent.setDataAndType(photoURI, "image/*");

        cropIntent.putExtra("outputX", 200);
        cropIntent.putExtra("outputY", 200);
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);

        cropIntent.putExtra("scale", true);
        cropIntent.putExtra("output", albumURI);//크랍된 이미지를 해당경로에 저장

        startActivityForResult(cropIntent, REQUEST_IMAGE_CROP);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) { //선택한 사진 데이터 처리
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_TAKE_ALBUM:
                if (resultCode == Activity.RESULT_OK){

                    if(data.getData() != null){
                        try {
                            File albumFile = null;
                            albumFile=createImageFile();
                            photoURI=data.getData();
                            albumURI=Uri.fromFile(albumFile);
                            cropImage();

                        }catch (Exception e){
                            Log.e("TAKE_ALBUM_SINGLE ERROR", e.toString());
                        }
                    }
                }
                break;
            case REQUEST_IMAGE_CROP:
                if(resultCode == Activity.RESULT_OK){
                    galleryAddPic();
                    mWriteImageView.setImageURI(albumURI);
                }
                break;
        }
    }
    private void checkPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            //처음 호출엔 if()안의 부분은 false로 리턴 됨 -> else{..}의 요청으로 넘어감

            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                new AlertDialog.Builder(this)
                        .setTitle("알림")
                        .setMessage("저장소 권한이 거부되었습니다. 사용을 원하시면 해당 권한을 직접 허용하셔야 합니다.")
                        .setNeutralButton("설정", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent =new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package: "+getPackageName()));
                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSION_CAMERA);
            }
        }
    }
}
