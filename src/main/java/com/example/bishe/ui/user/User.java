package com.example.bishe.ui.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bishe.App;
import com.example.bishe.R;
import com.example.bishe.model.SearchTeacher;
import com.example.bishe.model.bean.EventBean;
import com.example.bishe.widget.TeacherDialog;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class User extends Fragment {
    private TextView userName;
    private CardView user_exit;
    private RecyclerView rv_event;
    private TextView event;
    private String teaId;
    private String teajysm;
    private EventAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        user_exit = view.findViewById(R.id.user_exit);
        userName = view.findViewById(R.id.userName);
        rv_event = view.findViewById(R.id.rv_event);
        event = view.findViewById(R.id.event);
        adapter = new EventAdapter(rv_event);
        rv_event.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_event.setAdapter(adapter);
        user_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TeacherDialog(getContext())
                        .setListener(new TeacherDialog.OnClickListener() {
                            @Override
                            public void onConfirmClick() {
                                SharedPreferences sp= App.getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
                                SharedPreferences.Editor edit = sp.edit();
                                edit.remove("teaId");
                                edit.remove("teaName");
                                edit.remove("teaJysm");
                                edit.apply();
                                adapter.romveList();
                                initview();
                            }
                        })
                        .setMessage("是否退出登录")
                        .show();
            }
        });
        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (teaId == null){
                    Toast.makeText(getContext(),"请先登录",Toast.LENGTH_SHORT).show();
                }else {
                    Log.e(TAG, "onClick: " + teaId+teajysm );
                    SearchTeacher.searchEvent(teaId, new Callback() {
                        @Override
                        public void success(EventBean bean) {
                            adapter.romveList();
                            adapter.changeList(bean.getReturnData());
                            adapter.size = bean.getReturnData().size();
                            SearchTeacher.searchEvent(teajysm, new Callback() {
                                @Override
                                public void success(EventBean bean) {
                                    adapter.changeList(bean.getReturnData());
                                }
                            });
                        }
                    });
                }
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initview();
    }

    @SuppressLint("SetTextI18n")
    private void initview() {
        SharedPreferences sharedPreferences= App.getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        teaId = sharedPreferences.getString("teaId", null);
        teajysm = sharedPreferences.getString("teaJysm",null);
        if (teaId == null){
            userName.setText("请先搜索登录");
            return;
        }
        String string = sharedPreferences.getString("teaName", "用户名丢失在次元边界了");
        userName.setText("当前登录： "+string);
        adapter.romveList();
    }

    @Override
    public void onResume() {
        super.onResume();
        initview();
    }

    public interface Callback{
        void success(EventBean bean);
    }
}
