package me.jessyan.armscomponent.commonsdk.utils;

import android.text.TextUtils;

import com.blankj.utilcode.util.ConvertUtils;

import java.util.ArrayList;
import java.util.List;

public class StringConvertUtils {

    /**
     * 设置user昵称(没有昵称取username)的首字母属性，方便通讯中对联系人按header分类显示，以及通过右侧ABCD...字母栏快速定位联系人
     *
     * @param str
     */
    public static String getStrInitialLetter(String str) {
        final String DefaultLetter = "#";
        String letter = DefaultLetter;

        final class GetInitialLetter {
            String getLetter(String name) {
                if (TextUtils.isEmpty(name)) {
                    return DefaultLetter;
                }
                char char0 = name.toLowerCase().charAt(0);
                if (Character.isDigit(char0)) {
                    return DefaultLetter;
                }
                ArrayList<HanziToPinyin.Token> l = HanziToPinyin.getInstance().get(name.substring(0, 1));
                if (l != null && l.size() > 0 && l.get(0).target.length() > 0) {
                    HanziToPinyin.Token token = l.get(0);
                    String letter = token.target.substring(0, 1).toUpperCase();
                    char c = letter.charAt(0);
                    if (c < 'A' || c > 'Z') {
                        return DefaultLetter;
                    }
                    return letter;
                }
                return DefaultLetter;
            }
        }

        if (!TextUtils.isEmpty(str)) {
            letter = new GetInitialLetter().getLetter(str);
            return letter;
        }
        if (letter == DefaultLetter && !TextUtils.isEmpty(str)) {
            letter = new GetInitialLetter().getLetter(str);
        }
        return letter;
    }

    /*
     *  list转String
     * @param sChar
     * @param stringList
     * @return
     */
    public static String listToString(String sChar,List<String> stringList) {
        if (stringList == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (String string : stringList) {
            if (flag) {
                result.append(sChar);
            } else {
                flag = true;
            }
            result.append(string);
        }

        return result.toString();
    }

    public static ArrayList<String> stringToList(String sChar, String listString) {

        if (listString == null)
            return null;

        String strs[] = listString.split(sChar);
        ArrayList<String> stringList = new ArrayList<String>();
        for (String str : strs) {
            stringList.add(str);
        }
        return stringList;
    }
}
