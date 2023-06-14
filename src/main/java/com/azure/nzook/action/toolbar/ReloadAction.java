package com.azure.nzook.action.toolbar;

import com.azure.nzook.service.Login;
import com.azure.nzook.constant.StatusEnum;
import com.azure.nzook.data.LoginData;
import com.azure.nzook.util.Bundle;
import com.azure.nzook.util.DataUtils;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

/**
 * @author niu
 * @version 1.0
 */
public class ReloadAction extends AbstractAction{

    public ReloadAction() {
        super(Bundle.getString("action.ReloadAction.text"), Bundle.getString("action.ReloadAction.description"), AllIcons.Actions.Refresh);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Login.close();
        Login.load(e.getProject(), DataUtils.getCurrentLoginData());
    }

    @Override
    protected boolean isEnabled(AnActionEvent e) {
        return LoginData.getStatus() == StatusEnum.CONNECTED;
    }
}
