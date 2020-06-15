package com.example.bishe.ui.course;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.bishe.App;
import com.example.bishe.R;
import com.example.bishe.Util.DateTools;
import com.example.bishe.Util.NumberToChn;
import com.example.bishe.model.bean.CourseBean;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Course extends Fragment{
    private TextView title;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private CourseBean course = null;
    private ImageView image;
    private CourseViewpagerAdapter courseViewpagerAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course, container, false);
        title = view.findViewById(R.id.course_title);
        tabLayout = view.findViewById(R.id.week_tab);
        viewPager = view.findViewById(R.id.course_pager);
        image = view.findViewById(R.id.image);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tabLayout.getVisibility() == View.VISIBLE)
                tabLayout.setVisibility(View.GONE);
                else tabLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initImage() {
        image.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.GONE);
        viewPager.setVisibility(View.INVISIBLE);
    }

    private void initCourse() {
        Log.e(TAG, "initCourse: " );
        image.setVisibility(View.INVISIBLE);
        tabLayout.setVisibility(View.GONE);
        viewPager.setVisibility(View.VISIBLE);
        List<Schedule> schedules = new ArrayList<>();
        for (int i=0 ; i < Integer.parseInt(course.getLastWeak()) ;i ++){
            schedules.add(Schedule.getInstance(course,i-1));
        }
        String[] tab = new String[Integer.parseInt(course.getLastWeak())+1];
        String startTime = course.getStartTime();
        tab[0] = "整学期";
        for (int i = 1 ; i < tab.length ; i++){
            tab[i] = "第"+ NumberToChn.inttoCHn(i) +"周";
        }
        int weak = DateTools.getWeak(startTime);
        if (weak != -1){
            tab[weak+1] = "本周";
        }
        if (viewPager.getAdapter() == null) {
            FragmentManager manager = getChildFragmentManager();
            courseViewpagerAdapter = new CourseViewpagerAdapter(manager);
            courseViewpagerAdapter.setFragments(schedules);
            courseViewpagerAdapter.setTabNames(tab);
            viewPager.setAdapter(courseViewpagerAdapter);
        }
        courseViewpagerAdapter.setFragments(schedules);
        courseViewpagerAdapter.setTabNames(tab);
        courseViewpagerAdapter.notifyDataSetChanged();
        tabLayout.setupWithViewPager(viewPager);
        if (weak != -1){
            viewPager.setCurrentItem(weak+1,false);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (course != null) {
            String teaID = course.getTeaId();
            if (getCourse()){
                if (teaID.equals(course.getTeaId())){
                    image.setVisibility(View.INVISIBLE);
                    viewPager.setVisibility(View.VISIBLE);
                    return;
                }
            }
        }
        if (getCourse()){
            initCourse();
            return;
        }
        initImage();
    }

    public boolean getCourse(){
        SharedPreferences sharedPreferences= App.getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        String teaId=sharedPreferences.getString("teaId",null);
        Log.e(TAG, "getCourse: " +teaId );
        if (teaId == null){
            return false;
        }
        String course = sharedPreferences.getString(teaId, null);
        if (course == null){
            return false;
        }
        Gson gson = new Gson();
        this.course = gson.fromJson(course, CourseBean.class);
        int weak = DateTools.getWeak(this.course.getStartTime());
        if (weak >= Integer.parseInt(this.course.getLastWeak())){
            SharedPreferences sp= App.getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.remove("teaId");
            edit.remove("teaName");
            edit.remove(teaId);
            edit.apply();
            return false;
        }
        return true;
    }
}
