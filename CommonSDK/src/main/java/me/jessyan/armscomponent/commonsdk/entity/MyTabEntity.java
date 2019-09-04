package me.jessyan.armscomponent.commonsdk.entity;

import com.flyco.tablayout.listener.CustomTabEntity;

public class MyTabEntity implements CustomTabEntity {

    private String title;
    private int selectedIcon;
    private int unSelectedIcon;

    public MyTabEntity(String title){
        this.title = title;
    }

    public MyTabEntity(String title, int selectedIcon){
        this.title = title;
        this.selectedIcon = selectedIcon;
    }

    public MyTabEntity(String title, int selectedIcon, int unSelectedIcon){
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSelectedIcon(int selectedIcon) {
        this.selectedIcon = selectedIcon;
    }

    public void setUnSelectedIcon(int unSelectedIcon) {
        this.unSelectedIcon = unSelectedIcon;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }
}
