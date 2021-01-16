package com.hongsam.famstory.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.hongsam.famstory.R;
import com.hongsam.famstory.activitie.MainActivity;
import com.hongsam.famstory.databinding.FragmentCalendarBinding;
import com.hongsam.famstory.define.Define;
import com.hongsam.famstory.dialog.CalendarCustomDialog;
import com.hongsam.famstory.firebase.CalendarDB;
import com.hongsam.famstory.firebase.DeleteDB;
import com.hongsam.famstory.firebase.ReadDB;
import com.hongsam.famstory.firebase.CheckDB;
import com.hongsam.famstory.firebase.UpdateDB;
import com.hongsam.famstory.interf.CallbackInterface;

import java.util.Calendar;


/**
 * 메인화면에서 일정관리 화면
 * devaspirant 0510
 *
 */
public class CalendarFragment extends Fragment implements CallbackInterface {
    private FragmentCalendarBinding mBinding;
    private MainActivity mainActivity;
    protected View mContentView;
    private CalendarView calendarView;
    private TextView vm_name, vm_text, vm_date, vm_time;
    protected ConstraintLayout view_more;
    protected String getDate;
    public static Button cal_update_btn, cal_delete_btn, cal_create_btn;
    protected DeleteDB deleteDB = new DeleteDB();
    protected FragmentTransaction ft_view_more;
    protected CalendarViewMoreFragment viewMoreFragment;
    private Button monthCalendar;
    protected CheckDB checkDB = new CheckDB();
    public static String state = "null";
    protected View root;
    private int getYear,getMonth,getDay;
    Calendar nowDate = Calendar.getInstance();
    String date = nowDate.get(Calendar.YEAR) + "년" + nowDate.get(Calendar.MONTH) + "월" + nowDate.get(Calendar.DATE) + "일";
    int dateToInt = nowDate.get(Calendar.YEAR) + nowDate.get(Calendar.MONTH) + nowDate.get(Calendar.DATE);

    int Year = nowDate.get(Calendar.YEAR);
    int Month = nowDate.get(Calendar.MONTH);
    int Day = nowDate.get(Calendar.DATE);
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
        Toast.makeText(getContext(), date, Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container == null)
            return null;

        if (inflater == null)
            return null;
        mBinding = FragmentCalendarBinding.inflate(getLayoutInflater());
        root = mBinding.getRoot();
        mContentView = inflater.inflate(R.layout.fragment_calendar, container, false);

        init();
        if(getFragmentManager()!=null) {
            ft_view_more = getFragmentManager().beginTransaction();
        }
        viewMoreFragment = new CalendarViewMoreFragment();
        ReadDB readDB = new ReadDB(mainActivity);
        readDB.databaseRead(date);
        getFragmentManager().beginTransaction().replace(R.id.calendar_view_more, viewMoreFragment).commit();
        FragmentManager newFm = getFragmentManager();
        checkDB.checkDB(Year,Month,Day,date, getContext(), newFm, viewMoreFragment);


        return root;
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

                month += 1;
                date = year + "년" + month + "월" + dayOfMonth + "일";


                dateToInt = year + month + dayOfMonth;
                Toast.makeText(getContext(),dateToInt+"",Toast.LENGTH_SHORT).show();
                ReadDB readDB = new ReadDB(mainActivity);

                readDB.databaseRead(date);
                if (getFragmentManager()!=null) {
                    getFragmentManager().beginTransaction().replace(R.id.calendar_view_more, viewMoreFragment).commit();
                    FragmentManager newFm = getFragmentManager();
                    checkDB.checkDB(Year,Month,Day,date, getContext(), newFm, viewMoreFragment);
                }
                Define.setViewText(vm_date, year + "." + month + "." + dayOfMonth);
                if (state.equals("null")) {
                    cal_create_btn.setVisibility(View.VISIBLE);
                    cal_delete_btn.setVisibility(View.INVISIBLE);
                    cal_update_btn.setVisibility(View.INVISIBLE);
                }


            }
        });

        cal_create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int todayDate = nowDate.get(Calendar.YEAR) + nowDate.get(Calendar.MONTH) + nowDate.get(Calendar.DATE);

                if (dateToInt<=todayDate) {
                    Toast.makeText(getContext(),dateToInt+"<="+todayDate,Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), "이미 시간이 지난 일정은 생성이 불가능 합니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(),dateToInt+">"+todayDate,Toast.LENGTH_SHORT).show();
                    CalendarCustomDialog dialog = new CalendarCustomDialog(Year,Month,Day,mainActivity, date, Define.CREATE_DIALOG);
                    dialog.show();
                }
            }
        });

        cal_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setMessage("다시 한번 생각해보세요.\n삭제할경우 복구가 불가능합니다.")
                        .setTitle("후회 하지 않으시겠습니까?");
                alertDialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(getContext(), "일정을 삭제하였어요. 복구가 불가능합니다.", Toast.LENGTH_SHORT).show();
                        getFragmentManager().beginTransaction().replace(R.id.calendar_view_more, new CalendarViewMoreFragment()).commit();
                        deleteDB.databaseDelete(date);
                        vm_text.setText("");
                        cal_delete_btn.setVisibility(View.GONE);
                        cal_update_btn.setVisibility(View.GONE);
                        cal_create_btn.setVisibility(View.VISIBLE);
                    }
                });
                alertDialog.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), "삭제가 취소되었습니다.", Toast.LENGTH_SHORT).show();

                    }
                });
                alertDialog.show();


            }
        });
        cal_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "update dialog", Toast.LENGTH_SHORT).show();
                //updateDB.updateDB(date);
                int year = nowDate.get(Calendar.YEAR);
                int month = nowDate.get(Calendar.MONTH);
                int day = nowDate.get(Calendar.DATE);
                CalendarCustomDialog dialog = new CalendarCustomDialog(year,month,day,mainActivity, date, Define.UPDATE_DIALOG);
                Toast.makeText(getContext(), "update", Toast.LENGTH_SHORT).show();
                dialog.show();

            }
        });




        monthCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFragmentManager()!=null) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.calendar_fragment_layout,new MonthCalendar()).commit();

                }

            }
        });


    }


    /**
     * 컨트롤 초기화 해주는 함수
     */
    public void init() {

        calendarView = mBinding.calendarView;
        vm_date = mBinding.vmDate;
        view_more = mBinding.calendarViewMore;
        vm_name = mBinding.vmName;
        vm_text = mBinding.vmText;
        vm_time = mBinding.vmTime;
        cal_create_btn = mBinding.calendarCreate;
        cal_delete_btn = mBinding.calendarDelete;
        cal_update_btn = mBinding.calendarUpdate;
        monthCalendar = mBinding.monthCalendar;
    }


    /**
     * 이미지 리소스 세팅해주는 함수
     */
    public void setImageResource() {
        // 예시) button1.setBackgroundResource(R.drawable.image1);
    }


    @Override
    public void view_more_text(CalendarDB data) {
        String name = data.getTitle();
        String text = data.getDescription();
        String startTime = data.getStartTime();
        String endTime = data.getEndTime();


        vm_name.setText("일정명 : " + name);
        vm_text.setText("세부내용 : " + text);
        vm_time.setText("시작시간 : " + startTime + "\n" + "종료시간 : " + endTime);
    }


    /**
     * 각종 리소스 null 처리
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        // 예시) button1 = null;
    }
}
