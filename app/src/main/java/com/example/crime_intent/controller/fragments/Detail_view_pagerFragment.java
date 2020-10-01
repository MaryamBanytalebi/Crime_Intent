package com.example.crime_intent.controller.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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

import java.util.UUID;


public class Detail_view_pagerFragment extends Fragment {

    private EditText mTextView_title,mTextView_desc;
    private Button mButton_date;
    private CheckBox mCheckBox;
    private Crime mCrime;
    private IRepository mCrimeRepository;
    public static final String ARGS_CRIME_ID="com.example.crimeintent.crimeId";
    public static Detail_view_pagerFragment newInstance(UUID id) {

        Bundle args = new Bundle();
        args.putSerializable(ARGS_CRIME_ID,id);
        Detail_view_pagerFragment fragment = new Detail_view_pagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public Detail_view_pagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onPause() {
        super.onPause();
        //updateCrime();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrimeRepository= CrimeDBRepository.getInstance(getActivity());
        mCrime= (Crime) mCrimeRepository.get((UUID) getArguments().getSerializable(ARGS_CRIME_ID));
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

        View view= inflater.inflate(R.layout.fragment_detail_view_pager, container, false);
        findViews(view);
        setListeners();
        initView();
        return view;
    }
    private void findViews(View view){
        mTextView_title=view.findViewById(R.id.crime_title);
        mTextView_desc=view.findViewById(R.id.crime_decs);
        mButton_date=view.findViewById(R.id.crime_date);
        mCheckBox=view.findViewById(R.id.crime_solved);
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
}