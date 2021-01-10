package com.hongsam.famstrory.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hongsam.famstrory.R;
import com.hongsam.famstrory.activitie.MainActivity;

import org.w3c.dom.Text;

public class CalendarViewMoreFragment extends Fragment {
    MainActivity mainActivity;
    View convertView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        convertView = inflater.inflate(R.layout.calendar_view_more_layout,container,false);
        init(convertView);
        return convertView;
    }
    public void init(View v){
        if(v!=null){
            // id

        }
    }
}

