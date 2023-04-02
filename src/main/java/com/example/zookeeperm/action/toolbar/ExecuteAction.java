package com.example.zookeeperm.action.toolbar;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class ExecuteAction extends AbstractAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        //todo
    }
    public ExecuteAction() {
        super("Connection", "Connection", AllIcons.Actions.Execute);
    }

    @Override
    protected boolean isEnabled(AnActionEvent e) {
        return false;
    }
}
