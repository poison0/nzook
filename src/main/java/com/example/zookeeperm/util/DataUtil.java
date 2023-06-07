package com.example.zookeeperm.util;

import com.example.zookeeperm.data.LoginDataDto;
import com.intellij.ide.util.PropertiesComponent;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

/**
 * @author nss
 */
public class DataUtil {
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
    public static LoginDataDto getCurrentLoginData() {
        PropertiesComponent instance = PropertiesComponent.getInstance();
        String ip = instance.getValue("zookeeper.m.ip");
        String port = instance.getValue("zookeeper.m.port");
        LoginDataDto loginDataDto = new LoginDataDto();
        if (ip != null && !ip.isEmpty()) {
            loginDataDto.setIp(ip);
        }
        if (port != null && !port.isEmpty()) {
            loginDataDto.setPort(port);
        }
        loginDataDto.setTimeout(10000);
        return loginDataDto;
    }
}
