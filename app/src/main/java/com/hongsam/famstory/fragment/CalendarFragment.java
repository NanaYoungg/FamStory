package com.hongsam.famstory.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.hongsam.famstory.R;
import com.hongsam.famstory.activitie.MainActivity;
import com.hongsam.famstory.data.Calendar;
import com.hongsam.famstory.data.Emotion;
import com.hongsam.famstory.data.Member;
import com.hongsam.famstory.database.DBFamstory;
import com.hongsam.famstory.database.MyMessageDB;
import com.hongsam.famstory.databinding.FragmentCalendarBinding;
import com.hongsam.famstory.define.Define;
import com.hongsam.famstory.dialog.CalendarCustomDialog;
import com.hongsam.famstory.dialog.MonthCalendarDialog;
import com.hongsam.famstory.firebase.SpinnerDB;
import com.hongsam.famstory.firebase.CheckDB;
import com.hongsam.famstory.firebase.DeleteDB;
import com.hongsam.famstory.firebase.ReadDB;
import com.hongsam.famstory.firebase.UpdateDB;
import com.hongsam.famstory.interf.CallbackInterface;
import com.hongsam.famstory.service.MyFirebaseMessagingService;
import com.hongsam.famstory.util.FirebaseManager;

import com.hongsam.famstory.database.MyMessageDB.*;

/**
 * 메인화면에서 일정관리 화면
 * devaspirant 0510
 *
 */
public class CalendarFragment extends Fragment implements CallbackInterface {
    private SQLiteDatabase sqLiteDatabase;
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

/*        sqLiteDatabase = mainActivity.openOrCreateDatabase("myMsg.db",Context.MODE_PRIVATE,null);
        String sql = "create table if not exists myMsgTBL"+
                "(_id integer PRIMARY KEY autoincrement," +
                "MSG varchar(100),"+
                "TIME varchar(20))";
        sqLiteDatabase.execSQL(sql);*/
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
        mb.calendarView.setLis
        // 달력 선택했을때
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

                dateToInt = getCalendarMonth  + getCalendarMonth + getCalendarDay;
                Toast.makeText(getContext(),year+month+dayOfMonth+",",Toast.LENGTH_SHORT).show();
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
        // 일정생성버튼 눌렀을때
        mb.calendarCreate.setOnClickListener(new View.OnClickListener() {
            final int todayYear = nowDate.get(java.util.Calendar.YEAR);
            final int todayMonth = nowDate.get(java.util.Calendar.MONTH)+1;
            final int todayDay = nowDate.get(java.util.Calendar.DATE);
            //오늘 날짜랑 캘린더에서 선택된 날짜랑 비교
            @Override
            public void onClick(View v) {
                final int todayDate = todayYear + todayMonth + todayDay;
                int CalendarDate = getCalendarDay+getCalendarYear+getCalendarMonth;
                Log.e("aaaa","Today"+todayDate+"Calendar"+getCalendarYear);

                if (todayDate>CalendarDate) {
                    Toast.makeText(getContext(), "이미 시간이 지난 일정은 생성이 불가능 합니다.", Toast.LENGTH_SHORT).show();
                } else {
                    CalendarCustomDialog dialog = new CalendarCustomDialog(getCalendarYear, getCalendarMonth, getCalendarDay,mainActivity, Define.CREATE_DIALOG);
                    dialog.show();
                }
            }
        });
        //삭제버튼 눌렀을때
        mb.calendarDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                Toast.makeText(getContext(),dateToInt+"",Toast.LENGTH_SHORT).show();
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
        // 수정버튼 눌렀을때
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



        // 월별 일정보기 버튼 눌렀을때
        mb.monthCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("dd",getCalendarYear+" "+getCalendarMonth);
                MonthCalendarDialog monthDialog = new MonthCalendarDialog(mainActivity,getCalendarYear,getCalendarMonth);

                monthDialog.show();
/*                FragmentManager fm = getFragmentManager();
                if(fm!=null) {
                    FragmentTransaction ft = fm.beginTransaction();
                    MonthCalendarFragment mcf = new MonthCalendarFragment();
                    ft.add(R.id.calendar_fragment_layout,mcf).commit();

                }*/
                //mainActivity.changeFragment(Define.FRAGMENT_ID_MONTH_LIST);

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
    public void view_more_text(Calendar data) {
        Log.e("tag","adfasdf");
        String name = data.getTitle();
        String text = data.getDescription();
        String startTime = data.getStartTime();
        String endTime = data.getEndTime();
        String type = data.getType();


        mb.vmName.setText("일정명 : " + name);
        mb.vmText.setText("세부내용 : " + text);
        mb.vmTime.setText("시작시간 : " + startTime + "\n" + "종료시간 : " + endTime);
        mb.type.setText(type);
    }

    @Override
    public void visibleView(int dataIsNull) {

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
