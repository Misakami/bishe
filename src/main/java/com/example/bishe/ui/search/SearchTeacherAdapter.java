package com.example.bishe.ui.search;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bishe.App;
import com.example.bishe.R;
import com.example.bishe.model.SearchTeacher;
import com.example.bishe.model.bean.Teacher;
import com.example.bishe.widget.LoadingDialog;
import com.example.bishe.widget.TeacherDialog;

import java.util.List;

public class SearchTeacherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Teacher.ReturnDataBean> dataBeans;
    RecyclerView recyclerView;
    private Context context;

    public SearchTeacherAdapter(List<Teacher.ReturnDataBean> dataBeans,RecyclerView recyclerView){
        this.recyclerView = recyclerView;
        this.dataBeans = dataBeans;
    }

    public void changeList(List<Teacher.ReturnDataBean> dataBeans){
        this.dataBeans = dataBeans;
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_teacher,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.onbind(position);
    }

    @Override
    public int getItemCount() {
        return dataBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView teaId;
        TextView teaName;
        TextView zc;
        TextView jysm;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teaId = itemView.findViewById(R.id.teaId);
            teaName = itemView.findViewById(R.id.teaName);
            zc = itemView.findViewById(R.id.zc);
            jysm = itemView.findViewById(R.id.jysm);
        }

        public void onbind(int position){
            Teacher.ReturnDataBean returnDataBean = dataBeans.get(position);
            teaId.setText(returnDataBean.getTeaId());
            teaName.setText(returnDataBean.getTeaName());
            zc.setText(returnDataBean.getZc());
            jysm.setText(returnDataBean.getJysm());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TeacherDialog teacherDialog = new TeacherDialog(context);
                    teacherDialog.setListener(new TeacherDialog.OnClickListener() {
                        @Override
                        public void onConfirmClick() {
                            final LoadingDialog dialog = new LoadingDialog(context);
                            dialog.show();
                            SearchTeacher.searchCourse((String) teaId.getText(), new Callback() {
                                @Override
                                public void done(final String message) {
                                    recyclerView.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            SharedPreferences sharedPreferences= App.getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("teaName", String.valueOf(teaName.getText()));
                                            editor.putString("teaJysm",String.valueOf(jysm.getText()));
                                            editor.apply();
                                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                    });
                                }
                            });
                        }
                    });
                    teacherDialog.show();
                }
            });
        }
    }
    public interface Callback {
        void done(String message);
    }
}
