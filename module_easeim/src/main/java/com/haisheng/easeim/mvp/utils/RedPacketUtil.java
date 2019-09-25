package com.haisheng.easeim.mvp.utils;

/**
 * @author lanjian
 * creat at 2019/9/25
 * description
 */
public class RedPacketUtil {

    public enum RedType{
        NONE(0),SAO_LEI(1),GAME_CONTRAL(2),NIU_NIU(3),FU_LI(4);
        int value;
        RedType(int i) {
            value = i;
        }

        public int getValue() {
            return value;
        }
    }
}
