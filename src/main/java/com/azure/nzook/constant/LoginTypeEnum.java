package com.azure.nzook.constant;

/**
 * 登录类型枚举
 * @author niu
 * @since 1.0.3
 */
public enum LoginTypeEnum {
    GENERAL(0, "普通登录"),
    CONNECTION_STRING(1, "连接字符串登录");

    private final Integer code;
    private final String desc;

    LoginTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
