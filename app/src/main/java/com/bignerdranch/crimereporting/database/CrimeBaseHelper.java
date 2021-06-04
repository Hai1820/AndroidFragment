package com.bignerdranch.crimereporting.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class CrimeBaseHelper  extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME ="crimeBase.db";
    public CrimeBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query ="CREATE TABLE " +CrimeDbCrime.CrimeTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                CrimeDbCrime.CrimeTable.Cols.UUID + " , " +
                CrimeDbCrime.CrimeTable.Cols.TITLE + "," +
                CrimeDbCrime.CrimeTable.Cols.DATE + "," +
                CrimeDbCrime.CrimeTable.Cols.SOLVED +
                ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
