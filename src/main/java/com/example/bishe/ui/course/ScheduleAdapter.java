package com.example.bishe.ui.course;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bishe.R;
import com.example.bishe.Util.CalendarProviderUtil;
import com.example.bishe.Util.DateTools;
import com.example.bishe.model.bean.CourseBean;
import com.example.bishe.model.bean.Curriculum;
import com.example.bishe.widget.CustomDialog;
import com.example.bishe.widget.LoadingDialog;
import com.example.bishe.widget.TeacherDialog;

import java.util.HashMap;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private RecyclerView recyclerView;
    private List<Curriculum> curricula;
    private int weak;
    private String startTime;

    public ScheduleAdapter(RecyclerView recyclerView, List<Curriculum> curricula,int weak,String startTime){
        this.recyclerView = recyclerView;
        this.curricula = curricula;
        this.weak = weak;
        this.startTime = startTime;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null){
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_course,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder){
            ((ViewHolder) holder).onBindview(position);
        }
    }

    @Override
    public int getItemCount() {
        return 42;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView title;
        private TextView content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.course_cardView);
            title = itemView.findViewById(R.id.course_title);
            content = itemView.findViewById(R.id.course_content);
        }

        public void onBindview(int position) {
            cardView.setVisibility(View.INVISIBLE);
            final int day = position % 7;
            int section = position/7 * 2;
            for (int i = 0; i < curricula.size() ; i++) {
                final Curriculum curriculum = curricula.get(i);
                if (curriculum.getDay() == day && curriculum.getSection()[section] == 1){
                    cardView.setVisibility(View.VISIBLE);
                    title.setText(curriculum.getTitle());
                    content.setText(curriculum.getContent());
                    cardView.setClickable(true);
                    cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CustomDialog dialog = new CustomDialog(context);
                            dialog.setTitle(curriculum.getTitle())
                                    .setMessage(curriculum.getContent()).show();
                        }
                    });
                    if (weak != -1){
                        cardView.setOnLongClickListener(new View.OnLongClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public boolean onLongClick(View v) {
                                final String weakTime = DateTools.getWeakTime(startTime, 7 * weak + day);
                                TeacherDialog teacherDialog = new TeacherDialog(context);
                                teacherDialog.setMessage("是否把这次事件添加日历提醒");
                                teacherDialog.setListener(new TeacherDialog.OnClickListener() {
                                    @Override
                                    public void onConfirmClick() {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                            CalendarProviderUtil.addCourse(context, curriculum, weakTime);
                                        }
                                    }
                                });
                                teacherDialog.show();
                                return true;
                            }
                        });
                    }
                }
            }
        }
    }
}
