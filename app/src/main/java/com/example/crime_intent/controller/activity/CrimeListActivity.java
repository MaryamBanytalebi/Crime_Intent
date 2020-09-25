package com.example.crime_intent.controller.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.crime_intent.R;
import com.example.crime_intent.controller.fragments.Crime_listFragment;
import com.example.crime_intent.controller.fragments.Detail_view_pagerFragment;
import com.example.crime_intent.model.Crime;
import com.example.crime_intent.repository.CrimeRepository;

public class CrimeListActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context,String username){
        Intent intent=new Intent(context,CrimeListActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {


        return  Crime_listFragment.newInstance();
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater infalater = getMenuInflater();
        infalater.inflate(R.menu.menu_crime_list,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_add_crime:
                Crime crime = new Crime();
                CrimeRepository.getInstance().insert(crime);
                Intent intent = Detail_view_pagerActivity.newIntent(this,crime.getUUID());
                startActivity(intent);
                return true;
            default:

                return super.onOptionsItemSelected(item);


        }
    }*/
}