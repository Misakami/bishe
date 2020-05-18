package com.example.bishe.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;

import com.example.bishe.BaseActivity;
import com.example.bishe.R;
import com.example.bishe.ui.MainViewpagerAdapter;
import com.example.bishe.ui.course.Course;
import com.example.bishe.ui.search.Search;
import com.example.bishe.ui.user.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.lang.reflect.Array;
import java.util.Arrays;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        RxPermissions rxPermissions = new RxPermissions(this);
        Disposable subscribe = rxPermissions.request(Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (!aBoolean) {
                            finish();
                        }
                    }
                });
    }

    private void initView() {
        BottomNavigationView navView = findViewById(R.id.nav_view);
        final ViewPager viewPager = findViewById(R.id.main_pager);
        FragmentManager manager = getSupportFragmentManager();
        MainViewpagerAdapter adapter =new MainViewpagerAdapter(manager, Arrays.asList(new Course(),new Search(),new User()));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_course:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.navigation_search:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.navigation_login:
                        viewPager.setCurrentItem(2);
                        break;
                    default:
                }
                return true;
            }
        });
    }
}
