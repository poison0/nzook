package com.example.zookeeperm.action.rightclick;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class DeleteAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        //todo
    }
    public DeleteAction() {
        super("Delete Path", "Delete path", null);
    }
}