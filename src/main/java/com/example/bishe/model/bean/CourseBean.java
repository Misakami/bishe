package com.example.bishe.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CourseBean implements Parcelable {

    /**
     * code : 0
     * teaId : 020421
     * startTime : 2020-02-17
     * lastWeak : 20
     * returnData : [{"classification":"理论","courseName":"A2020750-硬件描述语言","classX":"A02192A2020750002","category":"必修","classMajor":"161118|01,02 集成电路工程类","teacher":"王明耀","time":"星期1第3-4节 1-16周","place":"4314"},{"classification":"理论","courseName":"A2020750-硬件描述语言","classX":"A02192A2020750003","category":"必修","classMajor":"161118|03,04 集成电路工程类","teacher":"王明耀","time":"星期3第1-2节 1-16周","place":"4502"},{"classification":"实验实践","courseName":"A2020750-硬件描述语言","classX":"SK02192A2020750001","category":"必修","classMajor":"161118|01,02 集成电路工程类","teacher":"王明耀","time":"星期2第3-4节 4-11周","place":"计算机制图教室（三）(综合实验楼C401/C402)"},{"classification":"实验实践","courseName":"A2020750-硬件描述语言","classX":"SK02192A2020750002","category":"必修","classMajor":"161118|03,04 集成电路工程类","teacher":"王明耀","time":"星期1第5-6节 4-11周","place":"计算机制图教室（三）(综合实验楼C401/C402)"}]
     */

    private int code;
    private String teaId;
    private String startTime;
    private String lastWeak;
    private List<ReturnDataBean> returnData;

    protected CourseBean(Parcel in) {
        code = in.readInt();
        teaId = in.readString();
        startTime = in.readString();
        lastWeak = in.readString();
        returnData = in.createTypedArrayList(ReturnDataBean.CREATOR);
    }

    public static final Creator<CourseBean> CREATOR = new Creator<CourseBean>() {
        @Override
        public CourseBean createFromParcel(Parcel in) {
            return new CourseBean(in);
        }

        @Override
        public CourseBean[] newArray(int size) {
            return new CourseBean[size];
        }
    };

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTeaId() {
        return teaId;
    }

    public void setTeaId(String teaId) {
        this.teaId = teaId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getLastWeak() {
        return lastWeak;
    }

    public void setLastWeak(String lastWeak) {
        this.lastWeak = lastWeak;
    }

    public List<ReturnDataBean> getReturnData() {
        return returnData;
    }

    public void setReturnData(List<ReturnDataBean> returnData) {
        this.returnData = returnData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(teaId);
        dest.writeString(startTime);
        dest.writeString(lastWeak);
        dest.writeTypedList(returnData);
    }


    public static class ReturnDataBean implements Parcelable {
        /**
         * classification : 理论
         * courseName : A2020750-硬件描述语言
         * classX : A02192A2020750002
         * category : 必修
         * classMajor : 161118|01,02 集成电路工程类
         * teacher : 王明耀
         * time : 星期1第3-4节 1-16周
         * place : 4314
         */

        private String classification;
        private String courseName;
        private String classX;
        private String category;
        private String classMajor;
        private String teacher;
        private String time;
        private String place;

        protected ReturnDataBean(Parcel in) {
            classification = in.readString();
            courseName = in.readString();
            classX = in.readString();
            category = in.readString();
            classMajor = in.readString();
            teacher = in.readString();
            time = in.readString();
            place = in.readString();
        }

        public static final Creator<ReturnDataBean> CREATOR = new Creator<ReturnDataBean>() {
            @Override
            public ReturnDataBean createFromParcel(Parcel in) {
                return new ReturnDataBean(in);
            }

            @Override
            public ReturnDataBean[] newArray(int size) {
                return new ReturnDataBean[size];
            }
        };

        public String getClassification() {
            return classification;
        }

        public void setClassification(String classification) {
            this.classification = classification;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getClassX() {
            return classX;
        }

        public void setClassX(String classX) {
            this.classX = classX;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getClassMajor() {
            return classMajor;
        }

        public void setClassMajor(String classMajor) {
            this.classMajor = classMajor;
        }

        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(classification);
            dest.writeString(courseName);
            dest.writeString(classX);
            dest.writeString(category);
            dest.writeString(classMajor);
            dest.writeString(teacher);
            dest.writeString(time);
            dest.writeString(place);
        }
    }
}
