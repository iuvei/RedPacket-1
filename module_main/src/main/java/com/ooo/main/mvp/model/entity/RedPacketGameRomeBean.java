package com.ooo.main.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author lanjian
 * creat at 2019/9/17
 * description 抢红包游戏实体类
 */
public class RedPacketGameRomeBean implements Serializable {

    /**
     * status : 1
     * result : [{"id":"1","hxgroupid":"93641408315394","roomname":"扫雷区的房间（单雷）","describe":"1","surl":"http://5949.iiio.top/attachment/images/1/2019/07/z9z9wuR6uH98Fz300w33KFPUPPrdef.jpg"}]
     * msg :
     */

    private int status;
    private String msg;
    private List <ResultBean> result;

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

    public static class ResultBean implements Serializable{
        /**
         * id : 1
         * hxgroupid : 93641408315394
         * roomname : 扫雷区的房间（单雷）
         * describe : 1
         * surl : http://5949.iiio.top/attachment/images/1/2019/07/z9z9wuR6uH98Fz300w33KFPUPPrdef.jpg
         */

        private String id;
        private String hxgroupid;
        private String roomname;
        private String describe;
        private String surl;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getHxgroupid() {
            return hxgroupid;
        }

        public void setHxgroupid(String hxgroupid) {
            this.hxgroupid = hxgroupid;
        }

        public String getRoomname() {
            return roomname;
        }

        public void setRoomname(String roomname) {
            this.roomname = roomname;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getSurl() {
            return surl;
        }

        public void setSurl(String surl) {
            this.surl = surl;
        }
    }
}
