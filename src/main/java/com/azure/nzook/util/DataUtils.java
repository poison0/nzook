package com.azure.nzook.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.azure.nzook.constant.Constant;
import com.azure.nzook.constant.LoginTypeEnum;
import com.azure.nzook.constant.PermissionEnum;
import com.azure.nzook.data.logindata.*;
import com.intellij.ide.util.PropertiesComponent;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author niu
 * @since 1.0
 */
public class DataUtils {

    private DataUtils() {}
    /**
     * 把文本设置到剪贴板（复制）
     */
    public static void setClipboardString(String text) {
        // 获取系统剪贴板
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        // 封装文本内容
        Transferable trans = new StringSelection(text);
        // 把文本内容设置到系统剪贴板
        clipboard.setContents(trans, null);
    }

    public static Boolean isLogin() {
        PropertiesComponent instance = PropertiesComponent.getInstance();
        return instance.getBoolean(Constant.PROPERTIES_COMPONENT_LOGIN);
    }

    public static void removeLoginData() {
        PropertiesComponent instance = PropertiesComponent.getInstance();
        instance.unsetValue(Constant.PROPERTIES_COMPONENT_IP);
        instance.unsetValue(Constant.PROPERTIES_COMPONENT_PORT);
        instance.unsetValue(Constant.PROPERTIES_COMPONENT_LOGIN);
        instance.unsetValue(Constant.PROPERTIES_COMPONENT_LOGIN_DATA);
    }


    public static UserLoginData getCurrentLoginData() {
        PropertiesComponent instance = PropertiesComponent.getInstance();
        String value = instance.getValue(Constant.PROPERTIES_COMPONENT_LOGIN_DATA);
        if (value == null || value.isEmpty()) {
            GeneralLoginData oldLoginData = getOldLoginData();
            if(oldLoginData.getIp() != null && !oldLoginData.getIp().isEmpty() && oldLoginData.getPort() != null && !oldLoginData.getPort().isEmpty()) {
                UserLoginData loginData = new UserLoginData();
                oldLoginData.setUse(true);
                loginData.setGeneralLoginData(oldLoginData);
                loginData.setConnectStringLoginData(new ConnectStringLoginData());
                instance.setValue(Constant.PROPERTIES_COMPONENT_LOGIN_DATA, JSON.toJSONString(loginData));
                return loginData;
            }
            return null;
        }else {
            return JSON.parseObject(value, UserLoginData.class);
        }
    }

    public static void saveLoginData(UserLoginData loginData) {
        PropertiesComponent instance = PropertiesComponent.getInstance();
        if (Boolean.FALSE.equals(loginData.getConnectStringLoginData().getSave())) {
            loginData.getConnectStringLoginData().setDefault();
        }
        if (Boolean.FALSE.equals(loginData.getGeneralLoginData().getSave())) {
            loginData.getGeneralLoginData().setDefault();
        }
        instance.setValue(Constant.PROPERTIES_COMPONENT_LOGIN_DATA, JSON.toJSONString(loginData));
    }

    /**
     * 获取旧的登录数据
     * @since 1.0.3
     * @return 旧的登录数据
     */
    private static GeneralLoginData getOldLoginData() {
        PropertiesComponent instance = PropertiesComponent.getInstance();
        String ip = instance.getValue(Constant.PROPERTIES_COMPONENT_IP);
        String port = instance.getValue(Constant.PROPERTIES_COMPONENT_PORT);
        boolean login = instance.getBoolean(Constant.PROPERTIES_COMPONENT_LOGIN);
        GeneralLoginData loginData = new GeneralLoginData();
        if (ip != null && !ip.isEmpty()) {
            loginData.setIp(ip);
        }
        if (port != null && !port.isEmpty()) {
            loginData.setPort(port);
        }
        if(login) {
            loginData.setSave(true);
        }
        loginData.setTimeout(Constant.DEFAULT_TIMEOUT);
        return loginData;
    }

    public static String getAclPerms(int perms) {
        List<PermissionEnum> aclPermsList = getAclPermsList(perms);
        return aclPermsList.stream().map(PermissionEnum::getName).collect(Collectors.joining(","));
    }
    public static List<PermissionEnum> getAclPermsList(int perms) {
        List<PermissionEnum> result = new ArrayList<>();
        for (PermissionEnum value : PermissionEnum.values()) {
            if ((value.getValue() | perms) == perms) {
                result.add(value);
            }
        }
        return result;
    }
}
