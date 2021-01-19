package com.hongsam.famstrory.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.hongsam.famstrory.R;
import com.hongsam.famstrory.activitie.MainActivity;
import com.hongsam.famstrory.define.Define;
import com.hongsam.famstrory.dialog.CalendarCustomDialog;
import com.hongsam.famstrory.firebase.CalendarDB;
import com.hongsam.famstrory.firebase.DeleteDB;
import com.hongsam.famstrory.firebase.ReadDB;
import com.hongsam.famstrory.firebase.CheckDB;
import com.hongsam.famstrory.firebase.UpdateDB;
import com.hongsam.famstrory.interf.CallbackInterface;

/*
    Calendar 프레그먼트
*/
public class CalendarFragment extends Fragment implements CallbackInterface {

    MainActivity mainActivity;
    View mContentView;
    CalendarView calendarView;
    TextView vm_name,vm_text,vm_date,vm_time;
    ConstraintLayout view_more;
    String date;
    String getDate;
    String getUpdateEtTitle,getUpdateEtDescription,getUpdateEtStartTime,getUpdateEtEndTime;
    public static Button cal_update_btn, cal_delete_btn, cal_create_btn;
    DeleteDB deleteDB = new DeleteDB();
    FragmentTransaction ft_view_more;
    CalendarViewMoreFragment viewMoreFragment;
    CheckDB checkDB = new CheckDB();
    UpdateDB updateDB = new UpdateDB(mainActivity);
    public static String state = "null";


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
        mainActivity.setCi(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container == null)
            return null;

        if (inflater == null)
            return null;

        mContentView = inflater.inflate(R.layout.fragment_calendar, container,false);

        init(mContentView);
        ft_view_more = getFragmentManager().beginTransaction();
        viewMoreFragment = new CalendarViewMoreFragment();



        return mContentView;
    }

    @Override
    public void isDateNull(String date) {
        getDate = date;
    }

    @Override
    public void onPause() {
        super.onPause();

    }



    @Override
    public void onResume() {
        super.onResume();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                state = "null";
                vm_text.setText("");
                vm_time.setText("");

                month+=1;
                date = year+"년"+month+"월"+dayOfMonth+"일";


                ReadDB readDB = new ReadDB(mainActivity);

                readDB.databaseRead(date);
                getFragmentManager().beginTransaction().replace(R.id.calendar_view_more,viewMoreFragment).commit();
                FragmentManager newFm = getFragmentManager();
                checkDB.checkDB(date,getContext(),newFm,viewMoreFragment);
                Define.setViewText(vm_date,year+"."+month+"."+dayOfMonth);
                if (state.equals("null")){
                    cal_create_btn.setVisibility(View.VISIBLE);
                    cal_delete_btn.setVisibility(View.INVISIBLE);
                    cal_update_btn.setVisibility(View.INVISIBLE);
                }



            }
        });

        cal_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"update dialog",Toast.LENGTH_SHORT).show();
                //updateDB.updateDB(date);
                CalendarCustomDialog dialog = new CalendarCustomDialog(mainActivity, date,Define.UPDATE_DIALOG);
                Toast.makeText(getContext(),"update",Toast.LENGTH_SHORT).show();
                dialog.show();

            }
        });

        cal_create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarCustomDialog dialog = new CalendarCustomDialog(mainActivity, date,Define.CREATE_DIALOG);
                dialog.show();
            }
        });
        cal_delete_btn.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View view) {
                Toast.makeText(getContext(),"일정을 삭제하였어요. 복구가 불가능합니다.",Toast.LENGTH_SHORT).show();
                getFragmentManager().beginTransaction().replace(R.id.calendar_view_more,new CalendarViewMoreFragment()).commit();
                deleteDB.databaseDelete(date);
                vm_text.setText("");
                cal_delete_btn.setVisibility(View.GONE);
                cal_update_btn.setVisibility(View.GONE);
                cal_create_btn.setVisibility(View.VISIBLE);

        }
    });


    }


    /**
     * 컨트롤 초기화 해주는 함수
     * */
    public void init(View v) {
        if (v != null) {
            calendarView = v.findViewById(R.id.calendar_view);
            vm_date = v.findViewById(R.id.vm_date);
            view_more = v.findViewById(R.id.calendar_view_more);
            vm_name = v.findViewById(R.id.vm_name);
            vm_text = v.findViewById(R.id.vm_text);
            vm_time = v.findViewById(R.id.vm_time);
            cal_create_btn = v.findViewById(R.id.calendar_create);
            cal_delete_btn = v.findViewById(R.id.calendar_delete);
            cal_update_btn = v.findViewById(R.id.calendar_update);
        }
    }


    /**
     * 이미지 리소스 세팅해주는 함수
     * */
    public void setImageResource() {
        // 예시) button1.setBackgroundResource(R.drawable.image1);
    }



    @Override
    public void view_more_text(CalendarDB data) {
        String name = data.getTitle();
        String text = data.getDescription();
        String startTime = data.getStartTime();
        String endTime = data.getEndTime();


        vm_name.setText("일정명 : "+name);
        vm_text.setText("세부내용 : "+text);
        vm_time.setText("시작시간 : "+startTime+"\n"+"종료시간 : "+endTime);
    }




    /**
     * 각종 리소스 null 처리
     * */
    @Override
    public void onDestroy() {
        super.onDestroy();
        // 예시) button1 = null;
    }
}
