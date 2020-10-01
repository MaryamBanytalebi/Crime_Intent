package com.example.crime_intent.controller.activity;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;

import com.example.crime_intent.controller.fragments.Crime_listFragment;

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