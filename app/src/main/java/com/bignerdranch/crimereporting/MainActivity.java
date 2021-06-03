package com.bignerdranch.crimereporting;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends  SingleFragmentActivity  {

    @Override
    public Fragment createFragment() {
        return new CrimeFragment();
    }


}