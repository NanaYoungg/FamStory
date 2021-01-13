package com.hongsam.famstrory.activitie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toolbar;

import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hongsam.famstrory.define.Define;
import com.hongsam.famstrory.R;
import com.hongsam.famstrory.firebase.CalendarDB;
import com.hongsam.famstrory.firebase.ReadDB;
import com.hongsam.famstrory.firebase.UpdateDB;
import com.hongsam.famstrory.fragment.CalendarFragment;
import com.hongsam.famstrory.fragment.EmotionFragment;
import com.hongsam.famstrory.fragment.FamCreateFragment;
import com.hongsam.famstrory.fragment.LetterListFragment;
import com.hongsam.famstrory.fragment.LetterReadFragment;
import com.hongsam.famstrory.fragment.LetterWriteFragment;
import com.hongsam.famstrory.fragment.MenuFragment;
import com.hongsam.famstrory.fragment.ProfileFragment;
import com.hongsam.famstrory.fragment.SettingFragment;
import com.hongsam.famstrory.fragment.TimeLineFragment;

import com.hongsam.famstrory.interf.CallbackInterface;
import com.hongsam.famstrory.interf.CustomDialogInterface;
import com.hongsam.famstrory.util.SharedManager;

public class MainActivity extends AppCompatActivity{

    BottomNavigationView navigationView;

    CallbackInterface ci;
    CustomDialogInterface cdi;
    ReadDB readDB;
    UpdateDB updateDB;
    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeFragment(Define.FRAGMENT_ID_LETTER_LIST);

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        SharedManager.getInstance(this);

        changeFragment(Define.FRAGMENT_ID_EMOTION);

        changeFragment(Define.FRAGMENT_ID_EMOTION);
        navigationView = (BottomNavigationView)findViewById(R.id.navi_view);

        readDB = new ReadDB(this);
        updateDB = new UpdateDB(this);
    }
    public void setCi(CallbackInterface ci) {
        this.ci = ci;
    }
    public void setCdi(CustomDialogInterface cdi){
        this.cdi = cdi;
    }


    public void databaseRead(String date) {
        readDB.databaseRead(date);
    }
    public void calendarUpdateGetDialogText(CalendarDB data){
        cdi.calendarUpdateGetDialogText(data);
    }
    public void view_more_text(CalendarDB data) {
        ci.view_more_text(data);
    }
    public void  isDataNull(String date){
        ci.isDateNull(date);
    }
    @Override
    protected void onResume() {
        super.onResume();

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.main_menu:
                    changeFragment(Define.FRAGMENT_ID_PROFILE);
                    break;
                case R.id.calendar_menu:
                    changeFragment(Define.FRAGMENT_ID_CALENDAR);
                    break;
                case R.id.message_menu:
                    changeFragment(Define.FRAGMENT_ID_LETTER_LIST);
                    break;
                case R.id.emotion_menu:
                    changeFragment(Define.FRAGMENT_ID_EMOTION);
                    break;
                case R.id.setting_menu:
                    changeFragment(Define.FRAGMENT_ID_PROFILE);
                    break;
                default:
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
                fragment = new SettingFragment();
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

}