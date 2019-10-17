package com.ooo.main.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * creat at 2019/9/20
 * description
 * 佣金列表实体类
 */
public class CommisonListBean implements Serializable{


    /**
     * status : 1
     * result : {"total":"11","list":[{"id":"551","uniacid":"0","uid":"970021","setuid":"970021","gold":"2.55","level":"1","goldid":"188938","type":"发包返佣","welfnums":"0","addtime":"2019-10-17 18:17","nickname":"hsfggg"},{"id":"550","uniacid":"0","uid":"970021","setuid":"970021","gold":"1.19","level":"1","goldid":"188937","type":"发包返佣","welfnums":"0","addtime":"2019-10-17 18:14","nickname":"hsfggg"},{"id":"549","uniacid":"0","uid":"970021","setuid":"970021","gold":"2.27","level":"1","goldid":"188934","type":"发包返佣","welfnums":"0","addtime":"2019-10-17 17:52","nickname":"hsfggg"},{"id":"548","uniacid":"0","uid":"970021","setuid":"970021","gold":"1.88","level":"1","goldid":"188933","type":"发包返佣","welfnums":"0","addtime":"2019-10-17 17:50","nickname":"hsfggg"},{"id":"547","uniacid":"0","uid":"970021","setuid":"970021","gold":"2.66","level":"1","goldid":"188932","type":"发包返佣","welfnums":"0","addtime":"2019-10-17 17:45","nickname":"hsfggg"},{"id":"546","uniacid":"0","uid":"970021","setuid":"970021","gold":"1.49","level":"1","goldid":"188930","type":"发包返佣","welfnums":"0","addtime":"2019-10-17 17:39","nickname":"hsfggg"},{"id":"545","uniacid":"0","uid":"970021","setuid":"970021","gold":"0.87","level":"1","goldid":"188929","type":"发包返佣","welfnums":"0","addtime":"2019-10-17 17:38","nickname":"hsfggg"},{"id":"544","uniacid":"0","uid":"970021","setuid":"970021","gold":"1.81","level":"1","goldid":"188925","type":"发包返佣","welfnums":"0","addtime":"2019-10-17 17:20","nickname":"hsfggg"},{"id":"543","uniacid":"0","uid":"970021","setuid":"970021","gold":"1.86","level":"1","goldid":"188924","type":"发包返佣","welfnums":"0","addtime":"2019-10-17 17:19","nickname":"hsfggg"},{"id":"542","uniacid":"0","uid":"970021","setuid":"970021","gold":"1.19","level":"1","goldid":"188922","type":"发包返佣","welfnums":"0","addtime":"2019-10-17 17:16","nickname":"hsfggg"},{"id":"541","uniacid":"0","uid":"970021","setuid":"970021","gold":"2.11","level":"1","goldid":"188921","type":"发包返佣","welfnums":"0","addtime":"2019-10-17 17:15","nickname":"hsfggg"}],"pagesize":30,"beginThisweek":"19.88","dayprofit":"19.88"}
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

    public static class ResultBean implements Serializable {
        /**
         * total : 11
         * list : [{"id":"551","uniacid":"0","uid":"970021","setuid":"970021","gold":"2.55","level":"1","goldid":"188938","type":"发包返佣","welfnums":"0","addtime":"2019-10-17 18:17","nickname":"hsfggg"},{"id":"550","uniacid":"0","uid":"970021","setuid":"970021","gold":"1.19","level":"1","goldid":"188937","type":"发包返佣","welfnums":"0","addtime":"2019-10-17 18:14","nickname":"hsfggg"},{"id":"549","uniacid":"0","uid":"970021","setuid":"970021","gold":"2.27","level":"1","goldid":"188934","type":"发包返佣","welfnums":"0","addtime":"2019-10-17 17:52","nickname":"hsfggg"},{"id":"548","uniacid":"0","uid":"970021","setuid":"970021","gold":"1.88","level":"1","goldid":"188933","type":"发包返佣","welfnums":"0","addtime":"2019-10-17 17:50","nickname":"hsfggg"},{"id":"547","uniacid":"0","uid":"970021","setuid":"970021","gold":"2.66","level":"1","goldid":"188932","type":"发包返佣","welfnums":"0","addtime":"2019-10-17 17:45","nickname":"hsfggg"},{"id":"546","uniacid":"0","uid":"970021","setuid":"970021","gold":"1.49","level":"1","goldid":"188930","type":"发包返佣","welfnums":"0","addtime":"2019-10-17 17:39","nickname":"hsfggg"},{"id":"545","uniacid":"0","uid":"970021","setuid":"970021","gold":"0.87","level":"1","goldid":"188929","type":"发包返佣","welfnums":"0","addtime":"2019-10-17 17:38","nickname":"hsfggg"},{"id":"544","uniacid":"0","uid":"970021","setuid":"970021","gold":"1.81","level":"1","goldid":"188925","type":"发包返佣","welfnums":"0","addtime":"2019-10-17 17:20","nickname":"hsfggg"},{"id":"543","uniacid":"0","uid":"970021","setuid":"970021","gold":"1.86","level":"1","goldid":"188924","type":"发包返佣","welfnums":"0","addtime":"2019-10-17 17:19","nickname":"hsfggg"},{"id":"542","uniacid":"0","uid":"970021","setuid":"970021","gold":"1.19","level":"1","goldid":"188922","type":"发包返佣","welfnums":"0","addtime":"2019-10-17 17:16","nickname":"hsfggg"},{"id":"541","uniacid":"0","uid":"970021","setuid":"970021","gold":"2.11","level":"1","goldid":"188921","type":"发包返佣","welfnums":"0","addtime":"2019-10-17 17:15","nickname":"hsfggg"}]
         * pagesize : 30
         * beginThisweek : 19.88
         * dayprofit : 19.88
         */

        private String total;
        private int pagesize;
        private String beginThisweek;
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

        public String getBeginThisweek() {
            return beginThisweek;
        }

        public void setBeginThisweek(String beginThisweek) {
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
             * id : 551
             * uniacid : 0
             * uid : 970021
             * setuid : 970021
             * gold : 2.55
             * level : 1
             * goldid : 188938
             * type : 发包返佣
             * welfnums : 0
             * addtime : 2019-10-17 18:17
             * nickname : hsfggg
             */

            private String id;
            private String uniacid;
            private String uid;
            private String setuid;
            private String gold;
            private String level;
            private String goldid;
            private String type;
            private String welfnums;
            private String addtime;
            private String nickname;

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

            public String getGoldid() {
                return goldid;
            }

            public void setGoldid(String goldid) {
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

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }
        }
    }
}
