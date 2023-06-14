package com.azure.nzook.action.menu;

import com.azure.nzook.action.toolbar.AbstractAction;
import com.azure.nzook.constant.StatusEnum;
import com.azure.nzook.data.LoginData;
import com.azure.nzook.message.Notifier;
import com.azure.nzook.util.DataUtils;
import com.azure.nzook.util.Bundle;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.MessageType;
import org.jetbrains.annotations.NotNull;
/**
 * @author niu
 * @version 1.0
 */
public class CopyPathFromRootAction extends AbstractAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        String path = getNode().getPath();
        DataUtils.setClipboardString(path);
        Notifier.notify(Bundle.getString("notify.info.copyPathSuccess"), MessageType.INFO);
    }
    public CopyPathFromRootAction() {
        super(Bundle.getString("action.CopyPathFromRootAction.text"), Bundle.getString("action.CopyPathFromRootAction.description"),null);
    }
    @Override
    protected boolean isEnabled(AnActionEvent e) {
        return LoginData.getStatus() == StatusEnum.CONNECTED;
    }
}