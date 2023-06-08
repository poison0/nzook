package com.example.zookeeperm.action.toolbar;

import com.example.zookeeperm.action.ListWindowFactory;
import com.example.zookeeperm.constant.StatusEnum;
import com.example.zookeeperm.data.LoginData;
import com.example.zookeeperm.data.LoginDataDto;
import com.example.zookeeperm.data.NodeData;
import com.example.zookeeperm.gui.pop.LoginDialog;
import com.example.zookeeperm.message.Notifier;
import com.example.zookeeperm.service.Login;
import com.example.zookeeperm.util.Bundle;
import com.intellij.icons.AllIcons;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import org.apache.zookeeper.KeeperException;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.IOException;

import static com.example.zookeeperm.data.LoginData.zooKeeper;

public class ExecuteAction extends AbstractAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();
        LoginDialog loginDialog = new LoginDialog(Bundle.getString("loginDialog.title"));
        boolean ok = loginDialog.showAndGet();
        if (ok) {
            //保存登录信息
            LoginDataDto loginData = loginDialog.getLoginData();
            LoginData.setStatus(StatusEnum.CONNECTING);
            LoginData.loginData = loginData;
            if (Boolean.TRUE.equals(loginData.getSave())) {
                //数据持久化
                PropertiesComponent instance = PropertiesComponent.getInstance();
                instance.setValue("zookeeper.m.ip", loginData.getIp());
                instance.setValue("zookeeper.m.port", loginData.getPort());
                //是否登录
                instance.setValue("zookeeper.m.login", "true");
            }
            Login.load(project,loginData);
        }
    }



    public ExecuteAction() {
        super(Bundle.getString("action.ExecuteAction.text"), Bundle.getString("action.ExecuteAction.description"), AllIcons.Toolbar.AddSlot);
    }

    @Override
    protected boolean isEnabled(AnActionEvent e) {
        return LoginData.getStatus() == StatusEnum.NOT_CONNECT;
    }
}
