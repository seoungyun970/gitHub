package com.example.project;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Holder.AttendanceViewHolder;
import com.example.project.Holder.DiaryViewHolder;
import com.example.project.Model.Diary;
import com.example.project.Model.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

//                String data = "";
//                List<User> stList = ((AttendanceViewHolder) mAdapter)
//                        .userList();
//                for (int i = 0; i < stList.size(); i++) {
//                    User singleStudent = stList.get(i);
//                    if (attendance_checkBox.isSelected() == true) {
//                        data = data + "\n" + singleStudent.getName().toString();
//                    }
//                }
                for(int i=0;i<iusers.size();i++){
                    User checkBox=iusers.get(i);
                    if(checkBox.isCheckBox()==true){
                        System.out.println(iusers.get(i).getUsername());
                    }

                }

//                Toast.makeText(TeacherAttendance.this,
//                        "이름: \n" + data, Toast.LENGTH_LONG)
//                        .show();

//                Toast.makeText(view.getContext(),"Click on Checkbox: "
//                +attendance_checkBox.isChecked(),Toast.LENGTH_LONG).show();
//                String result="";
//                for(int i=0;i< iusers.size();i++){
//                    attendance_checkBox.setText(String.valueOf(iusers.get(i)));
//                    result += attendance_checkBox.getText().toString();
//                    TextView attendanceResult=findViewById(R.id.attendanceResult);
//                    attendanceResult.setText(result+" ");
//                }
            }
        });
    }
}
