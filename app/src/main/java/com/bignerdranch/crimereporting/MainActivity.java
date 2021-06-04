package com.bignerdranch.crimereporting;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.UUID;

public class MainActivity extends  SingleFragmentActivity  {
    public static final String EXTRA_CRIME_ID = "com.bignerdranch.crimereporting.crime_id";
    public static Intent newIntent(Context packageContext, UUID crimeID){
        Intent intent = new Intent(packageContext, MainActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeID);
        return intent;
    }
    @Override
    public Fragment createFragment() {
//        return new CrimeFragment();
       UUID crimeID = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
       return CrimeFragment.newIntance(crimeID);
    }


}