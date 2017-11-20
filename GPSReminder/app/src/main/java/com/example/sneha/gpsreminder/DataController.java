package com.example.sneha.gpsreminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DataController {

    public static final String TASKNAME = "taskName";
    public static final String TASKDESCRIPTION = "taskDescription";
    public static final String TASKLOCATION = "taskLocation";
    public static final String TASKLONGITUTE = "taskLong";
    public static final String TASKLATITUDE = "taskLat";
    public static final String TABLENAME = "Task_Table";
    public static final String DATABASENAME = "GPSREMINDER";
    public static final int DATABASE_VERSION = 5;
    public static final String TABLE_CREATE="create table Task_Table (taskName text not null,taskDescription text not null, taskLocation text not null,taskLong double not null,taskLat double not null ); ";

    DataBaseHelper dbHelper;
    Context context;
    SQLiteDatabase db;

    public  DataController(Context ctx)
    {
        this.context = ctx;
        dbHelper = new DataBaseHelper(context);
    }
    public DataController open()
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public long insert(String taskName, String taskDescription, String taskLocation, double taskLong, double taskLat)
    {
        ContentValues content = new ContentValues();
        content.put(TASKNAME,taskName);
        content.put(TASKDESCRIPTION,taskDescription);
        content.put(TASKLOCATION,taskLocation);
        content.put(TASKLATITUDE,taskLong);
        content.put(TASKLONGITUTE,taskLat);
        return db.insertOrThrow(TABLENAME,null,content);
    }

    public long delete(String taskName)
    {
        return db.delete(TABLENAME,TASKNAME + "='"+taskName+"'",null);
    }

    public Cursor retrieve()
    {
        return db.query(TABLENAME, new String[]{TASKNAME,TASKDESCRIPTION,TASKLOCATION,TASKLATITUDE,TASKLONGITUTE },null,null,null,null,null);
    }

    private static class DataBaseHelper extends SQLiteOpenHelper
    {

        public DataBaseHelper(Context context)
        {
            super(context, DATABASENAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db){

            try
            {
                db.execSQL(TABLE_CREATE);
            }
            catch (SQLiteException e)
            {
                e.printStackTrace();
            }

        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

            db.execSQL("DROP TABLE IF EXISTS Task_Table ");
            onCreate(db);
        }
    }




}
