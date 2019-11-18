package com.example.project;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class ItemAttendance extends Activity {
    TextView date;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_attendance);
        date = findViewById(R.id.tvSelectedDate);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(ItemAttendance.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        date.setText(year + "년" + (month + 1) + "월" + day + "일");
                    }
                }, year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }
}
