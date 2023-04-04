package com.example.zookeeperm.action.toolbar;

import com.example.zookeeperm.util.Bundle;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class CollapseAllAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        //todo
    }
    public CollapseAllAction() {
        super(Bundle.getString("action.CollapseAllAction.text"), Bundle.getString("action.CollapseAllAction.description"), AllIcons.Actions.Collapseall);
    }
}