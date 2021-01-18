package com.hongsam.famstrory.activitie;

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
import com.hongsam.famstrory.data.Member;
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
import com.hongsam.famstrory.util.FirebaseManager;
import com.hongsam.famstrory.util.SharedManager;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigationView;

    CallbackInterface ci;
    CustomDialogInterface cdi;
    ReadDB readDB;
    UpdateDB updateDB;
    InputMethodManager imm;

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFirebaseToken();

        changeFragment(Define.FRAGMENT_ID_LETTER_LIST);

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        SharedManager.getInstance(this);

        changeFragment(Define.FRAGMENT_ID_PROFILE);

        navigationView = (BottomNavigationView) findViewById(R.id.navi_view);

        readDB = new ReadDB(this);
        updateDB = new UpdateDB(this);

        checkSelfPermission();
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

    public void saveToken(final String token) {
        SharedManager.writeString(Define.KEY_FIREBASE_TOKEN, token);
        writeMember("테스트가족", token, new Member("엄마", "김엄마"));
    }

    public void writeMember(String famName, String token, Member member) {
        FirebaseManager.dbFamRef.child(famName).child("members").child(token).setValue(member);
    }

    public void setCi(CallbackInterface ci) {
        this.ci = ci;
    }

    public void setCdi(CustomDialogInterface cdi) {
        this.cdi = cdi;
    }


    public void databaseRead(String date) {
        readDB.databaseRead(date);
    }

    public void calendarUpdateGetDialogText(CalendarDB data) {
        cdi.calendarUpdateGetDialogText(data);
    }

    public void view_more_text(CalendarDB data) {
        ci.view_more_text(data);
    }

    public void isDataNull(String date) {
        ci.isDateNull(date);
    }

    @Override
    protected void onResume() {
        super.onResume();

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.main_menu:
                        changeFragment(Define.FRAGMENT_ID_MENU);
                        break;
                    case R.id.calendar_menu:
                        changeFragment(Define.FRAGMENT_ID_PROFILE);
                        break;
                    case R.id.message_menu:
                        changeFragment(Define.FRAGMENT_ID_LETTER_LIST);
                        break;
                    case R.id.emotion_menu:
                        changeFragment(Define.FRAGMENT_ID_EMOTION);
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