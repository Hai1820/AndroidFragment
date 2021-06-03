package com.bignerdranch.crimereporting;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    public static CrimeLab sCrimeLab;
    List<Crime> mCrimes;
    public CrimeLab(Context context) {
        mCrimes = new ArrayList<Crime>();
        for (int i = 0 ;i <100; i++){
            Crime c =   new Crime();
            c.setmTitle("Crime #" +i);
            c.setmSolved(i%2==0);
            mCrimes.add(c);
        }
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
        return mCrimes;
    }

    public void setmCrimes(List<Crime> mCrimes) {
        this.mCrimes = mCrimes;
    }
    public Crime getCrime(UUID mID){
        for (Crime crime:mCrimes){
            if (crime.getmId().equals(mID))
                return crime;
        }
        return null;

    }
}
