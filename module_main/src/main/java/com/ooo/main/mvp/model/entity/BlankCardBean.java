package com.ooo.main.mvp.model.entity;

/**
 * @author lanjian
 * creat at 2019/9/6
 * description 银行卡实体类
 */
public class BlankCardBean {
    private int blankType;
    private int blankCardType;
    private String blankCardNum;
    private String blankName;

    public int getBlankType() {
        return blankType;
    }

    public void setBlankType(int blankType) {
        this.blankType = blankType;
    }

    public int getBlankCardType() {
        return blankCardType;
    }

    public void setBlankCardType(int blankCardType) {
        this.blankCardType = blankCardType;
    }

    public String getBlankCardNum() {
        return getStarString2(blankCardNum,4,4);
    }

    public void setBlankCardNum(String blankCardNum) {
        this.blankCardNum = blankCardNum;
    }

    public String getBlankName() {
        return blankName;
    }

    public void setBlankName(String blankName) {
        this.blankName = blankName;
    }

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

    private String getStarString2(String content, int frontNum, int endNum) {

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
}
