package com.example.bishe.ui.search;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bishe.R;
import com.example.bishe.model.SearchTeacher;
import com.example.bishe.model.bean.Teacher;
import com.example.bishe.widget.LoadingDialog;

import java.lang.reflect.Field;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class Search extends Fragment {
    Teacher teacher1;
    SearchTeacherAdapter adapter;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_search, container, false);
        final SearchView searchView = inflate.findViewById(R.id.view_search);
        setUnderLinetransparent(searchView);
        TextView textView = inflate.findViewById(R.id.search_src_text);
        final TextView search = inflate.findViewById(R.id.button_text);
        textView.setBackground(getResources().getDrawable(R.drawable.usericon_bg_circle));

        teacher1 = new Teacher();

        recyclerView = inflate.findViewById(R.id.fragment_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new SearchTeacherAdapter(teacher1.getReturnData(),recyclerView);
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e(TAG, "onQueryTextSubmit: " + query);
                search(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchView.getQuery()!= null){
                    search(searchView.getQuery().toString());
                }
            }
        });
        return inflate;
    }

    public void search(String query){
        final LoadingDialog dialog = new LoadingDialog(getContext());
        dialog.show();
        SearchTeacher.search(query,new Callback(){
            @Override
            public void success(Teacher teacher) {
                teacher1 = teacher;
                Log.e(TAG, "search: "+teacher);
                adapter.changeList(teacher.getReturnData());
                dialog.dismiss();
            }
        });
    }

    private void setUnderLinetransparent(SearchView searchView){
        try {
            Class<?> argClass = searchView.getClass();
            // mSearchPlate是SearchView父布局的名字
            Field ownField = argClass.getDeclaredField("mSearchPlate");
            ownField.setAccessible(true);
            View mView = (View) ownField.get(searchView);
            mView.setBackgroundColor(Color.TRANSPARENT);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public interface Callback {
        void success(Teacher teacher);
    }
}
