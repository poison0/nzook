package com.azure.nzook.action.toolbar;

import com.azure.nzook.message.Notifier;
import com.azure.nzook.action.ListWindowFactory;
import com.azure.nzook.constant.Constant;
import com.azure.nzook.constant.StatusEnum;
import com.azure.nzook.data.LoginData;
import com.azure.nzook.util.Bundle;
import com.intellij.icons.AllIcons;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.MessageType;
import org.jetbrains.annotations.NotNull;

/**
 * @author niu
 * @version 1.0
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
        }
    }

    // 该方法用于设置Action的可用性
    @Override
    protected boolean isEnabled(AnActionEvent e) {
        return LoginData.getStatus() == StatusEnum.CONNECTED;
    }
}
