package com.example.joo.indianpoker;

/**
 * Created by joo on 2016-11-27.
 */

public class ListViewItem {
    private String gradeStr ;
    private String idStr ;
    private String pointStr ;

    public void setIcon(String icon) {
        gradeStr = icon ;
    }
    public void setTitle(String title) {
        idStr = title ;
    }
    public void setDesc(String desc) {
        pointStr = desc ;
    }

    public String getIcon() {
        return this.gradeStr ;
    }
    public String getTitle() {
        return this.idStr ;
    }
    public String getDesc() {
        return this.pointStr ;
    }
}