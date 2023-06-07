package com.example.zookeeperm.util;

import com.example.zookeeperm.data.LoginData;
import com.example.zookeeperm.data.LoginDataDto;

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
        LoginDataDto loginDataDto = new LoginDataDto();
        if (LoginData.ip != null && !LoginData.ip.isEmpty()) {
            loginDataDto.setIp(LoginData.ip);
        }
        if (LoginData.port != null && !LoginData.port.isEmpty()) {
            loginDataDto.setPort(LoginData.port);
        }
        if (LoginData.timeout != null) {
            loginDataDto.setTimeout(LoginData.timeout);
        }
        return loginDataDto;
    }
}
