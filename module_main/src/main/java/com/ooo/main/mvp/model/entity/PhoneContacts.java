package com.ooo.main.mvp.model.entity;

import android.support.annotation.NonNull;
import android.util.Log;

import me.jessyan.armscomponent.commonres.utils.CharacterParser;

/**
 * @author lanjian
 * creat at 2019/9/12
 * description
 */
public class PhoneContacts implements Comparable<PhoneContacts>{
    private final CharacterParser characterParser;
    private String name;
    private String teleNumber;
    private String position;
    private String spelling;
    private boolean isFirst=false;

    public void setName(String name) {
        this.name = name;
        characterParser.setResource(name);//通过拼音转换类,把汉字转换成拼音//
        this.spelling=characterParser.getSpelling();
        Log.e ( "tag","spelling="+spelling );
    }

    public void setTeleNumber(String teleNumber) {
        this.teleNumber = teleNumber;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setSpelling(String spelling) {
        this.spelling = spelling;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public PhoneContacts() {
        characterParser = new CharacterParser ();
    }

    public String getName() {
        return name;
    }

    public String getTeleNumber() {
        return teleNumber;
    }

    public String getPosition() {
        return position;
    }

    public String getSpelling() {
        return spelling;
    }

    public boolean getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(boolean isFirst) {
        this.isFirst = isFirst;
    }

    @Override
    public int compareTo(@NonNull PhoneContacts o) {
        return this.spelling.compareTo(o.spelling);
    }

    @Override
    public String toString() {
        String name=getName();

        return "name="+name+",isFirst="+isFirst;
    }
}
