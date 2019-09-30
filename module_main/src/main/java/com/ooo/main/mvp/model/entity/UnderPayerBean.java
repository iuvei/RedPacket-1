package com.ooo.main.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 0
 * creat at 2019/9/17
 * description 下级玩家实体类
 */
public class UnderPayerBean implements Serializable {

    /**
     * status : 1
     * result : {"list":[{"fuid":"969987","nickname":"2","avatar":"http://5761.iiio.top/attachment/images/1/2019/07/H93CeEh9A9YHg99z6hXecx9EjjEOA6.png","gender":"0","createtime":"1566386845","agencynums":0,"playernums":1,"brokerage":0}],"team":{"allnums":4,"agencynums":0,"playernums":4},"drive":{"allnums":1,"agencynums":0,"playernums":1}}
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
         * list : [{"fuid":"969987","nickname":"2","avatar":"http://5761.iiio.top/attachment/images/1/2019/07/H93CeEh9A9YHg99z6hXecx9EjjEOA6.png","gender":"0","createtime":"1566386845","agencynums":0,"playernums":1,"brokerage":0}]
         * team : {"allnums":4,"agencynums":0,"playernums":4}
         * drive : {"allnums":1,"agencynums":0,"playernums":1}
         */
        private TeamBean team;
        private DriveBean drive;
        private List <ListBean> list;

        public TeamBean getTeam() {
            return team;
        }

        public void setTeam(TeamBean team) {
            this.team = team;
        }

        public DriveBean getDrive() {
            return drive;
        }

        public void setDrive(DriveBean drive) {
            this.drive = drive;
        }

        public List <ListBean> getList() {
            return list;
        }

        public void setList(List <ListBean> list) {
            this.list = list;
        }

        public static class TeamBean implements Serializable{
            /**
             * allnums : 4
             * agencynums : 0
             * playernums : 4
             */

            private int allnums;
            private int agencynums;
            private int playernums;

            public int getAllnums() {
                return allnums;
            }

            public void setAllnums(int allnums) {
                this.allnums = allnums;
            }

            public int getAgencynums() {
                return agencynums;
            }

            public void setAgencynums(int agencynums) {
                this.agencynums = agencynums;
            }

            public int getPlayernums() {
                return playernums;
            }

            public void setPlayernums(int playernums) {
                this.playernums = playernums;
            }
        }

        public static class DriveBean implements Serializable{
            /**
             * allnums : 1
             * agencynums : 0
             * playernums : 1
             */

            private int allnums;
            private int agencynums;
            private int playernums;

            public int getAllnums() {
                return allnums;
            }

            public void setAllnums(int allnums) {
                this.allnums = allnums;
            }

            public int getAgencynums() {
                return agencynums;
            }

            public void setAgencynums(int agencynums) {
                this.agencynums = agencynums;
            }

            public int getPlayernums() {
                return playernums;
            }

            public void setPlayernums(int playernums) {
                this.playernums = playernums;
            }
        }

        public static class ListBean implements Serializable{
            /**
             * fuid : 969987
             * nickname : 2
             * avatar : http://5761.iiio.top/attachment/images/1/2019/07/H93CeEh9A9YHg99z6hXecx9EjjEOA6.png
             * gender : 0
             * createtime : 1566386845
             * agencynums : 0
             * playernums : 1
             * brokerage : 0
             */

            private String fuid;
            private String nickname;
            private String avatar;
            private String gender;
            private String createtime;
            private int agencynums;
            private int playernums;
            private int brokerage;

            public String getFuid() {
                return fuid;
            }

            public void setFuid(String fuid) {
                this.fuid = fuid;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getCreatetime() {
                SimpleDateFormat format = new SimpleDateFormat ( "MM-dd HH:mm:ss" );
                return format.format ( new Date ( Long.parseLong ( createtime ) ) );
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public int getAgencynums() {
                return agencynums;
            }

            public void setAgencynums(int agencynums) {
                this.agencynums = agencynums;
            }

            public int getPlayernums() {
                return playernums;
            }

            public void setPlayernums(int playernums) {
                this.playernums = playernums;
            }

            public int getBrokerage() {
                return brokerage;
            }

            public void setBrokerage(int brokerage) {
                this.brokerage = brokerage;
            }
        }
    }
}
