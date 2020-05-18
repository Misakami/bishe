package com.example.bishe.model.bean;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Teacher {


    /**
     * code : 0
     * info : ok
     * returnData : [{"teaId":"040713","teaName":"夏书银","xb":"男","jysm":"智能科学与技术系","yxm":"计算机科学与技术学院","zc":"讲师（高校）"}]
     */

    private int code;
    private String info;
    private List<ReturnDataBean> returnData = new ArrayList<>();

    @Override
    public String toString() {
        return "Teacher{" +
                "code=" + code +
                ", info='" + info + '\'' +
                ", returnData=" + returnData +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<ReturnDataBean> getReturnData() {
        return returnData;
    }

    public void setReturnData(List<ReturnDataBean> returnData) {
        this.returnData = returnData;
    }

    public static class ReturnDataBean {
        /**
         * teaId : 040713
         * teaName : 夏书银
         * xb : 男
         * jysm : 智能科学与技术系
         * yxm : 计算机科学与技术学院
         * zc : 讲师（高校）
         */

        private String teaId;
        private String teaName;
        private String xb;
        private String jysm;
        private String yxm;
        private String zc;

        @Override
        public String toString() {
            return "ReturnDataBean{" +
                    "teaId='" + teaId + '\'' +
                    ", teaName='" + teaName + '\'' +
                    ", xb='" + xb + '\'' +
                    ", jysm='" + jysm + '\'' +
                    ", yxm='" + yxm + '\'' +
                    ", zc='" + zc + '\'' +
                    '}';
        }

        public String getTeaId() {
            return teaId;
        }

        public void setTeaId(String teaId) {
            this.teaId = teaId;
        }

        public String getTeaName() {
            return teaName;
        }

        public void setTeaName(String teaName) {
            this.teaName = teaName;
        }

        public String getXb() {
            return xb;
        }

        public void setXb(String xb) {
            this.xb = xb;
        }

        public String getJysm() {
            return jysm;
        }

        public void setJysm(String jysm) {
            this.jysm = jysm;
        }

        public String getYxm() {
            return yxm;
        }

        public void setYxm(String yxm) {
            this.yxm = yxm;
        }

        public String getZc() {
            return zc;
        }

        public void setZc(String zc) {
            this.zc = zc;
        }
    }
}
