package com.example.project;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class CustomCalendarView extends LinearLayout {
    ImageButton NextButton,PreviousButton;
    TextView CurrentDate;

    GridView gridView;
    private static final  int MAX_CALENDAR_DAYS=42;
    Calendar calendar= Calendar.getInstance(Locale.KOREAN);
    Context context;
    SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy년 MM월"  ,Locale.KOREAN);
    SimpleDateFormat monthFormat=new SimpleDateFormat("MMMM",Locale.KOREAN);
    SimpleDateFormat yearFormate=new SimpleDateFormat("yyyy",Locale.KOREAN);
    SimpleDateFormat eventDateFormate=new SimpleDateFormat("yyyy-MM-dd",Locale.KOREAN);
    MyGridAdpater myGridAdpater;
    AlertDialog alertDialog;
    List<Date> dates=new ArrayList<>();
    List<Events> eventsList=new ArrayList<>();
    DBOpenHelper dbOpenHelper;
    public CustomCalendarView(Context context) {
        super(context);
    }

    public CustomCalendarView(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;

        IntializeLayout();
        SetUpCalendar();


        PreviousButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH,-1);
                SetUpCalendar();
            }
        });

        NextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH,1);
                SetUpCalendar();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setCancelable(true);
                final View addView=LayoutInflater.from(parent.getContext()).inflate(R.layout.add_newevent_layout,null);
                final EditText EventName=addView.findViewById(R.id.eventname);
                final TextView EventTime=addView.findViewById(R.id.eventtime);
                ImageButton SetTime=addView.findViewById(R.id.seteventtime);
                Button AddEvent=addView.findViewById(R.id.addevent);
                SetTime.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar=Calendar.getInstance();
                        int hours=calendar.get(Calendar.HOUR_OF_DAY);
                        int minuts=calendar.get(Calendar.MINUTE);
                        TimePickerDialog timePickerDialog=new TimePickerDialog(addView.getContext(), R.style.Theme_AppCompat_Dialog
                                , new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                Calendar c=Calendar.getInstance();
                                c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                c.set(Calendar.MINUTE,minute);
                                c.setTimeZone(TimeZone.getDefault());
                                SimpleDateFormat hformate=new SimpleDateFormat("K:mm a",Locale.KOREAN);
                                String event_Time=hformate.format(c.getTime());
                                EventTime.setText(event_Time);
                            }
                        },hours,minuts,false);
                        timePickerDialog.show();
                    }
                });
                final String date=eventDateFormate.format(dates.get(position));
                final String month=monthFormat.format(dates.get(position));
                final String year=yearFormate.format(dates.get(position));

                AddEvent.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SaveEvent(EventName.getText().toString(), EventTime.getText().toString(),date,month,year);
                        SetUpCalendar();
                        alertDialog.dismiss();
                    }
                });
                builder.setView(addView);
                alertDialog=builder.create();
                alertDialog.show();
            }
        });
    }

    public CustomCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }
    private void SaveEvent(String event,String time,String date,String month,String year){
        dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase database=dbOpenHelper.getWritableDatabase();
        dbOpenHelper.SaveEvent(event,time,date,month,year,database);
        dbOpenHelper.close();
        Toast.makeText(context,"Event saved",Toast.LENGTH_SHORT).show();
    }
    private void IntializeLayout(){
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view =inflater.inflate(R.layout.calendar_layout,this);
        NextButton=view.findViewById(R.id.nextBtn);
        PreviousButton=view.findViewById(R.id.previousBtn);
        CurrentDate=view.findViewById(R.id.current_Date);
        gridView=view.findViewById(R.id.gridView);
    }

    private void SetUpCalendar(){
        String currwntDate=dateFormat.format(calendar.getTime());
        // 초기화 못한 문제 같은데 모르겠음
        CurrentDate.setText(currwntDate);
        dates.clear();
        Calendar monthCalendar=(Calendar)calendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH,1);
        int FirstDayofMonth=monthCalendar.get(Calendar.DAY_OF_WEEK)-1;
        monthCalendar.add(Calendar.DAY_OF_MONTH,-FirstDayofMonth);

        CollectEventPerMonth(monthFormat.format(calendar.getTime()),yearFormate.format(calendar.getTime()));

        while (dates.size()<MAX_CALENDAR_DAYS){
            dates.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH,1);

        }

        myGridAdpater=new MyGridAdpater(context,dates,calendar,eventsList);
        gridView.setAdapter(myGridAdpater);
    }

    private void CollectEventPerMonth(String Month,String year){
        eventsList.clear();
        dbOpenHelper=new DBOpenHelper(context);
        SQLiteDatabase database=dbOpenHelper.getReadableDatabase();
        Cursor cursor=dbOpenHelper.ReadEventsperMonth(Month,year,database);
        while(cursor.moveToNext()){
            String event=cursor.getString(cursor.getColumnIndex(DBStructure.EVENT));
            String time=cursor.getString(cursor.getColumnIndex(DBStructure.TIME));
            String date=cursor.getString(cursor.getColumnIndex(DBStructure.DATE));
            String month=cursor.getString(cursor.getColumnIndex(DBStructure.MONTH));
            String Year=cursor.getString(cursor.getColumnIndex(DBStructure.YEAR));
            Events events=new Events(event,time,date,month,Year);
            eventsList.add(events);

        }
        cursor.close();
        dbOpenHelper.close();
    }
}
