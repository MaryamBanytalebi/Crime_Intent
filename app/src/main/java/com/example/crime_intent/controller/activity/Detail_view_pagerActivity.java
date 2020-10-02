package com.example.crime_intent.controller.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.crime_intent.R;
import com.example.crime_intent.controller.fragments.Crime_DetailFragment;
import com.example.crime_intent.model.Crime;
import com.example.crime_intent.repository.CrimeDBRepository;
import com.example.crime_intent.repository.IRepository;

import java.util.List;
import java.util.UUID;

public class Detail_view_pagerActivity extends AppCompatActivity {

    public static final String EXTRA_CRIMEID="com.example.crimeintent.crimeId";
    private ViewPager2 mViewPager2;
    private UUID mCrimeId;
    private IRepository mCrimeRepository;
    public static Intent newIntent(Context context, UUID id){
        Intent intent=new Intent(context,Detail_view_pagerActivity.class);
        intent.putExtra(EXTRA_CRIMEID,id);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view_pager);
        mCrimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIMEID);
        mCrimeRepository= CrimeDBRepository.getInstance(this);
        findViews();
        initView();
    }
    private void findViews(){
        mViewPager2=findViewById(R.id.view_pager_detail);
    }
    private void initView(){
        List<Crime> crimes=mCrimeRepository.getList();
        mViewPager2.setAdapter(new ViewPagerAdapter(this,crimes));
        mViewPager2.setCurrentItem(mCrimeRepository.getPosition(mCrimeId));
    }
    private class ViewPagerAdapter extends FragmentStateAdapter {

        private List<Crime>mCrimes;

        public List<Crime> getCrimes() {
            return mCrimes;
        }

        public void setCrimes(List<Crime> crimes) {
            mCrimes = crimes;
        }

        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Crime> crimes) {
            super(fragmentActivity);
            mCrimes = crimes;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {

            return  Crime_DetailFragment.newInstance(mCrimes.get(position).getUUID());
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}