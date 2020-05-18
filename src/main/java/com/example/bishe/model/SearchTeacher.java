package com.example.bishe.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.bishe.App;
import com.example.bishe.model.bean.CourseBean;
import com.example.bishe.model.bean.EventBean;
import com.example.bishe.model.bean.Teacher;
import com.example.bishe.ui.search.Search;
import com.example.bishe.ui.search.SearchTeacherAdapter;
import com.example.bishe.ui.user.User;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class SearchTeacher {
    public static void search(String name, final Search.Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://jwc.cqupt.edu.cn/data/json_teacherSearch.php?searchKey=" + name)
                .build();
        Call call = okHttpClient.newCall(request);
        Log.e(TAG, "search: ");
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.success(null);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                Teacher teacher = gson.fromJson(string, Teacher.class);
                System.out.println(teacher);
                Log.e(TAG, "onResponse: " + teacher );
                callback.success(teacher);
            }
        });
    }

    public static void searchEvent(String teaId , final User.Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://192.168.43.224:8080/getEvents?teaId=" + teaId)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Gson gson = new Gson();
                EventBean eventBean = gson.fromJson(s, EventBean.class);
                Log.e(TAG, "onResponse: " + s );
                if (eventBean != null)
                callback.success(eventBean);
            }
        });
    }

    public static void searchCourse(String teaid, final SearchTeacherAdapter.Callback callback){
        /*//测试用
        String json = "{\"code\":0,\"teaId\":\"020422\",\"startTime\":\"2020-02-17\",\"lastWeak\":\"20\",\"returnData\":[{\"classification\":\"理论\",\"courseName\":\"A2020750-硬件描述语言\",\"classX\":\"A02192A2020750002\",\"category\":\"必修\",\"classMajor\":\"161118|01,02 集成电路工程类\",\"teacher\":\"王明耀\",\"time\":\"星期1第3-4节 1-16周\",\"place\":\"4314\"},{\"classification\":\"理论\",\"courseName\":\"A2020750-硬件描述语言\",\"classX\":\"A02192A2020750003\",\"category\":\"必修\",\"classMajor\":\"161118|03,04 集成电路工程类\",\"teacher\":\"王明耀\",\"time\":\"星期3第1-2节 1-16周\",\"place\":\"4502\"},{\"classification\":\"实验实践\",\"courseName\":\"A2020750-硬件描述语言\",\"classX\":\"SK02192A2020750001\",\"category\":\"必修\",\"classMajor\":\"161118|01,02 集成电路工程类\",\"teacher\":\"王明耀\",\"time\":\"星期2第3-4节 4-11周\",\"place\":\"计算机制图教室（三）(综合实验楼C401/C402)\"},{\"classification\":\"实验实践\",\"courseName\":\"A2020750-硬件描述语言\",\"classX\":\"SK02192A2020750002\",\"category\":\"必修\",\"classMajor\":\"161118|03,04 集成电路工程类\",\"teacher\":\"王明耀\",\"time\":\"星期1第5-6节 4-11周\",\"place\":\"计算机制图教室（三）(综合实验楼C401/C402)\"}]}";
        Gson gson = new Gson();
        CourseBean courseBean = gson.fromJson(json, CourseBean.class);
        SharedPreferences sharedPreferences= App.getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("teaId", courseBean.getTeaId());
        editor.putString(courseBean.getTeaId(),json);
        editor.apply();
        callback.done("课表下载成功");*/

        //上线后再说
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://192.168.43.224:8080/course?teaId=" + teaid)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                CourseBean courseBean = gson.fromJson(string, CourseBean.class);
                SharedPreferences sharedPreferences= App.getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("teaId", courseBean.getTeaId());
                editor.putString(courseBean.getTeaId(),string);
                editor.apply();
                callback.done("课表下载成功");
            }
        });
    }

}
