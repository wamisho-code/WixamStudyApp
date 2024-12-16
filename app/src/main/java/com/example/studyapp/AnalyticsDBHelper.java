package com.example.studyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AnalyticsDBHelper extends SQLiteOpenHelper {
    private static final String db_name ="Analytics.db";
    private static final String TABEL_NAME="analytics_table";
    private  final String COLUMN_1="id";
    private  final String COLUMN_2="SUBJECT";
    private  final String COLUMN_3="CORRECTLY_ANSWERED";
    private  final String COLUMN_4="SKIPPED";
    private  final String COLUMN_5="WRONGLY_ANSWERED";
   // private  final String COLUMN_6="UNIT";


    public AnalyticsDBHelper(@Nullable Context context) {
        super(context, db_name, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABEL_NAME
                +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,SUBJECT TEXT,CORRECTLY_ANSWERED INTEGER" +
                ",SKIPPED INTEGER,WRONGLY_ANSWERED INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABEL_NAME);
        onCreate(db);

    }
    public  boolean insertData(String subject,int correct,int wrong,int skip){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COLUMN_2,subject);
        contentValues.put(COLUMN_3,correct);
        contentValues.put(COLUMN_4,skip);
        contentValues.put(COLUMN_5,wrong);
        long result =db.insert(TABEL_NAME,null,contentValues);
        if(result==-1){
            return false;
        }else {
            return  true;
        }}
    public Cursor getAllData(String subject){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res=db.rawQuery("select CORRECTLY_ANSWERED,SKIPPED,WRONGLY_ANSWERED  from "+
                TABEL_NAME+" where SUBJECT = ?",new String[]{subject});
        return res;
    }



}
