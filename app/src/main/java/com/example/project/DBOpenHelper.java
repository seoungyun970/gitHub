package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.sql.PreparedStatement;
import java.time.Year;

public class DBOpenHelper extends SQLiteOpenHelper {

    private final static String CREATE_EVENTS_TABLE="create  table "+DBStructure.EVENT_TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT, "
            +DBStructure.EVENT+" TEXT, "+DBStructure.TIME+" TEXT, "+DBStructure.DATE+" TEXT, "+DBStructure.MONTH+" TEXT, "
            +DBStructure.YEAR+" TEXT)";
    private static final String DROP_EVENTS_TABLE="DROP TABLE IF EXISTS "+DBStructure.EVENT_TABLE_NAME;
    public DBOpenHelper(@Nullable Context context) {
        super(context, DBStructure.DB_NAME, null, DBStructure.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("CREATE TABLE eventstable ( ");
            sb.append("ID INTEGER PRIMARY KEY AUTOINCREMENT, ");
            sb.append("event TEXT, ");
            sb.append("time TEXT, ");
            sb.append("date TEXT, ");
            sb.append("month TEXT, ");
            sb.append("year TEXT);");
            db.execSQL(sb.toString());
//            db.execSQL(CREATE_EVENTS_TABLE);
            Log.i("DB생성완료", "됨");
        } catch(Exception e) {
            Log.e("DB생성오류" , e.toString());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DROP_EVENTS_TABLE);
        onCreate(db);
    }

    public void SaveEvent(String event,String time,String date,String month,String year,SQLiteDatabase database){
        ContentValues contentValues=new ContentValues();
        contentValues.put(DBStructure.EVENT,event);
        contentValues.put(DBStructure.TIME,time);
        contentValues.put(DBStructure.DATE,date);
        contentValues.put(DBStructure.MONTH,month);
        contentValues.put(DBStructure.YEAR,year);
        database.insert(DBStructure.EVENT_TABLE_NAME,null,contentValues);

    }

    public Cursor ReadEvents(String date,SQLiteDatabase database){
        String [] Projections={DBStructure.EVENT,DBStructure.TIME,DBStructure.DATE,DBStructure.MONTH,DBStructure.YEAR};
        String Selection=DBStructure.DATE+"=?";
        String [] SelectionArgs={date};
        return database.query(DBStructure.EVENT_TABLE_NAME,Projections,Selection,SelectionArgs,null,null,null);
    }

    public Cursor ReadEventsperMonth(String month,String year,SQLiteDatabase database){
        String [] Projections={DBStructure.EVENT,DBStructure.TIME,DBStructure.DATE,DBStructure.MONTH,DBStructure.YEAR};
        String Selection=DBStructure.MONTH+"=? and "+DBStructure.YEAR+"=?";
        String [] SelectionArgs={month,year};


        return database.query(DBStructure.EVENT_TABLE_NAME,Projections,Selection,SelectionArgs,null,null,null);
    }

    public Cursor readEventsperMonth(String month,String year) {
        String sql = String.format("SELECT * FROM eventstable WHERE month = '%s' and year = '%s'", month, year);
        return getReadableDatabase().rawQuery("SELECT * FROM eventstable;", null);
    }

}
