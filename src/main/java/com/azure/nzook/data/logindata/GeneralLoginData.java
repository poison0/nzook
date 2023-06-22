package com.azure.nzook.data.logindata;

import com.azure.nzook.constant.Constant;
import com.azure.nzook.constant.LoginTypeEnum;

/**
 * @author niu
 * @since 1.0.3
 */
public class GeneralLoginData extends LoginData{
    /*
     * @description 端口号
     **/
    private String port;
    /* *
     * @description ip地址
     **/
    private String ip;

    public GeneralLoginData() {
        setLoginTypeEnum(LoginTypeEnum.GENERAL);
        setTimeout(10000);
        setIp(Constant.DEFAULT_HOST);
        setPort(Constant.DEFAULT_PORT);
        setSave(true);
        setUse(false);
    }


    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String getConnectString() {
        return ip + ":" + port;
    }

    @Override
    public void setDefault() {
        setIp(Constant.DEFAULT_HOST);
        setPort(Constant.DEFAULT_PORT);
    }
}
