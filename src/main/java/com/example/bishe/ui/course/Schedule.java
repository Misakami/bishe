package com.example.bishe.ui.course;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bishe.R;
import com.example.bishe.Util.CoursetoRead;
import com.example.bishe.Util.DateTools;
import com.example.bishe.model.bean.CourseBean;
import com.example.bishe.model.bean.Curriculum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Schedule extends Fragment {
    private TextView month;
    private TextView weakOne;
    private TextView weakTwo;
    private TextView weakThree;
    private TextView weakFour;
    private TextView weakFive;
    private TextView weakSix;
    private TextView weakSeven;
    private TextView one;
    private TextView two;
    private TextView three;
    private TextView four;
    private TextView five;
    private TextView six;
    private TextView seven;
    private RecyclerView rev_schedule;
    private CourseBean courseBean;

    private Schedule(){
    }

    public static Schedule getInstance(CourseBean bean,int weak) {
        Bundle args = new Bundle();
        args.putInt("weak",weak);
        args.putParcelable("course",bean);
        Schedule fragment = new Schedule();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_course, container, false);
        month = view.findViewById(R.id.month);
        weakOne = view.findViewById(R.id.weak_one);
        weakTwo = view.findViewById(R.id.weak_two);
        weakThree = view.findViewById(R.id.weak_three);
        weakFour = view.findViewById(R.id.weak_four);
        weakFive = view.findViewById(R.id.weak_five);
        weakSix = view.findViewById(R.id.weak_six);
        weakSeven = view.findViewById(R.id.weak_seven);
        one = view.findViewById(R.id.one);
        two = view.findViewById(R.id.two);
        three = view.findViewById(R.id.three);
        four = view.findViewById(R.id.four);
        five = view.findViewById(R.id.five);
        six = view.findViewById(R.id.six);
        seven = view.findViewById(R.id.seven);
        rev_schedule = view.findViewById(R.id.rev_schedule);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = this.getArguments();
        assert arguments != null;
        int weak = arguments.getInt("weak",0);
        courseBean = arguments.getParcelable("course");
        assert courseBean != null;
        List<Curriculum> curricula = new ArrayList<>();
        for (int i = 0; i < courseBean.getReturnData().size(); i++) {
                CourseBean.ReturnDataBean returnDataBean = courseBean.getReturnData().get(i);
                Curriculum transform = CoursetoRead.transform(returnDataBean);
                if (weak == -1 ||transform.getWeak()[weak] == 1){
                    curricula.add(transform);
                }
        }
        ScheduleAdapter adapter = new ScheduleAdapter(rev_schedule,curricula,weak,courseBean.getStartTime());
        rev_schedule.setAdapter(adapter);
        rev_schedule.setLayoutManager(new GridLayoutManager(getContext(),7));
        initView(weak);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initView(int weak) {
        String startTime = courseBean.getStartTime();
        Log.e(TAG, "initView: " + courseBean.getTeaId());
        if (weak == -1) {
            return;
        }
        String[] day = new String[7];
        for (int i = 0; i < 7; i++) {
            String weakTime = DateTools.getWeakTime(startTime, 7 * weak + i);
            String[] split = weakTime.split("-");
            if (i == 0) month.setText(split[1] + "月");
            day[i] = split[2] +"日";
        }

        int nowWeak = DateTools.getWeak(startTime);
        if (weak == nowWeak){
            int i = DateTools.dateToWeek(DateTools.getNowTime()) +1;
            Log.e(TAG, "onViewCreated: " + i );
            List<TextView> textViews = Arrays.asList(one,two,three,four,five,six,seven);
            TextView textView = textViews.get(i);
            textView.setBackgroundResource(R.color.coloryellow);
        }
        weakOne.setText(day[0]);
        weakTwo.setText(day[1]);
        weakThree.setText(day[2]);
        weakFour.setText(day[3]);
        weakFive.setText(day[4]);
        weakSix.setText(day[5]);
        weakSeven.setText(day[6]);
    }
}
