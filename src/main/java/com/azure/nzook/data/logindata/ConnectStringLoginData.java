package com.azure.nzook.data.logindata;

import com.azure.nzook.constant.Constant;
import com.azure.nzook.constant.LoginTypeEnum;

/**
 * @author niu
 * @since 1.0.3
 */
public class ConnectStringLoginData extends LoginData{
    private String connectionString;

    public ConnectStringLoginData() {
        setLoginTypeEnum(LoginTypeEnum.CONNECTION_STRING);
        setTimeout(10000);
        setSave(true);
        setUse(false);
        setConnectionString(Constant.DEFAULT_HOST + ":" + Constant.DEFAULT_PORT);
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    @Override
    public String getConnectString() {
        return connectionString;
    }

    @Override
    public void setDefault() {
        setConnectionString(Constant.DEFAULT_HOST + ":" + Constant.DEFAULT_PORT);
    }
}
