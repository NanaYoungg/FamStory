package com.hongsam.famstory.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
    private SpinnerMangerBinding mBinding;
    protected MainActivity mainActivity;
    private Button addItem,deleteItem;
    private ListView spinnerList;
    private View root;
    private CalendarFirebaseDB firebaseDB = new CalendarFirebaseDB();
    CalendarFirebaseDB.SpinnerMangerDB mangerDB= firebaseDB.new SpinnerMangerDB();
    CalendarFirebaseDB.SpinnerDB spinnerDB = firebaseDB.new SpinnerDB(getContext());

    View mContentView;

    public SpinnerMangerFragment(ArrayAdapter<String> adapter, Spinner spinner) {
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
        if (container == null){
            return null;
        }
        mBinding = SpinnerMangerBinding.inflate(getLayoutInflater());
        mContentView = inflater.inflate(R.layout.spinner_manger,container,false);
        root = mBinding.getRoot();
        init();
        ArrayList<String> list =new ArrayList<>();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                list
        );
        mangerDB.getDBShowListView(adapter);
        spinnerList.setAdapter(adapter);

        addItem.setOnClickListener(new View.OnClickListener() {
            final EditText editText = new EditText(getContext());

            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final AlertDialog dialog = builder.create();
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
                dialog.show();
            }
        });
        return root;
    }

    private void init() {
        addItem = mBinding.addItem;
        deleteItem = mBinding.deleteItem;
        spinnerList = mBinding.spinnerList;

    }
}
