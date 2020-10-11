package com.example.crime_intent.controller.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.crime_intent.R;
import com.example.crime_intent.controller.SwipeRecyclerView;
import com.example.crime_intent.controller.activity.Detail_view_pagerActivity;
import com.example.crime_intent.model.Crime;
import com.example.crime_intent.repository.CrimeDBRepository;
import com.example.crime_intent.repository.IRepository;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Crime_listFragment extends Fragment {

    public static final String BUNDLE_ARG_SUB_TITLE_VISIBLE = "sub title visible";
    private Button mButton_add;
    private RecyclerView mRecyclerView;
    private IRepository mRepository;
    private List<UUID> deleteitems;
    private List<Crime> mCrimes;
    private CrimeAdapter mAdapter;
    private FrameLayout mFrameLayout;
    private boolean IsSubTitleVisible = false;

    private Callbacks mCallbacks;
    private Crime mCrimeUndo;

    public Crime_listFragment() {
        // Required empty public constructor
    }

    public static Crime_listFragment newInstance() {

        Bundle args = new Bundle();

        Crime_listFragment fragment = new Crime_listFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository= CrimeDBRepository.getInstance(getActivity());
        deleteitems=new ArrayList<>();
        setHasOptionsMenu(true);

        if (savedInstanceState != null) {
            setMenuItemSubTitle(savedInstanceState);
        }

    }

    private void setMenuItemSubTitle(Bundle savedInstanceState) {
        IsSubTitleVisible = savedInstanceState.getBoolean(BUNDLE_ARG_SUB_TITLE_VISIBLE,false);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateSubTitle();
        updateView();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Callbacks)
            mCallbacks = (Callbacks) context;
        else {
            throw new ClassCastException(context.toString()
                    + " must implement Callbacks");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(BUNDLE_ARG_SUB_TITLE_VISIBLE,IsSubTitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.remove_crime_menu,menu);
        inflater.inflate(R.menu.menu_crime_list,menu);
        MenuItem item = menu.findItem(R.id.menu_item_subtitle);
        setItemSubTitle(item);


    }

    private void setItemSubTitle(MenuItem item) {
        item.setTitle(IsSubTitleVisible ? R.string.hide_subtitle : R.string.show_subtitle);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_menuItem:
                for (int i = 0; i <mCrimes.size() ; i++) {
                    if (mCrimes.get(i).isCheckSelect()) {
                        mRepository.delete(mCrimes.get(i));
                        i-=1;
                    }
                }
                updateView();
                return true;

            case R.id.menu_item_subtitle:
                IsSubTitleVisible =! IsSubTitleVisible;
                updateSubTitle();
                setItemSubTitle(item);
                return true;

            case R.id.menu_item_add_crime:
                Crime crime = new Crime();
                mRepository.insert(crime);
                mCallbacks.onCrimeSelected(crime);
                Intent intent = Detail_view_pagerActivity.newIntent(getActivity(),crime.getUUID());
                startActivity(intent);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void updateSubTitle() {
        int numberOfCrimes = mRepository.getList().size();
        String crimeText = IsSubTitleVisible ? numberOfCrimes + "crimes" : null ;
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(crimeText);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_crime_list, container, false);
        findViews(view);
        setListenes();
        initView();
        return view;
    }
    private void findViews(View view){
        mRecyclerView=view.findViewById(R.id.recycler_crime_list);
        mButton_add=view.findViewById(R.id.add_btn);
        mFrameLayout=view.findViewById(R.id.empty_framLayout);
    }
    private void setListenes(){
        mButton_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Crime crime=new Crime();
                mRepository.insert(crime);
                mCallbacks.onCrimeSelected(crime);
                Intent intent= Detail_view_pagerActivity.newIntent(getActivity(),crime.getUUID());
                startActivity(intent);


            }
        });
    }
    private void initView(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipeRecycler();
        updateView();
    }

    private void swipeRecycler() {
        /*  set swipe touch listener */
        SwipeRecyclerView swipeTouchListener = new
                SwipeRecyclerView(mRecyclerView,
                new SwipeRecyclerView.SwipeListener() {

                    @Override
                    public boolean canSwipeRight(int position) {
                        //enable/disable right swipe on checkbox base else use true/false
                        return true;
                    }

                    @Override
                    public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                        //on recycler view swipe right dismiss update adapter
                        onRecyclerViewDismiss(reverseSortedPositions, mCrimes);
                    }
                });

        //add item touch listener to recycler view
        mRecyclerView.addOnItemTouchListener(swipeTouchListener);
    }

    private void onRecyclerViewDismiss(int[] reverseSortedPositions, List<Crime> crimes) {
        for (int position : reverseSortedPositions) {
            mCrimeUndo = crimes.get(position);
            mRepository.delete(crimes.get(position));
        }
        updateView();
        showSnackBar();
    }

    private void showSnackBar() {
        Snackbar snackbar = Snackbar.make(mFrameLayout, R.string.crime_dismiss_success, Snackbar.LENGTH_SHORT);
        snackbar.setAction(R.string.crime_dismiss_undo,new MyUndoListener());
        snackbar.show();
    }


    private class CrimeHolder extends RecyclerView.ViewHolder{
        TextView mTextView_title;
        TextView mTextView_desc;
        ImageView mImageView_pic;
        CheckBox mCheckBox;
        Crime mCrime;
        public CrimeHolder(@NonNull final View itemView) {
            super(itemView);
            mTextView_title=itemView.findViewById(R.id.item_title);
            mTextView_desc=itemView.findViewById(R.id.item_desc);
            mImageView_pic=itemView.findViewById(R.id.item_pic);
            mCheckBox=itemView.findViewById(R.id.checkbox_selected);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*Intent intent= Detail_view_pagerActivity.newIntent(getActivity(),mCrime.getUUID());
                    startActivity(intent);*/
                    mCallbacks.onCrimeSelected(mCrime);
                }
            });
            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (mCheckBox.isChecked()){
                        deleteitems.add(mCrime.getUUID());
                    }
                    else {
                        deleteitems.remove(mCrime.getUUID());
                    }
                }
            });
        }
        public void bindCrime(Crime crime){
            mCrime=crime;
            mTextView_title.setText(mCrime.getTitle());
            mTextView_desc.setText(mCrime.getDescription());
            //mImageView_pic.setImageResource(R.drawable.ic_handcuffs);
            mImageView_pic.setVisibility(mCrime.isSolved()?View.VISIBLE:View.INVISIBLE);
        }

    }
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{

        private List<Crime>mCrimes;

        public List<Crime> getCrimes() {
            return mCrimes;
        }

        public void setCrimes(List<Crime> crimes) {
            mCrimes = crimes;
        }

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @NonNull
        @Override
        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(getActivity()).inflate(R.layout.fragment_crime_item,parent,false);
            CrimeHolder crimeHolder=new CrimeHolder(view);
            return crimeHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull CrimeHolder holder, int position) {
            holder.bindCrime(mCrimes.get(position));
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
    private void updateView(){

        if (mRepository.getList().size()==0){
            mFrameLayout.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
        else {
            mFrameLayout.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            List<Crime> crimes = mRepository.getList();

            if ( mAdapter== null) {
                mAdapter = new CrimeAdapter(crimes);
                mRecyclerView.setAdapter(mAdapter);
            } else {
                mAdapter.notifyDataSetChanged();
            }
        }


    }

    public interface Callbacks {
        void onCrimeSelected(Crime crime);
    }

    public class MyUndoListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mRepository.insert(mCrimeUndo);
            updateView();
        }
    }
}