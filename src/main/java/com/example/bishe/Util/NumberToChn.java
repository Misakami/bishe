package com.example.bishe.Util;

import android.util.Log;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class NumberToChn {
    static String CHN_NUMBER[] = {" ", "一", "二", "三", "四", "五", "六", "七", "八", "九"};

    public static String inttoCHn(int i) {
        Log.e(TAG, "inttoCHn: " + i);
        if (i / 10 == 0) {
            return CHN_NUMBER[i];
        } else {
            int j = i / 10;
            return j == 1 ?"十" + CHN_NUMBER[i % 10]
                    :CHN_NUMBER[j] + "十" + CHN_NUMBER[i % 10];
        }
    }
}
