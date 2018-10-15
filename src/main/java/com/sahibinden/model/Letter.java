package com.sahibinden.model;

public class Letter {

    private String value;

    private Integer point;

    public Letter(String value, int point) {
        this.value = value;
        this.point = point;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
}
