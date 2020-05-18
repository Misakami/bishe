package com.example.bishe.ui.course;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.bishe.Util.DateTools;

import java.util.List;

public class CourseViewpagerAdapter extends FragmentStatePagerAdapter {
    private List<Schedule> fragments;
    private String[] tabNames = new String[20];

    public CourseViewpagerAdapter(FragmentManager fm){
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    public void setFragments(List<Schedule> fragments) {
        this.fragments = fragments;
    }

    public void setTabNames(String[] tabNames) {
        this.tabNames = tabNames;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames[position];
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
