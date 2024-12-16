package com.example.studyapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DatabaseHelper {
    private static  String DATABASE_NAME ;
    private static final String DATABASE_PATH = "/data/data/com.example.studyapp/databases/";
    private final Context context;

    public DatabaseHelper(Context context,String DATABASE_NAME) {
        this.context = context;
        this.DATABASE_NAME = DATABASE_NAME+".db";
        System.out.println("DBNAME : "+this.DATABASE_NAME);
    }

    public SQLiteDatabase openDatabase() {
        try {
            InputStream input = context.getAssets().open(DATABASE_NAME);
            String outFileName = DATABASE_PATH + DATABASE_NAME;
            OutputStream output = new FileOutputStream(outFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            output.flush();
            output.close();
            input.close();

            return SQLiteDatabase.openDatabase(outFileName, null, SQLiteDatabase.OPEN_READONLY);
        } catch (IOException e) {
            throw new RuntimeException("Error opening database", e);
        }
    }
    public Cursor[] getAllData(ArrayList<String> content,int noQuestion){
        SQLiteDatabase db = this.openDatabase();

        int eachQuestionNumber=(noQuestion/content.size());

        Cursor[] res =new Cursor[content.size()];
        int i=0;
        for (String topic : content){

            res[i]=db.rawQuery("select * from exam_questions"+" where Unit =? COLLATE NOCASE LIMIT ? ",new String[] {topic,String.valueOf(eachQuestionNumber)});
            i++;
        }
        for(Cursor c :res){
            System.out.println(" - "+res);
        }

//       // Cursor cursor = db.rawQuery("SELECT * FROM exam_questions", null);
//        Cursor res=db.rawQuery("select * from exam_questions",null);
        return res;
    }



}