package com.example.zhangnan.myfarm.activity_information;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Far-away on 17/6/15.
 */

public class Control_all {
    String type;
    String  ischeck;
    ArrayList<String> id;

    public Control_all() {
        id=new ArrayList<>();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    public ArrayList<String> getId() {
        return id;
    }

    public void setId(ArrayList<String> id) {
        this.id = id;
    }
    public void clear(){
        this.type=null;
        this.id.clear();
        this.ischeck=null;
    }
}
