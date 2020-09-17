package com.example.crime_intent.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.crime_intent.R;
import com.example.crime_intent.controller.fragments.Crime_listFragment;

public class CrimeListActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context){
        Intent intent=new Intent(context,CrimeListActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {


        return  Crime_listFragment.newInstance();
    }
}