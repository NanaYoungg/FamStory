package com.hongsam.famstory.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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
import com.hongsam.famstory.data.Calendar;
import com.hongsam.famstory.firebase.CalendarFirebaseDB;
import com.hongsam.famstory.firebase.DeleteDB;
import com.hongsam.famstory.firebase.ReadDB;
import com.hongsam.famstory.firebase.CheckDB;
import com.hongsam.famstory.firebase.UpdateDB;
import com.hongsam.famstory.interf.CallbackInterface;


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
    private TextView vm_name, vm_text, vm_date;
    public static TextView vm_time,calendarUpdateText,calendarCreateText,calendarDeleteText;
    protected ConstraintLayout view_more;
    protected String getDate;
    public static Button calendarUpdateBtn, calendarDeleteBtn, calendarCreateBtn;
    protected DeleteDB deleteDB = new DeleteDB();
    protected FragmentTransaction ft_view_more;
    protected CalendarViewMoreFragment viewMoreFragment;
    private Button monthCalendar,spinnerManger;
    protected CheckDB checkDB = new CheckDB();
    public static String state = "null";
    protected View root;
    java.util.Calendar nowDate = java.util.Calendar.getInstance();
    int dateToInt = nowDate.get(java.util.Calendar.YEAR) + nowDate.get(java.util.Calendar.MONTH)+1 + nowDate.get(java.util.Calendar.DATE);
    private int getCalendarYear = nowDate.get(java.util.Calendar.YEAR);
    private int getCalendarMonth = nowDate.get(java.util.Calendar.MONTH)+1;
    private int getCalendarDay = nowDate.get(java.util.Calendar.DATE);
    CalendarFirebaseDB a123 = new CalendarFirebaseDB();



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
        mainActivity.setCallbackInterface(this);

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
        mBinding = FragmentCalendarBinding.inflate(getLayoutInflater());
        root = mBinding.getRoot();
        mContentView = inflater.inflate(R.layout.fragment_calendar, container, false);

        init();
        if(getFragmentManager()!=null) {
            ft_view_more = getFragmentManager().beginTransaction();
        }
        viewMoreFragment = new CalendarViewMoreFragment();
        ReadDB readDB = new ReadDB(mainActivity);
        readDB.databaseRead(getCalendarYear, getCalendarMonth, getCalendarDay);
        getFragmentManager().beginTransaction().replace(R.id.calendar_view_more, viewMoreFragment).commit();
        FragmentManager newFm = getFragmentManager();
        checkDB.checkDB(getCalendarYear, getCalendarMonth, getCalendarDay,getContext(), newFm, viewMoreFragment);


        return root;
    }


    @Override
    public void visibleView(int dataIsNull) {
        if (dataIsNull==Define.DATA_IS_NULL){
            calendarDeleteText.setVisibility(View.INVISIBLE);
            calendarUpdateText.setVisibility(View.INVISIBLE);
            calendarCreateText.setVisibility(View.VISIBLE);
            calendarUpdateBtn.setVisibility(View.INVISIBLE);
            calendarDeleteBtn.setVisibility(View.INVISIBLE);
            calendarCreateBtn.setVisibility(View.VISIBLE);
        }
        else if (dataIsNull==Define.DATA_IS_NOT_NULL){
            calendarDeleteText.setVisibility(View.VISIBLE);
            calendarUpdateText.setVisibility(View.VISIBLE);
            calendarCreateText.setVisibility(View.INVISIBLE);
            calendarUpdateBtn.setVisibility(View.VISIBLE);
            calendarDeleteBtn.setVisibility(View.VISIBLE);
            calendarCreateBtn.setVisibility(View.INVISIBLE);

        }
        else{
            Log.e("e","rr0");
        }
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
                getCalendarYear = year;
                getCalendarMonth = month;
                getCalendarDay = dayOfMonth;

                dateToInt = year + month + dayOfMonth;
                ReadDB readDB = new ReadDB(mainActivity);

                readDB.databaseRead(getCalendarYear, getCalendarMonth, getCalendarDay);
                if (getFragmentManager()!=null) {
                    getFragmentManager().beginTransaction().replace(R.id.calendar_view_more, viewMoreFragment).commit();
                    FragmentManager newFm = getFragmentManager();
                    checkDB.checkDB(getCalendarYear, getCalendarMonth, getCalendarDay, getContext(), newFm, viewMoreFragment);
                }
                Define.setViewText(vm_date, year + "." + month + "." + dayOfMonth);
                if (state.equals("null")) {
                    calendarCreateBtn.setVisibility(View.VISIBLE);
                    calendarCreateText.setVisibility(View.VISIBLE);
                    calendarDeleteBtn.setVisibility(View.INVISIBLE);
                    calendarDeleteText.setVisibility(View.INVISIBLE);
                    calendarUpdateBtn.setVisibility(View.INVISIBLE);
                    calendarUpdateText.setVisibility(View.INVISIBLE);
                }


            }
        });

        calendarCreateBtn.setOnClickListener(new View.OnClickListener() {
            final int todayYear = nowDate.get(java.util.Calendar.YEAR);
            final int todayMonth = nowDate.get(java.util.Calendar.MONTH)+1;
            final int todayDay = nowDate.get(java.util.Calendar.DATE);
            @Override
            public void onClick(View v) {
                int todayDate = todayYear + todayMonth + todayDay;

                if (todayDate>dateToInt) {
                    Toast.makeText(getContext(), "이미 시간이 지난 일정은 생성이 불가능 합니다.", Toast.LENGTH_SHORT).show();
                } else {
                    CalendarCustomDialog dialog = new CalendarCustomDialog(getCalendarYear, getCalendarMonth, getCalendarDay,mainActivity, Define.CREATE_DIALOG);
                    dialog.show();
                }
            }
        });

        calendarDeleteBtn.setOnClickListener(new View.OnClickListener() {
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
                        deleteDB.databaseDelete(getCalendarYear, getCalendarMonth, getCalendarDay);
                        vm_text.setText("");
                        calendarDeleteBtn.setVisibility(View.GONE);
                        calendarDeleteText.setVisibility(View.GONE);
                        calendarUpdateBtn.setVisibility(View.GONE);
                        calendarUpdateText.setVisibility(View.GONE);
                        calendarCreateBtn.setVisibility(View.VISIBLE);
                        calendarCreateText.setVisibility(View.VISIBLE);
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
        calendarUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //updateDB.updateDB(date);

                CalendarCustomDialog dialog = new CalendarCustomDialog(getCalendarYear, getCalendarMonth, getCalendarDay,mainActivity, Define.UPDATE_DIALOG);
                UpdateDB updateDB = new UpdateDB(mainActivity);
                updateDB.updateDB(getCalendarYear, getCalendarMonth, getCalendarDay);
                dialog.show();

            }
        });




        monthCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.changeFragment(Define.FRAGMENT_ID_MONTH_LIST);

            }
        });

        spinnerManger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.changeFragment(Define.FRAGMENT_ID_SPINNER_MANGER);
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
        calendarCreateBtn = mBinding.calendarCreate;
        calendarDeleteBtn = mBinding.calendarDelete;
        calendarUpdateBtn = mBinding.calendarUpdate;
        calendarCreateText = mBinding.calendarCreateText;
        calendarUpdateText = mBinding.calendarUpdateText;
        calendarDeleteText = mBinding.calendarDeleteText;
        monthCalendar = mBinding.monthCalendar;
        spinnerManger = mBinding.spinnerMangerBtn;
    }


    /**
     * 이미지 리소스 세팅해주는 함수
     */
    public void setImageResource() {
        // 예시) button1.setBackgroundResource(R.drawable.image1);
    }


    @Override
    public void view_more_text(Calendar data) {
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
