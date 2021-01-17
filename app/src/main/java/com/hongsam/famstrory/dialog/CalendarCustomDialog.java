package com.hongsam.famstrory.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.hongsam.famstrory.activitie.MainActivity;
import com.hongsam.famstrory.databinding.CalendarDialogBinding;
import com.hongsam.famstrory.define.Define;
import com.hongsam.famstrory.firebase.CalendarDB;
import com.hongsam.famstrory.firebase.CreateDB;
import com.hongsam.famstrory.firebase.UpdateDB;
import com.hongsam.famstrory.interf.CustomDialogInterface;

import java.util.Calendar;


/*
    CalendarFragment 다이얼로그 일정 관련 이벤트
*/

public class CalendarCustomDialog extends Dialog implements CustomDialogInterface {
    private Dialog dialog;
    private Button dialogOkBtn, dialogCancelBtn, startTimeBtn, endTimeBtn, timePickerSubmitButton;
    private TextView startTimeTv, endTimeTv;
    private TimePicker timePicker;
    private Spinner spinner;
    private EditText viewMoreTitleEt, viewMoreDescriptionEt;
    private TextView description_set_text;
    String date;
    private int pickerState;
    View mBindingRoot;


    String start_time_str, end_time_str;
    // 시간 비교위해 초기값 설정
    int start_time = -1, end_time = 86401;
    int type;

    // 일정추가시 파이어베이스 db 연동
    CreateDB createDB = new CreateDB();

    // 현재 시간을 가져오기위해 객체 생성
    Calendar cal = Calendar.getInstance();
    int hour = cal.get(Calendar.HOUR_OF_DAY);
    int min = cal.get(Calendar.MINUTE);
    int nowTime = hour * 60 + min;
    private CalendarDialogBinding mBinding;
    UpdateDB updateDB;

    public CalendarCustomDialog(@NonNull MainActivity context, String date, int type) {
        super(context);
        this.date = date;
        this.type = type;

        dialog = new Dialog(context);
        if (type == Define.UPDATE_DIALOG) {
            updateDB = new UpdateDB(context);
            updateDB.updateDB(date);
        }
        context.setCdi(this);


    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = CalendarDialogBinding.inflate(getLayoutInflater());
        mBindingRoot = mBinding.getRoot();
        setContentView(mBindingRoot);

        //setContentView(R.layout.calendar_dialog);

        // xml id 연결
        init();

        viewMoreDescriptionEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int len = viewMoreDescriptionEt.getText().toString().length();

                description_set_text.setText(len + "/100");
                if (len < 70) {

                    description_set_text.setTextColor(Color.parseColor("#000000"));
                } else if (len < 90) {

                    description_set_text.setTextColor(Color.parseColor("#FB8C00"));
                } else {

                    description_set_text.setTextColor(Color.parseColor("#CD0202"));
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        dialogOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // EditText 에서 일정이름,일정내용을 가져옴
                String str_title = viewMoreTitleEt.getText().toString();
                String str_text = viewMoreDescriptionEt.getText().toString();
                String str_start_time = startTimeTv.getText().toString();
                String str_end_time = endTimeTv.getText().toString();

                // 확인 버튼을 눌렀을때 채우지 않은 부분이 있는지 확인
                if (str_text.length() == 0 || str_text.length() == 0 ||
                        startTimeTv.getText().toString().equals("시작시간") ||
                        endTimeTv.getText().toString().equals("종료시간")) {
                    Toast.makeText(getContext(), "빈칸을 채워주세요", Toast.LENGTH_SHORT).show();
                } else {
                    // 번들객체에 데이터를 담아서 Firebase 로 데이터 처리
                    Bundle bundle = new Bundle();
                    bundle.putString("date", date);
                    bundle.putString("title", str_title);
                    bundle.putString("text", str_text);
                    bundle.putString("startT", str_start_time);
                    bundle.putString("endT", str_end_time);
                    createDB.pushFireBaseDatabase(bundle);
                    dismiss();
                }

            }
        });
        // 취소버튼을 눌렀을때 다이얼로그 종료
        dialogCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "취소하였습니다.", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
        // 시작시간을 설정하면 timePiker 와 시간 적용 버튼의 Visibility 의 상태를 gone 에서 Visible 로 바꿔줌
        startTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickerState = Define.TIME_PICKER_START;
                timePicker.setVisibility(View.VISIBLE);
                timePickerSubmitButton.setVisibility(View.VISIBLE);
            }
        });
        // timePicker 관련 이벤트
        timePickerSubmitButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();
                // 시간 비교위해 분으로 바꿈
                start_time = hour * 60 + minute;
                // 시간에 따라 오전 오후 정함
                String full_time = "오전";
                // 시간이 오후인경우 시간 -12를 해줌

                if (hour > 12) {
                    full_time = "오후";
                    hour -= 12;
                }
                if (start_time > end_time) {
                    Toast.makeText(getContext(), "종료시간보다 시작시간이 커질수 없죠", Toast.LENGTH_SHORT).show();
                } else if (start_time < nowTime) {
                    Toast.makeText(getContext(), "이미 지나간 시간은 돌아오지 않아요", Toast.LENGTH_SHORT).show();
                } else {
                    if (pickerState == Define.TIME_PICKER_START) {
                        start_time_str = full_time + " " + hour + ":" + minute;
                        startTimeTv.setText(start_time_str);
                        // 시간 설정을 마치면 다시 Visibility 상태를 Gone 으로 바꿔줌
                        timePicker.setVisibility(View.GONE);
                        timePickerSubmitButton.setVisibility(View.GONE);
                    } else if (pickerState == Define.TIME_PICKER_END) {

                        end_time_str = full_time + " " + hour + ":" + minute;
                        endTimeTv.setText(end_time_str);
                        // 시간 설정을 마치면 다시 Visibility 상태를 Gone 으로 바꿔줌
                        timePicker.setVisibility(View.GONE);
                        timePickerSubmitButton.setVisibility(View.GONE);
                    }
                }
            }
        });

        endTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickerState = Define.TIME_PICKER_END;
                timePicker.setVisibility(View.VISIBLE);
                timePickerSubmitButton.setVisibility(View.VISIBLE);
            }
        });

    }


    public void init() {
        viewMoreTitleEt = mBinding.viewMoreTitleEt;
        viewMoreDescriptionEt = mBinding.viewMoreDescriptionEt;
        dialogOkBtn = mBinding.dialogOkBtn;
        dialogCancelBtn = mBinding.dialogCancelBtn;
        timePicker = mBinding.timePicker;
        startTimeBtn = mBinding.startTimeBtn;
        startTimeTv = mBinding.startTimeTv;
        endTimeBtn = mBinding.endTimeBtn;
        endTimeTv = mBinding.endTimeTv;
        timePickerSubmitButton = mBinding.timePickerSubmitBtn;
        spinner = mBinding.spinner;
        description_set_text = mBinding.descriptionSetText;

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void calendarUpdateGetDialogText(CalendarDB data) {

        if (type == Define.UPDATE_DIALOG) {
            String dialogGetTitle = data.getTitle();
            String dialogGetDescription = data.getDescription();
            String dialogGetStartTime = data.getStartTime();
            String dialogGetEndTime = data.getEndTime();
            viewMoreTitleEt.setText(dialogGetTitle);
            viewMoreDescriptionEt.setText(dialogGetDescription);
            startTimeTv.setText(dialogGetStartTime);
            endTimeTv.setText(dialogGetEndTime);
        }
    }
}
