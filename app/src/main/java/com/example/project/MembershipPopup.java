package com.example.project;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class MembershipPopup extends Activity implements View.OnClickListener {
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 10;
    private static final int CROP_FROM_IMAGE = 2;

    private static Uri mImageCaptureUri;
    private ImageView profileImgBtn;
    private String absoultePath;
    private Uri imageUri;

    FirebaseAuth firebaseAuth;
    EditText register_id;
    EditText register_pw;
    EditText register_name;
    RadioButton reg_Parent;
    RadioButton reg_Teacher;
    RadioButton reg_Head;
    Button register_checkBtn;
    ProgressDialog progressDialog;


    ArrayAdapter<String> arrayAdapter;
    static ArrayList<String> arrayIndex = new ArrayList<String>();
    static ArrayList<String> arrayData = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_membership_popup);


        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        // 윈도우 매니저 객체 얻어오고 디스플레이 객체 얻어오기
        int width = (int) (display.getWidth() * 0.9);
        // 얻어온 화면의 폭의 90프로만큼 width에 지정
        int height = (int) (display.getHeight() * 0.7);
        // 얻어온 화면의 높이의 70프로 만큼 height에 지정
        getWindow().getAttributes().width = width;
        //멤버십팝업 레이아웃의 폭을 width로 지정
        getWindow().getAttributes().height = height;
        //멤버십팝업 레이아웃의 폭을 height 지정
        getWindow().setGravity(Gravity.CENTER);
        //멤버십팝업 레이아웃 센터지정

        firebaseAuth = FirebaseAuth.getInstance();
        register_id = (EditText) findViewById(R.id.register_id);
        register_pw = (EditText) findViewById(R.id.register_pw);
        register_name = (EditText) findViewById(R.id.register_name);
        reg_Parent = (RadioButton) findViewById(R.id.reg_Parent);
        reg_Teacher = (RadioButton) findViewById(R.id.reg_Teacher);
        reg_Head = (RadioButton) findViewById(R.id.reg_Head);
        register_checkBtn = (Button) findViewById(R.id.register_checkBtn);
        progressDialog = new ProgressDialog(this);
        register_checkBtn.setOnClickListener(this);
        profileImgBtn = (ImageView) findViewById(R.id.profileImgBtn);

        profileImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, PICK_FROM_ALBUM);
            }
        });

        register_checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (register_id.getText().toString() == null || register_pw.getText().toString() == null || register_name.getText().toString() == null || imageUri == null) {
                    return;
                }
                FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(register_id.getText().toString(), register_pw.getText().toString())
                        .addOnCompleteListener(MembershipPopup.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                final String uid = task.getResult().getUser().getUid();
                                UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(register_name.getText().toString()).build();

                                task.getResult().getUser().updateProfile(userProfileChangeRequest);
                                FirebaseStorage.getInstance().getReference().child("userImages").child(uid).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                            Task<Uri> imageUrl = task.getResult().getStorage().getDownloadUrl();
                                            while(!imageUrl.isComplete());
                                            User user = new User();
                                            user.email = register_id.getText().toString();
                                            user.username = register_name.getText().toString();
                                            user.profileImageUrl = imageUrl.getResult().toString();
                                            user.uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                            FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    MembershipPopup.this.finish();
                                                }
                                            });

                                    }

                                });
                            }
                        });
            }
        });
    }




   public void profilebtn(View view) { //프로필 선택사진 눌렸을때  앨범선택 사진촬영 알림창 표시
        DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doTakePhotoAction();
            }
        };

        DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doTakeAlbumAction();
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

    public void doTakePhotoAction() { //카메라 촬영 후 이미지 가져오기

        Intent cintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //임시로 사용할 파일의 경로 생성
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

        cintent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(cintent, PICK_FROM_CAMERA);

    }

    public void doTakeAlbumAction() { //앨범에서 이미지 가져오기
        //앨범 호출
        Intent aintent = new Intent(Intent.ACTION_PICK);
        aintent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(aintent, PICK_FROM_ALBUM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        profileImgBtn.setImageURI(data.getData());
        imageUri = data.getData();
        if (requestCode != RESULT_OK)
            return;

        switch (requestCode) {

            case PICK_FROM_ALBUM: {
                //이후의 처리가 카메라와 같으므로 일단 break없이 진행
                mImageCaptureUri = data.getData();
                Log.d("Dolbomi", mImageCaptureUri.getPath().toString());
            }

            case PICK_FROM_CAMERA: {
                // 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정
                // 이후 이미지 크롭 어플리케이션을 호출
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

                intent.putExtra("outputX", 80); //크롭한 이미지의 x축 크기
                intent.putExtra("outputY", 80); //크롭한 이미지의 y축 크기
                intent.putExtra("aspectX", 1); //크롭 박스의 x축 비율
                intent.putExtra("aspectY", 1); //크롭 박스의 y축 비율
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_IMAGE); //CROP_FROM_CAMERA case 문 이동
                break;
            }

            case CROP_FROM_IMAGE: {
                //크롭이 된 이후의 이미지를 넘겨 받음
                //이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
                //임시 파일을 삭제

                if (resultCode != RESULT_OK) {
                    return;
                }

                final Bundle extras = data.getExtras();

                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Dolbomi/" + System.currentTimeMillis() + ".jpg";

                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data"); //크롭된 비트맵
                    profileImgBtn.setImageBitmap(photo); //이미지 뷰에 크롭된 비트맵을 보여줌

                    storeCropImage(photo, filePath); //크롭된 이미지를 외부저장소,앨범에 저장
                    absoultePath = filePath;
                    break;
                }

                //임시 파일 삭제
                File f = new File(mImageCaptureUri.getPath());
                if (f.exists()) {
                    f.delete();
                }
            }
        }

    }


    // 비트맵을 저장하는 부분

    private void storeCropImage(Bitmap bitmap, String filePath) {
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Dolbomi";
        File directory_Dolbomi = new File(dirPath);

        if (!directory_Dolbomi.exists()) // Dolbomi디렉토리에 폴더가 없다면 (새로 이미지를 저장할 경우)
            directory_Dolbomi.mkdir();

        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try {
            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(copyFile)));

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {

    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_FROM_ALBUM && resultCode == RESULT_OK)
        {
            profileImgBtn.setImageURI(data.getData());
            imageUri = data.getData();
        }
   */}




