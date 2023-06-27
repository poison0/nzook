package com.azure.nzook.service;

import com.azure.nzook.action.ListWindowFactory;
import com.azure.nzook.constant.Constant;
import com.azure.nzook.constant.StatusEnum;
import com.azure.nzook.data.ZookeeperData;
import com.azure.nzook.data.logindata.*;
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
import com.intellij.openapi.wm.ToolWindowManager;
import org.apache.zookeeper.KeeperException;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.IOException;

import static com.azure.nzook.constant.Constant.LOGIN_LOADING_TITLE;
import static com.azure.nzook.data.ZookeeperData.zooKeeper;
import static com.azure.nzook.data.ZookeeperData.zookeeperOperationService;

/**
 * @author niu
 * @since 1.0
 */
public class Login {
    private Login() {}

    public static void popupLoginDialog(Project project) {
        if(ZookeeperData.getStatus() != StatusEnum.NOT_CONNECT){
            return;
        }
        LoginDialog loginDialog = new LoginDialog(Bundle.getString("loginDialog.title"));
        boolean ok = loginDialog.showAndGet();
        if (ok) {
            //保存登录信息
            UserLoginData loginData = loginDialog.getLoginData();
            DataUtils.saveLoginData(loginData);
            Login.load(project,loginData);
        }
    }

    private static NodeData login(UserLoginData data) throws IOException, InterruptedException, KeeperException {
        if (data == null) {
            throw new RuntimeException("loginData error: data is null");
        }
        ZookeeperData.userLoginData = data;
        LoginData currentLoginData = data.getCurrentLoginData();
        String connectString;
        if(currentLoginData instanceof GeneralLoginData generalLoginData){
            connectString = generalLoginData.getIp() + ":" + generalLoginData.getPort();
        }else if(currentLoginData instanceof ConnectStringLoginData connectStringLoginData){
            connectString = connectStringLoginData.getConnectionString();
        }else {
            throw new RuntimeException("loginData error: data error");
        }
        zooKeeper = zookeeperOperationService.connect(connectString, currentLoginData.getTimeout());
        NodeData nodeData = new NodeData(Constant.ROOT,Constant.ROOT);
        zookeeperOperationService.getAllNode(nodeData,zooKeeper);
        zookeeperOperationService.setAcl(nodeData,zooKeeper);
        return nodeData;
    }
    /* *
     * 加载数据，渲染GUI
     **/
    public static void load(Project project, UserLoginData loginData) {
        addProgress(project,progressIndicator->{
                ZookeeperData.setStatus(StatusEnum.CONNECTING);
                NodeData data = login(loginData);
                SwingUtilities.invokeLater(() -> {

                    ListWindowFactory.operationWindow.clearAll();
                    // 更新GUI
                    ListWindowFactory.operationWindow.init(data);
                    ZookeeperData.setStatus(StatusEnum.CONNECTED);
                });
        });
    }

    public static void addProgress(Project project, ProgressConsumer<ProgressIndicator> consumer) {

        ProgressManager.getInstance().run(new Task.Backgroundable(project, LOGIN_LOADING_TITLE) {
            @Override
            public void run(@NotNull ProgressIndicator progressIndicator) {
                try {
                    ZookeeperData.setStatus(StatusEnum.CONNECTING);
                    consumer.accept(progressIndicator);
                    ZookeeperData.setStatus(StatusEnum.CONNECTED);
                } catch (IOException | InterruptedException | KeeperException e) {
                    if (e.getMessage().contains("ConnectionLoss")) {
                        Notifier.notify(Bundle.getString("notify.error.connection.parameters"), MessageType.ERROR);
                    } else {
                        Notifier.notify(e.getMessage(), MessageType.ERROR);
                    }
                    DataUtils.removeLoginData();
                    ZookeeperData.setStatus(StatusEnum.NOT_CONNECT);
                    PropertiesComponent instance = PropertiesComponent.getInstance();
                    instance.setValue(Constant.PROPERTIES_COMPONENT_LOGIN, false);
                    close();
                } catch (Exception e) {
                    Notifier.notify(e.getMessage(), MessageType.ERROR);
                    ZookeeperData.setStatus(StatusEnum.NOT_CONNECT);
                    DataUtils.removeLoginData();
                }
            }
        });
    }

    public static void close() {
        try {
            if (zooKeeper != null) {
                zooKeeper.close();
            }
        } catch (InterruptedException e) {
            Notifier.notify(e.getMessage(), MessageType.ERROR);
        }
    }
}
