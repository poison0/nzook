package com.example.zookeeperm.action.toolbar;

import com.example.zookeeperm.constant.StatusEnum;
import com.example.zookeeperm.data.LoginData;
import com.example.zookeeperm.data.LoginDataDto;
import com.example.zookeeperm.gui.pop.LoginDialog;
import com.example.zookeeperm.util.Bundle;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class SettingAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        LoginDialog loginDialog = new LoginDialog(Bundle.getString("loginDialog.title"));
        boolean ok = loginDialog.showAndGet();
        if (ok) {
            //保存登录信息
            LoginDataDto loginData = loginDialog.getLoginData();
            LoginData.setStatus(StatusEnum.NOT_CONNECT);
            LoginData.ip = loginData.getIp();
            LoginData.port = loginData.getPort();
            LoginData.timeout = loginData.getTimeout();
//            try {
//                NodeData data = Login.login(loginData);
//                ListWindowFactory.operationWindow.init(data);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            } catch (KeeperException e) {
//                throw new RuntimeException(e);
//            }
        }
    }
    public SettingAction() {
        super(Bundle.getString("action.SettingAction.text"), Bundle.getString("action.SettingAction.description"), AllIcons.Actions.InlayGear);
    }
}
