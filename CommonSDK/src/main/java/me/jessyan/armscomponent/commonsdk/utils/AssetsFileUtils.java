package me.jessyan.armscomponent.commonsdk.utils;

import android.content.Context;

import com.blankj.utilcode.util.Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AssetsFileUtils {
    public static String getFromAssets(String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(Utils.getApp().getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
