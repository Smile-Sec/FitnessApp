package com.example.fitnessapp;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SQLdb extends SQLiteOpenHelper {
    protected final static String DATABASE_NAME = "fitnesslocaldb";
    protected final static int VERSION_NUM = 1;
    public final static String TABLE_NAME = "LabFive";
    public final static String COL_BOOL = "BOOL";
    public final static String COL_NAME = "NAME";
    public final static String COL_ID = "_id";

    public SQLdb(Context contxt) {
        super(contxt, DATABASE_NAME, null, VERSION_NUM);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME + " text,"
                + COL_BOOL  + " INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }
}