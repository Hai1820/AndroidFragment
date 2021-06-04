package com.bignerdranch.crimereporting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import com.bignerdranch.crimereporting.database.CrimeBaseHelper;
import com.bignerdranch.crimereporting.database.CrimeCursorWrapper;
import com.bignerdranch.crimereporting.database.CrimeDbCrime;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    public static CrimeLab sCrimeLab;
    private SQLiteDatabase mDatebase;
//    List<Crime> mCrimes;


    public CrimeLab(Context context) {
        mDatebase = new CrimeBaseHelper(context.getApplicationContext()).getWritableDatabase();
//        mCrimes = new ArrayList<Crime>();
//        for (int i = 0 ;i <100; i++){
//            Crime c =   new Crime();
//            c.setmTitle("Crime #" +i);
//            c.setmSolved(i%2==0);
//            mCrimes.add(c);
//        }
    }

    public static CrimeLab get(Context context) {
        if(sCrimeLab ==null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    public static void setsCrimeLab(CrimeLab sCrimeLab) {
        CrimeLab.sCrimeLab = sCrimeLab;
    }

    public List<Crime> getmCrimes() {
//        return mCrimes;
//        return new ArrayList<>();
        List<Crime> crimes = new ArrayList<>();
        CrimeCursorWrapper cursorWrapper = queryCrime(null, null);
        try {
            cursorWrapper.moveToFirst();
            while(!cursorWrapper.isAfterLast()){
                crimes.add(cursorWrapper.getCrime());
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }

        return crimes;
    }


    public Crime getCrime(UUID mID){
        String whereClause = CrimeDbCrime.CrimeTable.Cols.UUID + "= ?";
        String[] whereArgs = new String[]{mID.toString()};
        CrimeCursorWrapper cursorWrapper = queryCrime(whereClause, whereArgs);
        try {
            if(cursorWrapper.getCount()==0)
                return null;
            cursorWrapper.moveToFirst();
           return cursorWrapper.getCrime();
        } finally {
            cursorWrapper.close();
        }
    }
    public void addCrime(Crime c) {
//        mCrimes.add(c);
        ContentValues values = getContenValues(c);
        mDatebase.insert(CrimeDbCrime.CrimeTable.NAME, null, values);
    }
    public void updateCrime(Crime crime){
        String uuidString = crime.getmId().toString();
        ContentValues values =getContenValues(crime);
        mDatebase.update(CrimeDbCrime.CrimeTable.NAME, values,
                CrimeDbCrime.CrimeTable.Cols.UUID + "= ?", new String[]{uuidString} );
    }
    public CrimeCursorWrapper queryCrime(String whereClause, String[] whereArgs){
        Cursor cursor = mDatebase.query(CrimeDbCrime.CrimeTable.NAME,
                null,whereClause, whereArgs, null, null, null);

        return new CrimeCursorWrapper(cursor);

    }
    private static ContentValues getContenValues(Crime crime){
        ContentValues values = new ContentValues();
        values.put(CrimeDbCrime.CrimeTable.Cols.UUID, crime.getmId().toString());
        values.put(CrimeDbCrime.CrimeTable.Cols.DATE, crime.getmDate().getTime());
        values.put(CrimeDbCrime.CrimeTable.Cols.SOLVED, crime.ismSolved()?1:0);
        values.put(CrimeDbCrime.CrimeTable.Cols.TITLE, crime.getmTitle());

        return values;
    }
}
