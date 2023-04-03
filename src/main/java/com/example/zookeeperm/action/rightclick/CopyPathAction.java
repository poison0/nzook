package com.example.zookeeperm.action.rightclick;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class CopyPathAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        //todo
    }
    public CopyPathAction() {
        super("Copy", "Copy the path to the current node", AllIcons.Actions.Copy);
    }
}