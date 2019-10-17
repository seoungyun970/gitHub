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
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class MembershipPopup extends Activity implements View.OnClickListener {
    private static final int  PICK_FROM_CAMERA=0;
    private static final int  PICK_FROM_ALBUM=1;
    private static final int  CROP_FROM_CAMERA=2;

    private static Uri mImageCaptureUri;
    private ImageView profileImgBtn;

    FirebaseAuth firebaseAuth;
    DatabaseReference mDatabase;
    EditText register_id;
    EditText register_pw;
    EditText register_name;
    RadioButton reg_Parent;
    RadioButton reg_Teacher;
    RadioButton reg_Head;
    Button register_checkBtn;
    ProgressDialog progressDialog;


    /*
        ArrayAdapter<String> arrayAdapter;
        static ArrayList<String> arrayIndex =  new ArrayList<String>();
        static ArrayList<String> arrayData = new ArrayList<String>();

        String id;
        String pw;
        String name;
        String job;
        String gender = "";
        String sort = "id";
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_membership_popup);


        Display display=((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        // 윈도우 매니저 객체 얻어오고 디스플레이 객체 얻어오기
        int width= (int) (display.getWidth() *0.9);
        // 얻어온 화면의 폭의 90프로만큼 width에 지정
        int height = (int) (display.getHeight()*0.7);
        // 얻어온 화면의 높이의 70프로 만큼 height에 지정
        getWindow().getAttributes().width=width;
        //멤버십팝업 레이아웃의 폭을 width로 지정
        getWindow().getAttributes().height=height;
        //멤버십팝업 레이아웃의 폭을 height 지정
        getWindow().setGravity(Gravity.CENTER);
        //멤버십팝업 레이아웃 센터지정

        firebaseAuth = FirebaseAuth.getInstance();
        register_id = (EditText)findViewById(R.id.register_id);
        register_pw = (EditText)findViewById(R.id.register_pw);
        register_name = (EditText)findViewById(R.id.register_name);
        reg_Parent = (RadioButton)findViewById(R.id.reg_Parent);
        reg_Teacher = (RadioButton)findViewById(R.id.reg_Teacher);
        reg_Head = (RadioButton)findViewById(R.id.reg_Head);
        register_checkBtn = (Button)findViewById(R.id.register_checkBtn);
        progressDialog = new ProgressDialog(this);

        register_checkBtn.setOnClickListener(this);

        profileImgBtn=findViewById(R.id.profileImgBtn);
        profileImgBtn.setOnClickListener(this);
    }
/*
    public void setInsertMode(){
        register_id.setText("");
        register_pw.setText("");
        register_name.setText("");
        reg_Male.setChecked(false);
        reg_Female.setChecked(false);
    }

    private AdapterView.OnItemClickListener onClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.e("On Click", "position = " + position);
            Log.e("On Click", "Data: " + arrayData.get(position));
            String[] tempData = arrayData.get(position).split("\\s+");
            Log.e("On Click", "Split Result = " + tempData);
            register_id.setText(tempData[0].trim());
            register_pw.setText(tempData[1].trim());
            register_name.setText(tempData[2].trim());
        }
    };

    public boolean IsExistID(){
        boolean IsExist = arrayIndex.contains(ID);
        return IsExist;
    }
    */



    //사용자등록
    private void registerUser(){
        //사용자가 입력하는 email, password를 가져온다.
        String email = register_id.getText().toString().trim();
        String password = register_pw.getText().toString().trim();
        //email과 password가 비었는지 아닌지를 체크 한다.
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Email을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Password를 입력해 주세요.", Toast.LENGTH_SHORT).show();
        }

        //email과 password가 제대로 입력되어 있다면 계속 진행된다.
        progressDialog.setMessage("등록중입니다. 기다려 주세요...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String email = register_id.getText().toString().trim();
                        String pwd = register_pw.getText().toString().trim();

                        if(task.isSuccessful()){
                            Toast.makeText(MembershipPopup.this, "가입성공", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        } else {
                            //에러발생시
                            Toast.makeText(MembershipPopup.this, "가입실패", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view == register_checkBtn) {
            //TODO
            registerUser();
        }

        if(view.getId()==R.id.profileImgBtn){
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

            new AlertDialog.Builder(this)
                    .setTitle("업로드할 이미지 선택")
                    .setPositiveButton("사진 촬영",cameraListener)
                    .setNeutralButton("앨범 선택",albumListener)
                    .setNegativeButton("취소",cancelListener)
                    .show();
        }
    }
    private void doTakePhotoAction(){ //카메라 촬영 후 이미지 가져오기
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //임시로 사용할 파일의 경로 생성
        String url="tmp_"+String.valueOf(System.currentTimeMillis())+".jpg";
        mImageCaptureUri= Uri.fromFile(new File(Environment.getExternalStorageDirectory(),url));

        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,mImageCaptureUri);

        startActivityForResult(intent,PICK_FROM_CAMERA);
    }
    private void doTakeAlbumAction(){ //앨범에서 이미지 가져오기
        //앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent,PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != RESULT_OK)
            return;

        switch (requestCode){
            case CROP_FROM_CAMERA:
            {
                //크롭이 된 이후의 이미지를 넘겨 받음
                //이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
                //임시 파일을 삭제

                final Bundle extras = data.getExtras();

                if (extras != null){
                    Bitmap photo = extras.getParcelable("data"); //크롭된 비트맵
                    profileImgBtn.setImageBitmap(photo); //이미지 뷰에 크롭된 비트맵을 보여줌
                }

                //임시 파일 삭제
                File f = new File(mImageCaptureUri.getPath());
                if(f.exists()){
                    f.delete();
                }
                break;
            }

            case PICK_FROM_ALBUM:
            {
                //이후의 처리가 카메라와 같으므로 일단 break없이 진행
                mImageCaptureUri=data.getData();
            }

            case PICK_FROM_CAMERA:
            {
                // 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정
                // 이후 이미지 크롭 어플리케이션을 호출
                Intent intent=new Intent("com.android.camera.action.CROP");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.setDataAndType(mImageCaptureUri,"image/*");

                //CROP할 이미지를 270*250 크기로 저장
                intent.putExtra("outputX",100); //크롭한 이미지의 x축 크기
                intent.putExtra("outputY",100); //크롭한 이미지의 y축 크기
                intent.putExtra("aspectX",1); //크롭 박스의 x축 비율
                intent.putExtra("aspectY",1); //크롭 박스의 y축 비율
                intent.putExtra("scale",true);
                intent.putExtra("return-data",true);
                startActivityForResult(intent,CROP_FROM_CAMERA); //CROP_FROM_CAMERA case 문 이동
                break;
            }

        }
    }

}