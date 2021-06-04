package com.bignerdranch.crimereporting;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CrimeListFragment extends Fragment {
    private RecyclerView mCrimeRecyclerView;
    private boolean mSubtitleVisible;
    private final static String SUBTITLE_VISIBLE ="subtitle";
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private Crime mCrime;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent){

            super(inflater.inflate(R.layout.list_item_crime, parent, false));
            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
            itemView.setOnClickListener(this);
        }
        public void bind(Crime crime){
            mCrime = crime;
            mTitleTextView.setText(mCrime.getmTitle());
            mDateTextView.setText(mCrime.getmDate().toString());
        }

        @Override
        public void onClick(View v) {

//            Toast.makeText(getActivity(), mCrime.getmTitle(), Toast.LENGTH_LONG).show();
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getmId());
            startActivity(intent);

        }
    }
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> mCrime) {
            this.mCrimes = mCrime;
        }

        @NonNull
        @Override
        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater   = LayoutInflater.from(getActivity());
            return new CrimeHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }

        public void setmCrimes(List<Crime> mCrimes) {
            mCrimes = mCrimes;
        }
    }
    private CrimeAdapter crimeAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        super.onCreateView(inflater, container, savedInstanceState);
         View v =  inflater.inflate(R.layout.fragment_list_crime, container, false);
         mCrimeRecyclerView = (RecyclerView) v.findViewById(R.id.crime_recycler_view);
         mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
         if (savedInstanceState != null){
             mSubtitleVisible = savedInstanceState.getBoolean(SUBTITLE_VISIBLE);
         }
         updateSubTitle();
        setHasOptionsMenu(true);

        updateUI();
         return v;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);
        MenuItem item = menu.findItem(R.id.show_subtitle);
        if(mSubtitleVisible){
            item.setTitle(R.string.hide_subtitle);
        }else {
            item.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
       switch ( item.getItemId()){
           case R.id.new_crime:
               Crime crime = new Crime();
               CrimeLab.get(getActivity()).addCrime(crime);
               Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getmId());
               startActivity(intent);
               return true;
           case R.id.show_subtitle:
               mSubtitleVisible = !mSubtitleVisible;
               getActivity().invalidateOptionsMenu();
               updateSubTitle();
               return true;
           default:
               return super.onOptionsItemSelected(item);
       }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    private void updateSubTitle() {
        CrimeLab lab= CrimeLab.get(getActivity());
        int count = lab.getmCrimes().size();
        System.out.println(count);
        String subtitle = getString(R.string.subtitle_format, count);
        if (mSubtitleVisible == false){
            subtitle = null;
        }
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.getSupportActionBar().setSubtitle(subtitle);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
         List<Crime> crimes = crimeLab.getmCrimes();
         if(crimeAdapter == null){
             crimeAdapter = new CrimeAdapter(crimes);
             mCrimeRecyclerView.setAdapter(crimeAdapter);
         }
         else {
                crimeAdapter.setmCrimes(crimes);
             crimeAdapter.notifyDataSetChanged();
         }
    }
}
