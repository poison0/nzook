package com.azure.nzook.service;

import com.azure.nzook.action.ListWindowFactory;
import com.azure.nzook.constant.Constant;
import com.azure.nzook.constant.StatusEnum;
import com.azure.nzook.data.LoginData;
import com.azure.nzook.data.LoginDataDto;
import com.azure.nzook.data.NodeData;
import com.azure.nzook.function.ProgressConsumer;
import com.azure.nzook.gui.pop.LoginDialog;
import com.azure.nzook.message.Notifier;
import com.azure.nzook.util.Bundle;
import com.azure.nzook.util.DataUtils;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import org.apache.zookeeper.KeeperException;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.IOException;

import static com.azure.nzook.constant.Constant.LOGIN_LOADING_TITLE;
import static com.azure.nzook.data.LoginData.zooKeeper;
import static com.azure.nzook.data.LoginData.zookeeperOperationService;

/**
 * @author niu
 * @since 1.0
 */
public class Login {
    private Login() {}

    public static void popupLoginDialog(Project project) {
        if(LoginData.getStatus() != StatusEnum.NOT_CONNECT){
            return;
        }
        LoginDialog loginDialog = new LoginDialog(Bundle.getString("loginDialog.title"));
        boolean ok = loginDialog.showAndGet();
        if (ok) {
            //保存登录信息
            LoginDataDto loginData = loginDialog.getLoginData();
            LoginData.loginData = loginData;
            if (Boolean.TRUE.equals(loginData.getSave())) {
                //数据持久化
                PropertiesComponent instance = PropertiesComponent.getInstance();
                instance.setValue(Constant.PROPERTIES_COMPONENT_IP, loginData.getIp());
                instance.setValue(Constant.PROPERTIES_COMPONENT_PORT, loginData.getPort());
                //是否登录
                instance.setValue(Constant.PROPERTIES_COMPONENT_LOGIN, true);
            }else {
                PropertiesComponent instance = PropertiesComponent.getInstance();
                instance.setValue(Constant.PROPERTIES_COMPONENT_LOGIN, false);
                instance.unsetValue(Constant.PROPERTIES_COMPONENT_IP);
                instance.unsetValue(Constant.PROPERTIES_COMPONENT_PORT);
            }
            Login.load(project,loginData);
        }
    }

    private static NodeData login(LoginDataDto data) throws IOException, InterruptedException, KeeperException {
        zooKeeper = zookeeperOperationService.connect(data.getIp() + ":" + data.getPort(), data.getTimeout());
        NodeData nodeData = new NodeData(Constant.ROOT,Constant.ROOT);
        zookeeperOperationService.getAllNode(nodeData,zooKeeper);
        zookeeperOperationService.setAcl(nodeData,zooKeeper);
        return nodeData;
    }
    /* *
     * 加载数据，渲染GUI
     **/
    public static void load(Project project, LoginDataDto loginData) {
        addProgress(project,progressIndicator->{
                LoginData.setStatus(StatusEnum.CONNECTING);
                NodeData data = login(loginData);
                SwingUtilities.invokeLater(() -> {
                    ListWindowFactory.operationWindow.clearAll();
                    // 更新GUI
                    ListWindowFactory.operationWindow.init(data);
                    LoginData.setStatus(StatusEnum.CONNECTED);
                });
        });
    }

    public static void addProgress(Project project, ProgressConsumer<ProgressIndicator> consumer) {

        ProgressManager.getInstance().run(new Task.Backgroundable(project, LOGIN_LOADING_TITLE) {
            @Override
            public void run(@NotNull ProgressIndicator progressIndicator) {
                try {
                    LoginData.setStatus(StatusEnum.CONNECTING);
                    consumer.accept(progressIndicator);
                    LoginData.setStatus(StatusEnum.CONNECTED);
                } catch (IOException | InterruptedException | KeeperException e) {
                    if (e.getMessage().contains("ConnectionLoss")) {
                        Notifier.notify(Bundle.getString("notify.error.connection.parameters"), MessageType.ERROR);
                    } else {
                        Notifier.notify(e.getMessage(), MessageType.ERROR);
                    }
                    DataUtils.removeLoginData();
                    LoginData.setStatus(StatusEnum.NOT_CONNECT);
                    PropertiesComponent instance = PropertiesComponent.getInstance();
                    instance.setValue(Constant.PROPERTIES_COMPONENT_LOGIN, false);
                    close();
                } catch (Exception e) {
                    Notifier.notify(e.getMessage(), MessageType.ERROR);
                    LoginData.setStatus(StatusEnum.NOT_CONNECT);
                    DataUtils.removeLoginData();
                }
            }
        });
    }

    public static void close() {
        try {
            zooKeeper.close();
        } catch (InterruptedException e) {
            Notifier.notify(e.getMessage(), MessageType.ERROR);
        }
    }
}
