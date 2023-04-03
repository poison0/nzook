package com.example.zookeeperm.action.rightclick;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.ui.Gray;
import com.intellij.util.IconUtil;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;

public class AddAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        //todo
    }
    public AddAction() {
        super("New Node", "Add new node", AllIcons.General.Add);
    }
}
