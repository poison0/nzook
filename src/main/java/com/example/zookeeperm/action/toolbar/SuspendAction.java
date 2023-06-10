package com.example.zookeeperm.action.toolbar;

import com.example.zookeeperm.action.ListWindowFactory;
import com.example.zookeeperm.constant.Constant;
import com.example.zookeeperm.constant.StatusEnum;
import com.example.zookeeperm.data.LoginData;
import com.example.zookeeperm.message.Notifier;
import com.example.zookeeperm.util.Bundle;
import com.intellij.icons.AllIcons;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.MessageType;
import org.jetbrains.annotations.NotNull;

/**
 * @author niu
 */
public class SuspendAction extends AbstractAction {

    public SuspendAction() {
        super(Bundle.getString("action.SuspendAction.text"), Bundle.getString("action.SuspendAction.description"), AllIcons.Actions.Suspend);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        try {
            PropertiesComponent instance = PropertiesComponent.getInstance();
            instance.setValue(Constant.PROPERTIES_COMPONENT_LOGIN, false);
            LoginData.zooKeeper.close();
            LoginData.setStatus(StatusEnum.NOT_CONNECT);
            ListWindowFactory.operationWindow.clearAll();
            ListWindowFactory.operationWindow.setDefaultPanel();
        } catch (InterruptedException ex) {
            Notifier.notify(ex.getMessage(), MessageType.ERROR);
            Thread.currentThread().interrupt();
        }
    }

    // 该方法用于设置Action的可用性
    @Override
    protected boolean isEnabled(AnActionEvent e) {
        return LoginData.getStatus() == StatusEnum.CONNECTED;
    }
}
