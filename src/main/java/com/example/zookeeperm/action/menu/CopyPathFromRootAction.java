package com.example.zookeeperm.action.menu;

import com.example.zookeeperm.util.Bundle;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class CopyPathFromRootAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        //todo
    }
    public CopyPathFromRootAction() {
        super(Bundle.getString("action.CopyPathFromRootAction.text"), Bundle.getString("action.CopyPathFromRootAction.description"),null);
    }
}