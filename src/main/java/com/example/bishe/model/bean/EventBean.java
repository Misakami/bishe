package com.example.bishe.model.bean;

import java.util.List;

public class EventBean {

    /**
     * code : 0
     * teaId : 030201
     * returnData : [{"starttime":"","endtime":"","title":"","content":""},{"starttime":"","endtime":"","title":"","content":""},{"starttime":"","endtime":"","title":"","content":""}]
     */

    private int code;
    private String teaId;
    private List<ReturnDataBean> returnData;

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

    public List<ReturnDataBean> getReturnData() {
        return returnData;
    }

    public void setReturnData(List<ReturnDataBean> returnData) {
        this.returnData = returnData;
    }

    public static class ReturnDataBean {
        /**
         * starttime :
         * endtime :
         * title :
         * content :
         */

        private String starttime;
        private String endtime;
        private String title;
        private String content;

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
