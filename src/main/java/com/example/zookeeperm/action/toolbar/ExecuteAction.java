package com.example.zookeeperm.action.toolbar;

import com.example.zookeeperm.constant.StatusEnum;
import com.example.zookeeperm.data.LoginData;
import com.example.zookeeperm.util.Bundle;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class ExecuteAction extends AbstractAction {
    private Boolean isEnable;
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        //todo
    }
    public ExecuteAction() {
        super(Bundle.getString("action.ExecuteAction.text"), Bundle.getString("action.ExecuteAction.description"), AllIcons.Actions.Execute);
    }

    @Override
    protected boolean isEnabled(AnActionEvent e) {
        return LoginData.getStatus() == StatusEnum.NOT_CONNECT;
    }
}
