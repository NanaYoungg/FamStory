package com.hongsam.famstrory.activitie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toolbar;

import com.hongsam.famstrory.define.Define;
import com.hongsam.famstrory.R;
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

public class MainActivity extends AppCompatActivity{

    final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeFragment(Define.FRAGMENT_ID_LETTER_LIST);

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

}