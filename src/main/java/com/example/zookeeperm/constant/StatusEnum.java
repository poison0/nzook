package com.example.zookeeperm.constant;

public enum StatusEnum {
    NOT_SAVE("未保存"),
    NOT_CONNECT("未连接"),
    CONNECTED("已连接");

    private String status;

    StatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
