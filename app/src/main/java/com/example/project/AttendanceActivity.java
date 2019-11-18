package com.example.project;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class AttendanceActivity extends Activity {

    Button selectDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance_layout);
        Button selectDate = findViewById(R.id.btnDate);


        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDate=new Intent(AttendanceActivity.this,ItemAttendance.class);
                startActivity(intentDate);
            }
        });
    }
}






