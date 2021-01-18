package com.hongsam.famstory.activitie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.hongsam.famstory.data.Calendar;
import com.hongsam.famstory.data.Family;
import com.hongsam.famstory.data.Member;
import com.hongsam.famstory.database.DBFamstory;
import com.hongsam.famstory.define.Define;
import com.hongsam.famstory.R;
import com.hongsam.famstory.firebase.CalendarDB;
import com.hongsam.famstory.firebase.ReadDB;
import com.hongsam.famstory.firebase.UpdateDB;
import com.hongsam.famstory.fragment.CalendarFragment;
import com.hongsam.famstory.fragment.EmotionFragment;
import com.hongsam.famstory.fragment.FamCreateFragment;
import com.hongsam.famstory.fragment.LetterListFragment;
import com.hongsam.famstory.fragment.LetterReadFragment;
import com.hongsam.famstory.fragment.LetterWriteFragment;
import com.hongsam.famstory.fragment.MenuFragment;
import com.hongsam.famstory.fragment.MonthCalendar;
import com.hongsam.famstory.fragment.ProfileFragment;
import com.hongsam.famstory.fragment.SettingFragment;
import com.hongsam.famstory.fragment.SpinnerMangerFragment;
import com.hongsam.famstory.fragment.TimeLineFragment;

import com.hongsam.famstory.interf.CallbackInterface;
import com.hongsam.famstory.interf.CustomDialogInterface;
import com.hongsam.famstory.util.FirebaseManager;
import com.hongsam.famstory.util.SharedManager;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";

    BottomNavigationView navigationView;

    CallbackInterface ci;
    CustomDialogInterface cdi;
    ReadDB readDB;
    UpdateDB updateDB;
    InputMethodManager imm;

    public DBFamstory db;

    // 가족객체. db에서 받아와서 넣어줄 예정
    private Family myFamily;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedManager.getInstance(this);
        db = DBFamstory.getInstance(this);

        // Firebase로부터 Token값을 받아 firebase database와 sharedPreference에 저장해준다.
        getFirebaseToken();

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        changeFragment(Define.FRAGMENT_ID_PROFILE);

        navigationView = (BottomNavigationView) findViewById(R.id.navi_view);

        readDB = new ReadDB(this);
        updateDB = new UpdateDB(this);

        changeFragment(Define.FRAGMENT_ID_LETTER_LIST);
    }

    public void writeMember(String famName, String token, Member member) {
        FirebaseManager.dbFamRef.child(famName).child("members").child(token).setValue(member);
    }

    public void updateMember(String famName, Member member) {
        String token = SharedManager.readString(Define.KEY_FIREBASE_TOKEN, "");
        Map<String, Object> memberMap = new HashMap<>();
        memberMap.put(token, member);
        FirebaseManager.dbFamRef.child(famName).child("members").updateChildren(memberMap);
    }

    public void getFirebaseToken() {
        FirebaseMessaging.getInstance().getToken()
            .addOnCompleteListener(new OnCompleteListener<String>() {
                @Override
                public void onComplete(@NonNull Task<String> task) {
                    if (!task.isSuccessful()) {
                        Log.e(TAG, "파이어베이스 토큰 등록 실패", task.getException());
                        return;
                    }

                    String token = task.getResult();
                    Log.d(TAG, "파이어베이스 토큰 : " + token);
                    saveToken(token);
                }
            });
    }

    // 가족 객체를 얻어오는 함수
    public Family getMyFamily() {
        if (myFamily != null)
            return myFamily;
        else
            return null;
    }

    public void saveToken(final String token) {
        SharedManager.writeString(Define.KEY_FIREBASE_TOKEN, token);
        writeMember("테스트가족", token, new Member("아들", "김아들"));
    }

    public void setCallbackInterface(CallbackInterface ci) {
        this.ci = ci;
    }

    public void setCustomInterface(CustomDialogInterface cdi) {
        this.cdi = cdi;
    }

    public void databaseRead(int year, int month, int day, String date) {
        readDB.databaseRead(year,month,day);
    }

    public void calendarUpdateGetDialogText(Calendar data) {
        cdi.calendarUpdateGetDialogText(data);
    }
    public void visibleView(int dataIsNull){
        ci.visibleView(dataIsNull);

    }

    public void view_more_text(Calendar data) {
        ci.view_more_text(data);
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkSelfPermission();

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.calendar_menu:
                        changeFragment(Define.FRAGMENT_ID_PROFILE);
                        break;
                    case R.id.main_menu:
                        changeFragment(Define.FRAGMENT_ID_CALENDAR);
                        break;
                    case R.id.message_menu:
                        changeFragment(Define.FRAGMENT_ID_LETTER_LIST);
                        break;
                    case R.id.emotion_menu:
                        changeFragment(Define.FRAGMENT_ID_EMOTION);
                        break;
                    case R.id.setting_menu:
                        changeFragment(Define.FRAGMENT_ID_SETTING);
                        break;
                }
                return true;
            }
        });
    }

    Fragment fragment = null;

    public void changeFragment(int fragmentId) {

        switch (fragmentId) {
            case Define.FRAGMENT_ID_MENU:
                fragment = new MenuFragment();
                break;

            case Define.FRAGMENT_ID_FAM_CREATE:
                fragment = new FamCreateFragment();
                break;

            case Define.FRAGMENT_ID_TIME_LINE:
                fragment = new TimeLineFragment();
                break;

            case Define.FRAGMENT_ID_CALENDAR:
                fragment = new CalendarFragment();
                break;

            case Define.FRAGMENT_ID_LETTER_LIST:
                fragment = new LetterListFragment();
                break;

            case Define.FRAGMENT_ID_LETTER_WRITE:
                fragment = new LetterWriteFragment();
                break;

            case Define.FRAGMENT_ID_EMOTION:
                fragment = new EmotionFragment();
                break;

            case Define.FRAGMENT_ID_PROFILE:
                fragment = new ProfileFragment();
                break;

            case Define.FRAGMENT_ID_SETTING:
                fragment = new SettingFragment();
                break;

            case Define.FRAGMENT_ID_LETTER_READ:
                fragment = new LetterReadFragment();
                break;
            case Define.FRAGMENT_ID_MONTH_LIST:
                fragment = new MonthCalendar();
                break;
            case Define.FRAGMENT_ID_SPINNER_MANGER:
                //fragment = new SpinnerMangerFragment();
                break;
            default:
                break;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); ++i) {
                    try {
                        getSupportFragmentManager().popBackStack();
                    } catch (IllegalStateException e) {
                        if (getSupportFragmentManager() != null && !getSupportFragmentManager().isStateSaved()) {
                            getSupportFragmentManager().popBackStack();
                        }
                    }
                }

                ft.replace(R.id.basic, fragment);

                if (fragment.isStateSaved()) {
                    ft.commitAllowingStateLoss();
                } else {
                    ft.commit();
                }
            }
        });
    }

    public void showKeyboard(final EditText et, final boolean flag) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (flag) {
                    et.requestFocus();
                    imm.showSoftInput(et, 0);
                } else {
                    imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
                }
            }
        });
    }

    public void checkSelfPermission() {
        String temp = "";

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.READ_EXTERNAL_STORAGE + " ";
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.WRITE_EXTERNAL_STORAGE + " ";
        }

        if (!TextUtils.isEmpty(temp)) {
            ActivityCompat.requestPermissions(this, temp.trim().split(" "), 1);
        } else {
            Toast.makeText(this, "권한을 모두 허용", Toast.LENGTH_SHORT).show();
        }
    }


}