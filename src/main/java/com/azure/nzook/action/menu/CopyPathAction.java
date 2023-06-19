package com.azure.nzook.action.menu;

import com.azure.nzook.action.toolbar.AbstractAction;
import com.azure.nzook.constant.StatusEnum;
import com.azure.nzook.data.LoginData;
import com.azure.nzook.message.Notifier;
import com.azure.nzook.util.DataUtils;
import com.azure.nzook.util.Bundle;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.MessageType;
import org.jetbrains.annotations.NotNull;
/**
 * @author niu
 * @since 1.0
 */
public class CopyPathAction extends AbstractAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        String path = getNode().getNodeName();
        DataUtils.setClipboardString(path);
        Notifier.notify(Bundle.getString("notify.info.copyPathSuccess"), MessageType.INFO);
    }
    public CopyPathAction() {
        super(Bundle.getString("action.CopyPathAction.text"), Bundle.getString("action.CopyPathAction.description"), AllIcons.Actions.Copy);
    }
    @Override
    protected boolean isEnabled(AnActionEvent e) {
        return LoginData.getStatus() == StatusEnum.CONNECTED;
    }
}