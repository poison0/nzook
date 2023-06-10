package com.example.zookeeperm.action.toolbar;

import com.example.zookeeperm.constant.StatusEnum;
import com.example.zookeeperm.data.LoginData;
import com.example.zookeeperm.service.Login;
import com.example.zookeeperm.util.Bundle;
import com.example.zookeeperm.util.DataUtils;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

/**
 * @author nss
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
