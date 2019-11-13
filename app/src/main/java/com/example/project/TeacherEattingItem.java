package com.example.project;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TeacherEattingItem extends AppCompatActivity implements TimePicker.OnTimeChangedListener{
    Button saveEating;
    ImageView first_eat_image;
    ImageView second_eat_image;
    ImageView third_eat_image;

    String mCurrentPhotoPath; //실제 사진 파일 경로
    Uri imageUri;
    Uri photoURI;
    Uri albumURI;

    private static final int MY_PERMISSION_CAMERA = 1111;
    private static final int REQUEST_TAKE_PHOTO = 2222;
    private static final int REQUEST_TAKE_ALBUM = 3333;
    private static final int REQUEST_IMAGE_CROP = 4444;

    int select_photo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_eatting_item);
        Calendar c = Calendar.getInstance();
        saveEating=findViewById(R.id.saveEating);

        DatePickerDialog datePickerDialog = new DatePickerDialog(TeacherEattingItem.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {



            @Override

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                // TODO Auto-generated method stub

                try {
                    Locale locale=new Locale("ko","KR");
                    Date d = new SimpleDateFormat


















                            
                            ("yyyy-MM-dd", locale.getDefault()).parse(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                    TextView nowDate=findViewById(R.id.nowDate);
                    nowDate.setText(String.valueOf(year)+"년 "+String.valueOf(monthOfYear)+"월 "+String.valueOf((dayOfMonth))+"일");
                } catch (Exception e) {

                    // TODO: handle exception

                    e.printStackTrace();

                }

            }

        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));



        datePickerDialog.getDatePicker().setCalendarViewShown(false);

        datePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        datePickerDialog.show();

        first_eat_image = findViewById(R.id.first_eat_image);
        second_eat_image = findViewById(R.id.second_eat_image);
        third_eat_image = findViewById(R.id.third_eat_image);

    }


    @Override
    public void onTimeChanged(TimePicker timePicker, int i, int i1) {

    }

    public void onClick(View view) {
        switch (view.getId()){

            case R.id.first_eat_image:
                select_photo=1;
                albumDialog();
                break;

            case R.id.second_eat_image:
                select_photo=2;
                albumDialog();
                break;

            case R.id.third_eat_image:
                select_photo=3;
                albumDialog();
                break;

            case R.id.saveEating:

        }
    }

    private  void albumDialog(){
        checkPermission();

        DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                captureCamera();
            }
        };

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
                .setPositiveButton("사진 촬영", cameraListener)
                .setNeutralButton("앨범 선택", albumListener)
                .setNegativeButton("취소", cancelListener)
                .show();
    }

    private void captureCamera() {
        String state = Environment.getExternalStorageState();
        //외장 메모리 검사

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    Log.e("captureCamera Error", ex.toString());
                }
                if (photoFile != null) {
                    //getUriForFile의 두번째 인자는 Manifest provider의 authorites와 일치해야함

                    Uri providerURI = FileProvider.getUriForFile(this, getPackageName(), photoFile);
                    imageUri = providerURI;

                    // 인텐트에 전달할 때는 FileProvider의 Return값인 content://로만, providerURI의 값에 카메라 데이터를 넣어 보냄
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, providerURI);

                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
            }
        } else {
            Toast.makeText(this, "저장공간이 접근 불가능한 기기입니다", Toast.LENGTH_SHORT).show();
            return;
        }
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



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { //선택한 사진 데이터 처리
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        Log.i("REQUEST_TAKE_PHOTO","OK");
                        galleryAddPic();

                        if(resultCode == Activity.RESULT_OK){
                            galleryAddPic();
                            if(select_photo==1){
                                first_eat_image.setImageURI(imageUri);
                            }else if (select_photo==2){
                                second_eat_image.setImageURI(imageUri);
                            }else if(select_photo==3){
                                third_eat_image.setImageURI(imageUri);
                            }
                        }
                    } catch (Exception e) {
                        Log.e("REQUEST_TAKE_PHOTO", e.toString());
                    }
                } else {
                    Toast.makeText(TeacherEattingItem.this, "사진찍기를 취소하였습니다.", Toast.LENGTH_SHORT).show();
                }
                break;

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
                    if(select_photo==1){
                        first_eat_image.setImageURI(albumURI);
                    }else if (select_photo==2){
                        second_eat_image.setImageURI(albumURI);
                    }else if(select_photo==3){
                        third_eat_image.setImageURI(albumURI);
                    }
                }
                break;
        }
    }

    private void checkPermission(){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //처음 호출엔 if()안의 부분은 false로 리턴 됨 -> else{..}의 요청으로 넘어감

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                    (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))) {
                new AlertDialog.Builder(this)
                        .setTitle("알림")
                        .setMessage("저장소 권한이 거부되었습니다. 사용을 원하시면 해당 권한을 직접 허용하셔야 합니다.")
                        .setNeutralButton("설정", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package: " + getPackageName()));
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
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, MY_PERMISSION_CAMERA);
            }
        }
    }
}
