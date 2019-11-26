package com.example.project;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Holder.AttendanceViewHolder;
import com.example.project.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class TeacherAttendance extends Activity {
    TextView date;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    private RecyclerView list_recyclerview;
    private RecyclerView.Adapter mAdapter;
    TextView attendanceitem_textview;
    Button attendance_save;
    CheckBox attendance_checkBox;
    List<User> iusers = null;
    //    private List<Student> studentList;
    private  User UserModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_attendance);
        date = findViewById(R.id.tvSelectedDate);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        attendanceitem_textview=findViewById(R.id.attendanceitem_textview);
        attendance_save=findViewById(R.id.attendance_save);
        datePickerDialog = new DatePickerDialog(TeacherAttendance.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        date.setText(year + "년" + (month + 1) + "월" + day + "일");
                        new FirebaseDatabaseHelper().readUser(new FirebaseDatabaseHelper.DataStatus() {
                            @Override
                            public void DataIsLoaded(List<User> users, List<String> keys) {
                                new AttendanceViewHolder().setConfig(list_recyclerview,TeacherAttendance.this,users,keys

                                );

                                iusers = users;
                            }

                            @Override
                            public void DataIsInserted() {

                            }

                            @Override
                            public void DataIsUpdated() {

                            }

                            @Override
                            public void DataIsDeleted() {

                            }
                        });{

                        }
                    }
                }, year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
        list_recyclerview=(RecyclerView)findViewById(R.id.attendance_recyclerView);
        attendance_checkBox=findViewById(R.id.attendance_checkBox);
        iusers = new ArrayList<User>();
//        list_recyclerview = new AttendanceViewHolder(iusers);

        // set the adapter object to the Recyclerview
//        list_recyclerview.setAdapter(mAdapter);




        attendance_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<iusers.size();i++){
                    User checkBox=iusers.get(i);
                    if(checkBox.isCheckBox()==true){
                        System.out.println(iusers.get(i).getUsername());
                    }

                }
                FirebaseInstanceId.getInstance().getInstanceId()
                        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                            @Override
                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                if (!task.isSuccessful()) {
                                    Log.w(TAG, "getInstanceId failed", task.getException());
                                    return;
                                }

                                // Get new Instance ID token
                                String token = task.getResult().getToken();

                                // Log and toast
                                String msg = getString(R.string.msg_token_fmt, token);
                                Log.d(TAG, msg);

                                Toast.makeText(TeacherAttendance.this, msg, Toast.LENGTH_SHORT).show();
                                System.out.println(token);
                            }

                        });


                String token = "cekjQJ8ZQd8:APA91bFfwcdk9DiRBItqMwfoqsKyEKvPvoIMaya302EctUwXgANpbwFG7ibifIqgPYWB2wMwZcKTA72gQooQnuWIhoPKWnDse4mz5amyCDAaaHxr7eokW5gl_3xAD-ostwM7DF-XqYHK";
                String title = "제목입니다.";
                String body = "본문입니다.";
                try {
                    String result = new PushMsgTask().execute(token, title, body).get();
                    Log.i("통신결과값", result + " ");
                } catch (Exception e) {
                    Log.e("통신오류", e.toString());
                }
            }


        });


    }




}
