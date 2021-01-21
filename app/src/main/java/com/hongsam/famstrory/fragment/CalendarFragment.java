package com.hongsam.famstrory.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.hongsam.famstrory.R;
import com.hongsam.famstrory.activitie.MainActivity;
import com.hongsam.famstrory.data.Calendar;
import com.hongsam.famstrory.databinding.FragmentCalendarBinding;
import com.hongsam.famstrory.define.Define;
import com.hongsam.famstrory.dialog.CalendarCustomDialog;
import com.hongsam.famstrory.firebase.CalendarFirebaseDB;
import com.hongsam.famstrory.firebase.CheckDB;
import com.hongsam.famstrory.firebase.DeleteDB;
import com.hongsam.famstrory.firebase.ReadDB;
import com.hongsam.famstrory.firebase.UpdateDB;
import com.hongsam.famstrory.interf.CallbackInterface;


/**
 * 메인화면에서 일정관리 화면
 * devaspirant 0510
 *
 */
public class CalendarFragment extends Fragment implements CallbackInterface {
    private FragmentCalendarBinding mb;
    private MainActivity mainActivity;
    protected View mContentView;
    protected String getDate;
    protected DeleteDB deleteDB = new DeleteDB();
    protected FragmentTransaction ft_view_more;
    protected CalendarViewMoreFragment viewMoreFragment;
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
        mb = FragmentCalendarBinding.inflate(getLayoutInflater());
        root = mb.getRoot();
        mContentView = inflater.inflate(R.layout.fragment_calendar, container, false);

        if(getFragmentManager()!=null) {
            ft_view_more = getFragmentManager().beginTransaction();
        }
        CallbackInterface callbackInterface;
        mainActivity.setCallbackInterface(this);

        viewMoreFragment = new CalendarViewMoreFragment();
        ReadDB readDB = new ReadDB(mainActivity);
        readDB.databaseRead(getCalendarYear, getCalendarMonth, getCalendarDay);
        getFragmentManager().beginTransaction().replace(R.id.calendar_view_more, viewMoreFragment).commit();
        FragmentManager newFm = getFragmentManager();
        checkDB.checkDB(getCalendarYear, getCalendarMonth, getCalendarDay,getContext(), newFm, viewMoreFragment,
                mb.calendarCreate,mb.calendarDelete,mb.calendarUpdate,mb.calendarCreateText,mb.calendarDeleteText,mb.calendarUpdateText);


        return root;
    }





    @Override
    public void onResume() {
        super.onResume();
        mb.calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                state = "null";
                mb.vmText.setText("");
                mb.vmTime.setText("");

                month += 1;
                getCalendarYear = year;
                getCalendarMonth = month;
                getCalendarDay = dayOfMonth;

                dateToInt = year + month + dayOfMonth;
                ReadDB readDB = new ReadDB(mainActivity);
                checkDB.checkDB(getCalendarYear,getCalendarMonth,getCalendarDay,getContext(),getFragmentManager(),new CalendarFragment(),
                                mb.calendarCreate,mb.calendarDelete,mb.calendarUpdate,mb.calendarCreateText,mb.calendarDeleteText,mb.calendarUpdateText);
                readDB.databaseRead(getCalendarYear, getCalendarMonth, getCalendarDay);
                if (getFragmentManager()!=null) {
                    getFragmentManager().beginTransaction().replace(R.id.calendar_view_more, viewMoreFragment).commit();
                    FragmentManager newFm = getFragmentManager();
                    checkDB.checkDB(getCalendarYear, getCalendarMonth, getCalendarDay, getContext(), newFm, viewMoreFragment,
                            mb.calendarCreate,mb.calendarDelete,mb.calendarUpdate,mb.calendarCreateText,mb.calendarDeleteText,mb.calendarUpdateText);
                }
                Define.setViewText(mb.vmDate, year + "." + month + "." + dayOfMonth);
                if (state.equals("null")) {
                    mb.calendarCreate.setVisibility(View.VISIBLE);
                    mb.calendarCreateText.setVisibility(View.VISIBLE);
                    mb.calendarDelete.setVisibility(View.INVISIBLE);
                    mb.calendarDeleteText.setVisibility(View.INVISIBLE);
                    mb.calendarUpdate.setVisibility(View.INVISIBLE);
                    mb.calendarUpdateText.setVisibility(View.INVISIBLE);
                }


            }
        });

        mb.calendarCreate.setOnClickListener(new View.OnClickListener() {
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

        mb.calendarDelete.setOnClickListener(new View.OnClickListener() {
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
                        mb.vmText.setText("");
                        mb.calendarDelete.setVisibility(View.GONE);
                        mb.calendarDeleteText.setVisibility(View.GONE);
                        mb.calendarUpdate.setVisibility(View.GONE);
                        mb.calendarUpdateText.setVisibility(View.GONE);
                        mb.calendarCreate.setVisibility(View.VISIBLE);
                        mb.calendarCreateText.setVisibility(View.VISIBLE);
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
        mb.calendarUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //updateDB.updateDB(date);

                CalendarCustomDialog dialog = new CalendarCustomDialog(getCalendarYear, getCalendarMonth, getCalendarDay,mainActivity, Define.UPDATE_DIALOG);
                UpdateDB updateDB = new UpdateDB(mainActivity);
                updateDB.updateDB(getCalendarYear, getCalendarMonth, getCalendarDay);
                dialog.show();

            }
        });




        mb.monthCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.changeFragment(Define.FRAGMENT_ID_MONTH_LIST);

            }
        });

        mb.spinnerMangerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.changeFragment(Define.FRAGMENT_ID_SPINNER_MANGER);
            }
        });


    }




    public interface DataSender{
        public void spinnerFragment(Spinner spinner, ArrayAdapter<String> adapter);
    }
    /**
     * 이미지 리소스 세팅해주는 함수
     */
    public void setImageResource() {
        // 예시) button1.setBackgroundResource(R.drawable.image1);
    }
    @Override
    public void visibleView(int dataIsNull) {
        Log.e("d",dataIsNull+"");

        if (dataIsNull==Define.DATA_IS_NULL){
            mb.calendarDeleteText.setVisibility(View.INVISIBLE);
            mb.calendarUpdateText.setVisibility(View.INVISIBLE);
            mb.calendarCreateText.setVisibility(View.VISIBLE);
            mb.calendarUpdate.setVisibility(View.INVISIBLE);
            mb.calendarDelete.setVisibility(View.INVISIBLE);
            mb.calendarCreate.setVisibility(View.VISIBLE);
        }
        else if (dataIsNull==Define.DATA_IS_NOT_NULL){
            mb.calendarDeleteText.setVisibility(View.VISIBLE);
            mb.calendarUpdateText.setVisibility(View.VISIBLE);
            mb.calendarCreateText.setVisibility(View.INVISIBLE);
            mb.calendarUpdate.setVisibility(View.VISIBLE);
            mb.calendarDelete.setVisibility(View.VISIBLE);
            mb.calendarCreate.setVisibility(View.INVISIBLE);

        }
        else{
            Log.e("e","rr0");
        }
    }

    @Override
    public void view_more_text(Calendar data) {
        Log.e("tag","adfasdf");
        String name = data.getTitle();
        String text = data.getDescription();
        String startTime = data.getStartTime();
        String endTime = data.getEndTime();


        mb.vmName.setText("일정명 : " + name);
        mb.vmText.setText("세부내용 : " + text);
        mb.vmTime.setText("시작시간 : " + startTime + "\n" + "종료시간 : " + endTime);
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
