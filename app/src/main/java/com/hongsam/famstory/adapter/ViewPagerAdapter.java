package com.hongsam.famstory.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> pager = new ArrayList<>();
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return pager.get(position);
    }

    @Override
    public int getCount() {
        return pager.size();
    }

    public void addItem(Fragment fr){
        pager.add(fr);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                // 타임라인
                return "타임라인";
            case 1:
                // 일정
                return "일정";
            default:
                return null;
        }
    }
}
