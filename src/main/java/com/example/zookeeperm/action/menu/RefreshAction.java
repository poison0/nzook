package com.example.zookeeperm.action.menu;

import com.example.zookeeperm.util.Bundle;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class RefreshAction extends AnAction {
    public RefreshAction() {
        super(Bundle.getString("action.RefreshAction.text"), Bundle.getString("action.RefreshAction.description"), AllIcons.Actions.Refresh);
    }
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

    }

}
