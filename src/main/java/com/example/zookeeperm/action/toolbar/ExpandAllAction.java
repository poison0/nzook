package com.example.zookeeperm.action.toolbar;

import com.example.zookeeperm.util.Bundle;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class ExpandAllAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        //todo
    }
    public ExpandAllAction() {
        super(Bundle.getString("action.ExpandAllAction.text"), Bundle.getString("action.ExpandAllAction.description"), AllIcons.Actions.Expandall);
    }
}