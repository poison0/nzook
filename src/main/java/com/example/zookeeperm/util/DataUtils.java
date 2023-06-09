package com.example.zookeeperm.util;

import com.example.zookeeperm.constant.Constant;
import com.example.zookeeperm.data.LoginDataDto;
import com.intellij.ide.util.PropertiesComponent;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import static com.example.zookeeperm.constant.Constant.PROPERTIES_COMPONENT_IP;

/**
 * @author nss
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
        String login = instance.getValue(Constant.PROPERTIES_COMPONENT_LOGIN);
        return ip != null && !ip.isEmpty() && port != null && !port.isEmpty() && login != null && !login.isEmpty() && !"false".equals(login);
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
}
