package com.azure.nzook.data.logindata;

import com.azure.nzook.constant.LoginTypeEnum;

/**
 * @author niu
 * @since 1.0
 */
public abstract class LoginData {

    /**
     * 超时时间
     */
    private Integer timeout;

    /**
     * 是否保存
     */
    private Boolean isSave;

    /**
     * 是否使用
     */
    private Boolean isUse;

    /**
     * 登录类型
     */
    private LoginTypeEnum loginTypeEnum;

    public abstract String getConnectString();

    public abstract void setDefault();

    public Boolean getUse() {
        return isUse;
    }

    public void setUse(Boolean use) {
        isUse = use;
    }

    public LoginTypeEnum getLoginTypeEnum() {
        return loginTypeEnum;
    }

    public void setLoginTypeEnum(LoginTypeEnum loginTypeEnum) {
        this.loginTypeEnum = loginTypeEnum;
    }

    public Boolean getSave() {
        return isSave;
    }

    public void setSave(Boolean save) {
        isSave = save;
    }


    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
}
