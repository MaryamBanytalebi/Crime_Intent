package com.example.crime_intent.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.crime_intent.R;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_fragment_activity);
        FragmentManager fragmentManager=getSupportFragmentManager();
        Fragment fragment=fragmentManager.findFragmentById(R.id.container_fragment_activity);
        if (fragment==null){
            fragmentManager
                    .beginTransaction()
                    .add(R.id.container_fragment_activity,createFragment())
                    .commit();
        }
    }
}