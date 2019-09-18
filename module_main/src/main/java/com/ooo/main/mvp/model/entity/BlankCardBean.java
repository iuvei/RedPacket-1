package com.ooo.main.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author lanjian
 * creat at 2019/9/6
 * description 银行卡实体类
 */
public class BlankCardBean implements Serializable {


    /**
     * status : 1
     * result : [{"id":"4","uid":"969999","cardname":"12","cardcode":"6223282800078930116","cardopen":"中国工商银行","cardaddress":"JOJO","title":"中国工商银行(0116)","type":"0"}]
     * msg :
     */

    private int status;
    private String msg;
    private List <ResultBean> result;

    /**
     * 对字符加星号处理：除前面几位和后面几位外，其他的字符以星号代替
     *
     * @param content
     *            传入的字符串
     * @param frontNum
     *            保留前面字符的位数
     * @param endNum
     *            保留后面字符的位数
     * @return 带星号的字符串
     */

    public static String getStarString2(String content, int frontNum, int endNum) {

        if (frontNum >= content.length() || frontNum < 0) {
            return content;
        }
        if (endNum >= content.length() || endNum < 0) {
            return content;
        }
        if (frontNum + endNum >= content.length()) {
            return content;
        }
        String starStr = "";
        for (int i = 0; i < (content.length() - frontNum - endNum); i++) {
            starStr = starStr + "*";
        }
        return content.substring(0, frontNum) + starStr
                + content.substring(content.length() - endNum, content.length());

    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List <ResultBean> getResult() {
        return result;
    }

    public void setResult(List <ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 4
         * uid : 969999
         * cardname : 12
         * cardcode : 6223282800078930116
         * cardopen : 中国工商银行
         * cardaddress : JOJO
         * title : 中国工商银行(0116)
         * type : 0
         */

        private String id;
        private String uid;
        private String cardname;
        private String cardcode;
        private String cardopen;
        private String cardaddress;
        private String title;
        private String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getCardname() {
            return cardname;
        }

        public void setCardname(String cardname) {
            this.cardname = cardname;
        }

        public String getCardcodeSecret() {
            return getStarString2(cardcode,4,4);
        }

        public String getCardcode() {
            return cardcode;
        }

        public void setCardcode(String cardcode) {
            this.cardcode = cardcode;
        }

        public String getCardopen() {
            return cardopen;
        }

        public void setCardopen(String cardopen) {
            this.cardopen = cardopen;
        }

        public String getCardaddress() {
            return cardaddress;
        }

        public void setCardaddress(String cardaddress) {
            this.cardaddress = cardaddress;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
