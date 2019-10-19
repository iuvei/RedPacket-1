package com.ooo.main.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 0
 * creat at 2019/9/17
 * description 下级玩家实体类
 */
public class UnderPayerBean implements Serializable {


    /**
     * status : 1
     * result : {"total":"2","list":[{"id":"970005","agentid":"970003","agentnickname":"你好","times":"2019-09-10 11:30:52"},{"id":"692536","agentid":"970003","agentnickname":"你好","times":"2019-09-10 11:30:52"}],"pagesize":10}
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
         * list : [{"id":"970005","agentid":"970003","agentnickname":"你好","times":"2019-09-10 11:30:52"},{"id":"692536","agentid":"970003","agentnickname":"你好","times":"2019-09-10 11:30:52"}]
         * pagesize : 10
         */

        private String total;
        private int pagesize;
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

        public List <ListBean> getList() {
            return list;
        }

        public void setList(List <ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable{
            /**
             * id : 970005
             * agentid : 970003
             * agentnickname : 你好
             * times : 2019-09-10 11:30:52
             */

            private String id;
            private String agentid;
            private String agentnickname;
            private String times;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getAgentid() {
                return agentid;
            }

            public void setAgentid(String agentid) {
                this.agentid = agentid;
            }

            public String getAgentnickname() {
                return agentnickname;
            }

            public void setAgentnickname(String agentnickname) {
                this.agentnickname = agentnickname;
            }

            public String getTimes() {
                return times;
            }

            public void setTimes(String times) {
                this.times = times;
            }
        }
    }
}
