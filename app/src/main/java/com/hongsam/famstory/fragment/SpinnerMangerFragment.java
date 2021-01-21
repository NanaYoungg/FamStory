package com.hongsam.famstory.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
 
import android.widget.Button;
import android.widget.ListView;

import android.widget.EditText;
import android.widget.Spinner;
 

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hongsam.famstory.R;
import com.hongsam.famstory.activitie.MainActivity;
import com.hongsam.famstory.databinding.SpinnerMangerBinding;
import com.hongsam.famstory.firebase.CalendarFirebaseDB;

import java.util.ArrayList;

public class SpinnerMangerFragment extends Fragment {
 
    protected MainActivity mainActivity;
    private Button addItem,deleteItem;
    private ListView spinnerList;
    private View root;
    private CalendarFirebaseDB firebaseDB = new CalendarFirebaseDB();
    CalendarFirebaseDB.SpinnerMangerDB mangerDB= firebaseDB.new SpinnerMangerDB();
    View mContentView;


    private SpinnerMangerBinding mb;

    Spinner spinner;
    ArrayAdapter<String> adapter;
    CalendarFirebaseDB.SpinnerDB spinnerDB = firebaseDB.new SpinnerDB(getContext());


    public SpinnerMangerFragment(ArrayAdapter<String> adapter, Spinner spinner) {
        this.spinner = spinner;
        this.adapter =adapter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
        mainActivity = (MainActivity)getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(container==null){
            return null;
        }



        mb.spinnerList.setAdapter(adapter);

        mb.addItem.setOnClickListener(new View.OnClickListener() {
            final EditText editText = new EditText(getContext());

            //CustomAlertDialog alertDialog = new CustomAlertDialog(getContext());


            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                editText.setHint("아이템을 입력해주세요");

                builder.setTitle("추가하실 아이템을 입력하세요");
                builder.setView(editText);
                builder.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        adapter.add(editText.getText().toString());

                        mangerDB.getDBShowListView(adapter);
                        //dialog.dismiss();
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
        return root;
    }


}
