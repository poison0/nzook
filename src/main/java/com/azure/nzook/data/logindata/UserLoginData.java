package com.azure.nzook.data.logindata;

import com.azure.nzook.constant.LoginTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.Map;

/**
 * @author niu
 * @since 1.0.3
 */
public class UserLoginData {

    private ConnectStringLoginData connectStringLoginData;
    private GeneralLoginData generalLoginData;

    @JsonIgnore
    public LoginData getCurrentLoginData() {
        if (Boolean.TRUE.equals(generalLoginData.getUse())) {
            return generalLoginData;
        } else {
            return connectStringLoginData;
        }
    }

    public ConnectStringLoginData getConnectStringLoginData() {
        return connectStringLoginData;
    }

    public GeneralLoginData getGeneralLoginData() {
        return generalLoginData;
    }

    public void setConnectStringLoginData(ConnectStringLoginData connectStringLoginData) {
        this.connectStringLoginData = connectStringLoginData;
    }

    public void setGeneralLoginData(GeneralLoginData generalLoginData) {
        this.generalLoginData = generalLoginData;
    }
}
