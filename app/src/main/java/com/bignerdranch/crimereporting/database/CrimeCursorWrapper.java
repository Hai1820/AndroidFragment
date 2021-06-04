package com.bignerdranch.crimereporting.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.bignerdranch.crimereporting.Crime;

import java.util.Date;
import java.util.UUID;

public class CrimeCursorWrapper extends CursorWrapper {

    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public Crime getCrime(){
        String uuidString = getString(getColumnIndex(CrimeDbCrime.CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeDbCrime.CrimeTable.Cols.TITLE));
        Long date = getLong(getColumnIndex(CrimeDbCrime.CrimeTable.Cols.DATE));
        boolean isSolved = (getInt(getColumnIndex(CrimeDbCrime.CrimeTable.Cols.SOLVED))!=0);
        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setmTitle(title);
        crime.setmDate(new Date(date));
        crime.setmSolved(isSolved);

        return crime;

    }
}
