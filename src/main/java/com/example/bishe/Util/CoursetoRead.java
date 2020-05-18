package com.example.bishe.Util;

import com.example.bishe.model.bean.CourseBean;
import com.example.bishe.model.bean.Curriculum;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CoursetoRead {
    public static Curriculum transform(CourseBean.ReturnDataBean bean){
        Curriculum curriculum = new Curriculum();
        curriculum.setTitle(bean.getCourseName());
        String content = bean.getClassX()+"\n"+bean.getClassification()+"\n"+bean.getPlace()+"\n"+bean.getTime()+"\n"+bean.getTeacher()+"\n"+bean.getClassMajor()+"\n"+bean.getCategory();
        curriculum.setContent(content);
        String time = bean.getTime();
        int[] sections = new int[12]; //第几节有课
        int[] weaks = new int[30]; //第几周有课
        String pattern = "星期(.*)第(.*)节(.*)周";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(time);
        if (matcher.matches()){
            String day = matcher.group(1).trim();
            String section = matcher.group(2).trim();
            String[] split1 = section.split("-");
            for (int i = Integer.parseInt(split1[0])-1; i < Integer.parseInt(split1[split1.length-1]) ; i++) {
                sections[i] = 1;
            }
            String weak = matcher.group(3).trim();
            weak = weak.replace("周","");
            String[] split = weak.split(",");
            for (int i = 0; i < split.length; i++) {
                String[] split2 = split[i].split("-");
                String s = split2[split2.length - 1];
                boolean singular = s.contains("单");
                boolean dual = s.contains("双");
                String substring = s;
                if (singular || dual){
                    substring = s.substring(0, s.length() - 1);
                }
                for (int j =  Integer.parseInt(split2[0])-1; j < Integer.parseInt(substring) ; j++) {
                    weaks[j] = 1;
                    if (singular && (j+1)%2 == 0) weaks[j] = 0;
                    if (dual && (j+1)%2 != 0) weaks[j] = 0;
                }
            }
            curriculum.setDay(Integer.parseInt(day));
            curriculum.setSection(sections);
            curriculum.setWeak(weaks);
        }
        return curriculum;
    }
}
