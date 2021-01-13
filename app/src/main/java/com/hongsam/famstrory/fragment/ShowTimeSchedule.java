package com.hongsam.famstrory.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hongsam.famstrory.R;
import com.hongsam.famstrory.activitie.MainActivity;

public class ShowTimeSchedule extends Fragment {
    MainActivity mainActivity;
    View mContentView;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity)getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container == null){
            return  null;
        }
        if (inflater == null){
            return null;
        }
        mContentView = inflater.inflate(R.layout.show_time_schedule,container,false);
        init(mContentView);


        return mContentView;
    }

    public void init(View view){
        if (view!=null){

        }
    }
}
