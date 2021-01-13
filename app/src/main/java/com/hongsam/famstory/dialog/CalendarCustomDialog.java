package com.hongsam.famstory.dialog;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.hongsam.famstory.R;
import com.hongsam.famstory.activitie.MainActivity;
import com.hongsam.famstory.define.Define;
import com.hongsam.famstory.firebase.CalendarDB;
import com.hongsam.famstory.firebase.CreateDB;
import com.hongsam.famstory.firebase.UpdateDB;
import com.hongsam.famstory.interf.CustomDialogInterface;

import java.util.Calendar;


/*
    CalendarFragment 다이얼로그 일정 관련 이벤트
*/

public class CalendarCustomDialog extends Dialog implements CustomDialogInterface {
    Dialog dialog;
    Button dialogOkBtn, dialogCancelBtn, startTimeBtn, ednTimeBtn, timePickerSubmitStartBtn, timePickerSubmitEndBtn;
    public TextView startTimeTv, endTimeTv;
    TimePicker timePicker;
    public EditText viewMoreTitleEt, viewMoreDescriptionEt;
    String date;
    MainActivity  mainActivity;
    String start_time_str,end_time_str;
    // 시간 비교위해 초기값 설정
    int start_time=-1,end_time=86401;
    int type;

    // 일정추가시 파이어베이스 db 연동
    CreateDB createDB = new CreateDB();

    // 현재 시간을 가져오기위해 객체 생성
    Calendar cal = Calendar.getInstance();
    int hour = cal.get(Calendar.HOUR_OF_DAY);
    int min = cal.get(Calendar.MINUTE);
    int nowTime = hour*60+min;
    UpdateDB updateDB;
    public CalendarCustomDialog(@NonNull MainActivity context, String date,int type) {
        super(context);
        this.date = date;
        this.type = type;

        dialog = new Dialog(context);

        updateDB = new UpdateDB(context);
        updateDB.updateDB(date);
        context.setCdi(this);

    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_dialog);
        Log.e("interface",type+"00");
        Toast.makeText(getContext(),date+"",Toast.LENGTH_SHORT).show();
        // xml id 연결
        init();


        dialogOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // EditText 에서 일정이름,일정내용을 가져옴
                String str_title = viewMoreTitleEt.getText().toString();
                String str_text = viewMoreDescriptionEt.getText().toString();
                // 확인 버튼을 눌렀을때 채우지 않은 부분이 있는지 확인
                if (str_text.length()==0 || str_text.length()==0 ||
                        startTimeTv.getText().toString().equals("시작시간") ||
                        endTimeTv.getText().toString().equals("종료시간")){
                    Toast.makeText(getContext(),"빈칸을 채워주세요",Toast.LENGTH_SHORT).show();
                }
                else {
                    // 번들객체에 데이터를 담아서 Firebase 로 데이터 처리
                    Bundle bundle = new Bundle();
                    bundle.putString("date",date);
                    bundle.putString("title", str_title);
                    bundle.putString("text", str_text);
                    bundle.putString("startT",start_time_str);
                    bundle.putString("endT",end_time_str);
                    createDB.pushFireBaseDatabase(bundle);
                    dismiss();
                }

            }
        });
        // 취소버튼을 눌렀을때 다이얼로그 종료
        dialogCancelBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"취소하였습니다.",Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
        // 시작시간을 설정하면 timePiker 와 시간 적용 버튼의 Visibility 의 상태를 gone 에서 Visible 로 바꿔줌
        startTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePicker.setVisibility(View.VISIBLE);
                timePickerSubmitStartBtn.setVisibility(View.VISIBLE);
            }
        });
        // timePicker 관련 이벤트
        timePickerSubmitStartBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();
                // 시간 비교위해 분으로 바꿈
                start_time = hour*60+minute;
                // 시간에 따라 오전 오후 정함
                String full_time = "오전";
                // 시간이 오후인경우 시간 -12를 해줌
                if (hour>12){
                    full_time = "오후";
                    hour-=12;
                }
                if (start_time>end_time){
                    Toast.makeText(getContext(),"종료시간보다 시작시간이 커질수 없죠",Toast.LENGTH_SHORT).show();
                }
                else if (start_time<nowTime){
                    Toast.makeText(getContext(),"이미 지나간 시간은 돌아오지 않아요",Toast.LENGTH_SHORT).show();
                }
                else {
                    start_time_str = full_time + " " + hour + ":" + minute;
                    Define.setViewText(startTimeTv,start_time_str );
                    // 시간 설정을 마치면 다시 Visibility 상태를 Gone 으로 바꿔줌
                    timePicker.setVisibility(View.GONE);
                    timePickerSubmitStartBtn.setVisibility(View.GONE);
                }
            }
        });

        ednTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePicker.setVisibility(View.VISIBLE);
                timePickerSubmitEndBtn.setVisibility(View.VISIBLE);
            }
        });
        timePickerSubmitEndBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();
                // 시간 비교위해 분으로 바꿈
                end_time = hour*60+minute;
                String full_time = "오전";
                if (hour>12){
                    full_time = "오후";
                    hour-=12;
                }
                if (start_time>end_time){
                    Toast.makeText(getContext(),"종료시간이 시작시간보다 작을 수 없죠",Toast.LENGTH_SHORT).show();
                }
                else {
                    end_time_str = full_time + " " + hour + ":" + minute;
                    Define.setViewText(endTimeTv, end_time_str);
                    timePicker.setVisibility(View.GONE);
                    timePickerSubmitEndBtn.setVisibility(View.GONE);
                }

            }
        });

    }





    public void init(){

        viewMoreTitleEt = findViewById(R.id.view_more_title_et);
        viewMoreDescriptionEt = findViewById(R.id.view_more_description_et);
        dialogOkBtn = findViewById(R.id.dialog_ok_btn);
        dialogCancelBtn = findViewById(R.id.dialog_cancel_btn);
        timePicker = findViewById(R.id.time_picker);
        startTimeBtn = findViewById(R.id.start_time_btn);
        startTimeTv = findViewById(R.id.start_time_tv);
        ednTimeBtn = findViewById(R.id.end_time_btn);
        endTimeTv = findViewById(R.id.end_time_tv);
        timePickerSubmitStartBtn = findViewById(R.id.time_picker_submit_start_btn);
        timePickerSubmitEndBtn = findViewById(R.id.time_picker_submit_end_btn);

    }

    @Override
    public void calendarUpdateGetDialogText(CalendarDB data) {

        Toast.makeText(getContext(),"oner",Toast.LENGTH_SHORT).show();
        if (type==Define.UPDATE_DIALOG){
            Log.e("Log","inreface");
            Toast.makeText(getContext(),"oner",Toast.LENGTH_SHORT).show();
            String dialogGetTitle = data.getTitle();
            String dialogGetDescription  = data.getDescription();
            String dialogGetStartTime = data.getStartTime();
            String dialogGetEndTime = data.getEndTime();
            Toast.makeText(getContext(),dialogGetDescription,Toast.LENGTH_SHORT).show();
            viewMoreTitleEt.setText(dialogGetTitle);
            viewMoreDescriptionEt.setText(dialogGetDescription);
            startTimeTv.setText(dialogGetStartTime);
            endTimeTv.setText(dialogGetEndTime);
        }
    }
}
