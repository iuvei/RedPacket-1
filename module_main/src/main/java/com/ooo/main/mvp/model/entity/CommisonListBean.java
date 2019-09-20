package com.ooo.main.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author lanjian
 * creat at 2019/9/20
 * description
 * 佣金列表实体类
 */
public class CommisonListBean implements Serializable {

    /**
     * status : 1
     * result : {"total":"2","list":[{"id":"145","uniacid":"0","uid":"969999","setuid":"969999","gold":"10.00","level":"0","goldid":null,"type":"2","welfnums":"3","addtime":"1970-01-01 08:00"},{"id":"147","uniacid":"0","uid":"969999","setuid":"969999","gold":"5.00","level":"0","goldid":null,"type":"3","welfnums":"5","addtime":"1970-01-01 08:00"}],"pagesize":20,"beginThisweek":1568563200,"dayprofit":null}
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
         * total : 2
         * list : [{"id":"145","uniacid":"0","uid":"969999","setuid":"969999","gold":"10.00","level":"0","goldid":null,"type":"2","welfnums":"3","addtime":"1970-01-01 08:00"},{"id":"147","uniacid":"0","uid":"969999","setuid":"969999","gold":"5.00","level":"0","goldid":null,"type":"3","welfnums":"5","addtime":"1970-01-01 08:00"}]
         * pagesize : 20
         * beginThisweek : 1568563200
         * dayprofit : null
         */

        private String total;
        private int pagesize;
        private int beginThisweek;
        private String dayprofit;
        private List <ListBean> list;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public int getPagesize() {
            return pagesize;
        }

        public void setPagesize(int pagesize) {
            this.pagesize = pagesize;
        }

        public int getBeginThisweek() {
            return beginThisweek;
        }

        public void setBeginThisweek(int beginThisweek) {
            this.beginThisweek = beginThisweek;
        }

        public String getDayprofit() {
            return dayprofit;
        }

        public void setDayprofit(String dayprofit) {
            this.dayprofit = dayprofit;
        }

        public List <ListBean> getList() {
            return list;
        }

        public void setList(List <ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 145
             * uniacid : 0
             * uid : 969999
             * setuid : 969999
             * gold : 10.00
             * level : 0
             * goldid : null
             * type : 2
             * welfnums : 3
             * addtime : 1970-01-01 08:00
             */

            private String id;
            private String uniacid;
            private String uid;
            private String setuid;
            private String gold;
            private String level;
            private Object goldid;
            private String type;
            private String welfnums;
            private String addtime;

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

            public String getSetuid() {
                return setuid;
            }

            public void setSetuid(String setuid) {
                this.setuid = setuid;
            }

            public String getGold() {
                return gold;
            }

            public void setGold(String gold) {
                this.gold = gold;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public Object getGoldid() {
                return goldid;
            }

            public void setGoldid(Object goldid) {
                this.goldid = goldid;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getWelfnums() {
                return welfnums;
            }

            public void setWelfnums(String welfnums) {
                this.welfnums = welfnums;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }
        }
    }
}
