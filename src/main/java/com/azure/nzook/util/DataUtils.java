package com.azure.nzook.util;

import com.azure.nzook.constant.Constant;
import com.azure.nzook.constant.PermissionEnum;
import com.azure.nzook.data.LoginDataDto;
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
 * @version 1.0
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
        String ip = instance.getValue(Constant.PROPERTIES_COMPONENT_IP);
        String port = instance.getValue(Constant.PROPERTIES_COMPONENT_PORT);
        boolean login = instance.getBoolean(Constant.PROPERTIES_COMPONENT_LOGIN);
        return ip != null && !ip.isEmpty() && port != null && !port.isEmpty() && login;
    }
    public static void removeLoginData() {
        PropertiesComponent instance = PropertiesComponent.getInstance();
        instance.unsetValue(Constant.PROPERTIES_COMPONENT_IP);
        instance.unsetValue(Constant.PROPERTIES_COMPONENT_PORT);
        instance.unsetValue(Constant.PROPERTIES_COMPONENT_LOGIN);
    }

    public static LoginDataDto getCurrentLoginData() {
        PropertiesComponent instance = PropertiesComponent.getInstance();
        String ip = instance.getValue(Constant.PROPERTIES_COMPONENT_IP);
        String port = instance.getValue(Constant.PROPERTIES_COMPONENT_PORT);
        LoginDataDto loginDataDto = new LoginDataDto();
        if (ip != null && !ip.isEmpty()) {
            loginDataDto.setIp(ip);
        }
        if (port != null && !port.isEmpty()) {
            loginDataDto.setPort(port);
        }
        loginDataDto.setTimeout(Constant.DEFAULT_TIMEOUT);
        return loginDataDto;
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
