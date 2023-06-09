package com.example.zookeeperm.service;

import com.example.zookeeperm.action.ListWindowFactory;
import com.example.zookeeperm.constant.Constant;
import com.example.zookeeperm.constant.StatusEnum;
import com.example.zookeeperm.data.LoginData;
import com.example.zookeeperm.data.LoginDataDto;
import com.example.zookeeperm.data.NodeData;
import com.example.zookeeperm.message.Notifier;
import com.example.zookeeperm.util.Bundle;
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

import static com.example.zookeeperm.constant.Constant.LOGIN_LOADING_TITLE;
import static com.example.zookeeperm.data.LoginData.zooKeeper;
import static com.example.zookeeperm.data.LoginData.zookeeperOperationService;

/**
 * @author nss
 **/
public class Login {
    private Login() {}

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
        ProgressManager.getInstance().run(new Task.Backgroundable(project,LOGIN_LOADING_TITLE) {
            @Override
            public void run(@NotNull ProgressIndicator progressIndicator) {
                try {
                    LoginData.setStatus(StatusEnum.CONNECTING);
                    NodeData data = login(loginData);
                    SwingUtilities.invokeLater(() -> {
                        ListWindowFactory.operationWindow.clearAll();
                        // 更新GUI
                        ListWindowFactory.operationWindow.init(data);
                        LoginData.setStatus(StatusEnum.CONNECTED);
                    });

                } catch (IOException | InterruptedException | KeeperException e) {
                    if (e.getMessage().contains("ConnectionLoss for /")) {
                        Notifier.notify(Bundle.getString("notify.error.connection.parameters"), MessageType.ERROR);
                    } else {
                        Notifier.notify(e.getMessage(), MessageType.ERROR);
                    }
                    LoginData.setStatus(StatusEnum.NOT_CONNECT);
                    try {
                        PropertiesComponent instance = PropertiesComponent.getInstance();
                        instance.setValue(Constant.PROPERTIES_COMPONENT_LOGIN, "false");
                        zooKeeper.close();
                    } catch (InterruptedException ex) {
                        Notifier.notify(e.getMessage(), MessageType.ERROR);
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
    }
}
