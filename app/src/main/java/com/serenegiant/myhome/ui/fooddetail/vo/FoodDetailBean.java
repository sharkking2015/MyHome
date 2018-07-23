package com.serenegiant.myhome.ui.fooddetail.vo;

public class FoodDetailBean {
    private String name;
    private String introdece;
    private int type;

    public FoodDetailBean(String name, String introdece, int type) {
        this.name = name;
        this.introdece = introdece;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntrodece() {
        return introdece;
    }

    public void setIntrodece(String introdece) {
        this.introdece = introdece;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
