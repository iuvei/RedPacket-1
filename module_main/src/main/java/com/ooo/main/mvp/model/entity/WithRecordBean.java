package com.ooo.main.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author lanjian
 * creat at 2019/9/17
 * description
 * 提现记录
 */
public class WithRecordBean implements Serializable {


    /**
     * status : 1
     * result : {"list":[{"id":"18","uniacid":"1","uid":"969999","logno":"TX15687867143560","gold":"100","sergold":"0.00","reagold":"0.00","cardname":"5555","cardcode":"6223282800078930116","cardopen":"中国邮政储蓄银行","addtime":"2019-09-18 14:05:14","status":"0","pushtime":null,"details":null},{"id":"17","uniacid":"1","uid":"969999","logno":"TX15687865587996","gold":"100","sergold":"0.00","reagold":"0.00","cardname":"5555","cardcode":"6223282800078930116","cardopen":"中国邮政储蓄银行","addtime":"2019-09-18 14:02:38","status":"0","pushtime":null,"details":null},{"id":"16","uniacid":"1","uid":"969999","logno":"TX15687865274060","gold":"100","sergold":"0.00","reagold":"0.00","cardname":"5555","cardcode":"6223282800078930116","cardopen":"中国邮政储蓄银行","addtime":"2019-09-18 14:02:07","status":"0","pushtime":null,"details":null}],"paytype":["申请提现","提现成功","提现失败"],"allmoney":300}
     * msg :
     */

    private int status;
    private ResultBean result;
    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class ResultBean implements Serializable{
        /**
         * list : [{"id":"18","uniacid":"1","uid":"969999","logno":"TX15687867143560","gold":"100","sergold":"0.00","reagold":"0.00","cardname":"5555","cardcode":"6223282800078930116","cardopen":"中国邮政储蓄银行","addtime":"2019-09-18 14:05:14","status":"0","pushtime":null,"details":null},{"id":"17","uniacid":"1","uid":"969999","logno":"TX15687865587996","gold":"100","sergold":"0.00","reagold":"0.00","cardname":"5555","cardcode":"6223282800078930116","cardopen":"中国邮政储蓄银行","addtime":"2019-09-18 14:02:38","status":"0","pushtime":null,"details":null},{"id":"16","uniacid":"1","uid":"969999","logno":"TX15687865274060","gold":"100","sergold":"0.00","reagold":"0.00","cardname":"5555","cardcode":"6223282800078930116","cardopen":"中国邮政储蓄银行","addtime":"2019-09-18 14:02:07","status":"0","pushtime":null,"details":null}]
         * paytype : ["申请提现","提现成功","提现失败"]
         * allmoney : 300
         */

        private int allmoney;
        private List <ListBean> list;
        private List <String> paytype;

        public int getAllmoney() {
            return allmoney;
        }

        public void setAllmoney(int allmoney) {
            this.allmoney = allmoney;
        }

        public List <ListBean> getList() {
            return list;
        }

        public void setList(List <ListBean> list) {
            this.list = list;
        }

        public List <String> getPaytype() {
            return paytype;
        }

        public void setPaytype(List <String> paytype) {
            this.paytype = paytype;
        }

        public static class ListBean implements Serializable{
            /**
             * id : 18
             * uniacid : 1
             * uid : 969999
             * logno : TX15687867143560
             * gold : 100      提现金额
             * sergold : 0.00  手续费
             * reagold : 0.00  到账金额
             * cardname : 5555 持卡人名字
             * cardcode : 6223282800078930116  银行卡账号
             * cardopen : 中国邮政储蓄银行
             * addtime : 2019-09-18 14:05:14   提现时间
             * status : 0
             * pushtime : null
             * details : null
             */

            private String id;
            private String uniacid;
            private String uid;
            private String logno;
            private String gold;
            private String sergold;
            private String reagold;
            private String cardname;
            private String cardcode;
            private String cardopen;
            private String addtime;
            private String status;
            private Object pushtime;
            private Object details;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUniacid() {
                return uniacid;
            }

            public void setUniacid(String uniacid) {
                this.uniacid = uniacid;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getLogno() {
                return logno;
            }

            public void setLogno(String logno) {
                this.logno = logno;
            }

            public String getGold() {
                return gold;
            }

            public void setGold(String gold) {
                this.gold = gold;
            }

            public String getSergold() {
                return sergold;
            }

            public void setSergold(String sergold) {
                this.sergold = sergold;
            }

            public String getReagold() {
                return reagold;
            }

            public void setReagold(String reagold) {
                this.reagold = reagold;
            }

            public String getCardname() {
                return cardname;
            }

            public void setCardname(String cardname) {
                this.cardname = cardname;
            }

            public String getCardcode() {
                return cardcode;
            }

            public String getCardCodeScreat(){
                return BlankCardBean.getStarString2 ( cardcode,4,4 );
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

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getStatus() {
                return status;
            }

            public String getStatusValue(){
                if (status.equals ( "0" )){
                    return "未审核";
                }else if (status.equals ( "1" )){
                    return "审核通过";
                }else if (status.equals ( "2" )){
                    return "审核拒绝";
                }
                return "";
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public Object getPushtime() {
                return pushtime;
            }

            public void setPushtime(Object pushtime) {
                this.pushtime = pushtime;
            }

            public Object getDetails() {
                return details;
            }

            public void setDetails(Object details) {
                this.details = details;
            }
        }
    }
}
