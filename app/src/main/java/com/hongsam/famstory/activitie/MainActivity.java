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
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hongsam.famstory.databinding.ActivityMainBinding;
import com.hongsam.famstory.define.Define;
import com.hongsam.famstory.R;

import com.hongsam.famstory.data.Calendar;
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
import com.hongsam.famstory.util.SharedManager;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigationView;

    private CallbackInterface ci;
    private CustomDialogInterface cdi;
    private ReadDB readDB;
    private ActivityMainBinding mBinding;
    private View root;
    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        root = mBinding.getRoot();
        setContentView(root);

        changeFragment(Define.FRAGMENT_ID_LETTER_LIST);

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        SharedManager.getInstance(this);

        changeFragment(Define.FRAGMENT_ID_CALENDAR);

        navigationView = (BottomNavigationView) findViewById(R.id.navi_view);

        readDB = new ReadDB(this);

        checkSelfPermission();
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
                fragment = new SpinnerMangerFragment();
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
        if (TextUtils.isEmpty(temp) == false) {
            ActivityCompat.requestPermissions(this, temp.trim().split(" "), 1);
        } else {
            Toast.makeText(this, "권한을 모두 허용", Toast.LENGTH_SHORT).show();
        }
    }


}