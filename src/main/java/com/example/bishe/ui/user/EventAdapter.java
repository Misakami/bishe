package com.example.bishe.ui.user;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bishe.R;
import com.example.bishe.Util.CalendarProviderUtil;
import com.example.bishe.model.bean.EventBean;
import com.example.bishe.widget.CustomDialog;
import com.example.bishe.widget.TeacherDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class EventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<EventBean.ReturnDataBean> rd;
    private RecyclerView recyclerView;
    public EventAdapter(RecyclerView recyclerView){
        this.recyclerView = recyclerView;
        rd = new ArrayList<>();
    }
    public void changeList(List<EventBean.ReturnDataBean> rd){
        this.rd = rd;
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null){
            context =parent.getContext();
        }
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false);
        return new ViewHolder(inflate) ;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return rd.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView startTime;
        private TextView endTime;
        private TextView event_title;
        private TextView event_content;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            startTime = itemView.findViewById(R.id.startTime);
            endTime = itemView.findViewById(R.id.endTime);
            event_title = itemView.findViewById(R.id.event_title);
            event_content = itemView.findViewById(R.id.event_content);
        }

        public void onBind(int position){
            final EventBean.ReturnDataBean returnDataBean = rd.get(position);
            startTime.setText(returnDataBean.getStarttime());
            endTime.setText(returnDataBean.getEndtime());
            event_content.setText(returnDataBean.getContent());
            event_title.setText(returnDataBean.getTitle());

            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomDialog dialog = new CustomDialog(context)
                            .setTitle(returnDataBean.getTitle())
                            .setMessage(returnDataBean.getContent());
                    dialog.show();
                }
            });
            itemView.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    TeacherDialog teacherDialog = new TeacherDialog(context);
                    teacherDialog.setMessage("是否把这次事件添加日历提醒");
                    teacherDialog.setListener(new TeacherDialog.OnClickListener() {
                        @Override
                        public void onConfirmClick() {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                CalendarProviderUtil.addEvent(context, returnDataBean);
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
