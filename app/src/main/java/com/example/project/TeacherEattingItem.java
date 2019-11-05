package com.example.project;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TeacherEattingItem extends AppCompatActivity implements TimePicker.OnTimeChangedListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_eatting_item);
        Calendar c = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(TeacherEattingItem.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {



            @Override

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                // TODO Auto-generated method stub

                try {

                    Date d = new SimpleDateFormat
                            ("yyyy-MM-dd", Locale.getDefault()).parse(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
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



    }


    @Override
    public void onTimeChanged(TimePicker timePicker, int i, int i1) {

    }
}
