package com.example.crime_intent.controller.fragments;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.crime_intent.R;
import com.example.crime_intent.model.Crime;
import com.example.crime_intent.repository.CrimeDBRepository;
import com.example.crime_intent.repository.CrimeRepository;
import com.example.crime_intent.repository.IRepository;

import java.text.SimpleDateFormat;
import java.util.UUID;


public class Crime_DetailFragment extends Fragment {

    private EditText mTextView_title,mTextView_desc;
    private Button mButton_date;
    private Button mButtonCall;
    private Button mButtonDial;
    private CheckBox mCheckBox;
    private Crime mCrime;
    private IRepository mCrimeRepository;
    public static final String ARGS_CRIME_ID="com.example.crimeintent.crimeId";
    private static final int REQUEST_SELECT_PHONE_NUMBER = 2;

    public static Crime_DetailFragment newInstance(UUID id) {

        Bundle args = new Bundle();
        args.putSerializable(ARGS_CRIME_ID,id);
        Crime_DetailFragment fragment = new Crime_DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public Crime_DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onPause() {
        super.onPause();
        updateCrime();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrimeRepository= CrimeDBRepository.getInstance(getActivity());
        mCrime= mCrimeRepository.get((UUID) getArguments().getSerializable(ARGS_CRIME_ID));
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.remove_crime_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_menuItem:
                CrimeRepository.getInstance().delete(mCrime);
                getActivity().finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_crime_detail, container, false);
        findViews(view);
        setListeners();
        initView();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        if (resultCode != Activity.RESULT_OK || intent == null)
            return;

        else if (requestCode == REQUEST_SELECT_PHONE_NUMBER && resultCode == Activity.RESULT_OK) {
            Uri contactUri = intent.getData();
            String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
            Cursor cursor = getActivity().getContentResolver().query(contactUri, projection,
                    null, null, null);
            // If the cursor returned is valid, get the phone number
            if (cursor == null || cursor.getCount() == 0)
                return;
            try {
                cursor.moveToFirst();
                int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(numberIndex);
                mCrime.setSuspectPhoneNumber(number);
                mButtonCall.setText("call " + mCrime.getSuspectPhoneNumber());
                mButtonDial.setText("dial" + mCrime.getSuspectPhoneNumber());
            } finally {
                cursor.close();
            }
        }
    }
        private void shareReportIntent() {
        /*Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, getReport());
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crime_report_subject));
        sendIntent.setType("text/plain");

        Intent shareIntent =
                Intent.createChooser(sendIntent, getString(R.string.send_report));

        //we prevent app from crash if the intent has no destination.
        if (sendIntent.resolveActivity(getActivity().getPackageManager()) != null)
            startActivity(shareIntent);*/
            ShareCompat.IntentBuilder intentBuilder = ShareCompat.IntentBuilder.from(getActivity());
            Intent intent = intentBuilder
                    .setText(getReport())
                    .setChooserTitle(getString(R.string.crime_report_subject))
                    .setType("text/plain")
                    .createChooserIntent();
            if (intent.resolveActivity(getActivity().getPackageManager()) != null)
                startActivity(intent);
        }

        private String getReport() {
            String title = mCrime.getTitle();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd-HH:mm:SS");
            String dateString = simpleDateFormat.format(mCrime.getDate());

            String solvedString = mCrime.isSolved() ?
                    getString(R.string.crime_report_solved) :
                    getString(R.string.crime_report_unsolved);

            String suspectString = mCrime.getSuspect() == null ?
                    getString(R.string.crime_report_no_suspect) :
                    getString(R.string.crime_report_suspect, mCrime.getSuspect());

            String report = getString(
                    R.string.crime_report,
                    title,
                    dateString,
                    solvedString,
                    suspectString);

            return report;
        }


    private void findViews(View view){
        mTextView_title=view.findViewById(R.id.crime_title);
        mTextView_desc=view.findViewById(R.id.crime_decs);
        mButton_date=view.findViewById(R.id.crime_date);
        mCheckBox=view.findViewById(R.id.crime_solved);
        mButtonCall = view.findViewById(R.id.btn_call);
        mButtonDial = view.findViewById(R.id.btn_dial);
    }
    private void setListeners(){
        mTextView_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mCrime.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mTextView_desc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mCrime.setDescription(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mCrime.setSolved(b);
            }
        });

        mButtonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPhoneNumber();
            }
        });
        mButtonDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialPhoneNumber();
            }
        });
    }
    private void initView(){
        mTextView_title.setText(mCrime.getTitle());
        mTextView_desc.setText(mCrime.getDescription());
        mButton_date.setText(mCrime.getDate().toString());
        mCheckBox.setChecked(mCrime.isSolved());
    }
    private void updateCrime(){
        CrimeRepository.getInstance().update(mCrime);
    }

    private void callPhoneNumber() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+mCrime.getSuspectPhoneNumber()));
        startActivity(callIntent);
    }

    public void dialPhoneNumber() {
        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
        dialIntent.setData(Uri.parse("tel:" + mCrime.getSuspectPhoneNumber()));
        startActivity(dialIntent);
    }
}