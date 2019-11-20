package com.example.project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class TeacherSetting extends Activity {

    final Context context = this;
    LinearLayout childManger;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_setting);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            getActionBar().setBackgroundDrawable(getDrawable(R.color.colorPrimary));
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.childManger:
                Intent intent = new Intent(TeacherSetting.this, TeacherManager.class);
                startActivity(intent);
                break;

            case R.id.signout:
                DialogInterface.OnClickListener signcancelListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };
                DialogInterface.OnClickListener signoutListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                Toast.makeText(getApplicationContext(), "로그아웃 되었습니다.", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(TeacherSetting.this, LoginActivity.class));
                                finish();

                    }
                };
                new AlertDialog.Builder(this)   //프로필 알림창 표시
                        .setTitle("로그아웃")
                        .setMessage("로그아웃 하시겠습니까?")
                        .setPositiveButton("확인", signoutListener)
                        .setNegativeButton("취소", signcancelListener)
                        .show();
                break;

            case R.id.deleteuser:

                DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };
                DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getApplicationContext(), "계정이 삭제되었습니다.", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(TeacherSetting.this, LoginActivity.class));
                                finish();
                            }
                        });
                    }
                };
                new AlertDialog.Builder(this)   //프로필 알림창 표시
                        .setTitle("계정 삭제")
                        .setMessage("삭제된 계정은 복구할 수 없습니다. 탈퇴하시겠습니까?")
                        .setPositiveButton("확인", positiveListener)
                        .setNegativeButton("취소", cancelListener)
                        .show();
                break;
        }

    }
}
