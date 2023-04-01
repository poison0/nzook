package com.example.zookeeperm.action.toolbar;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class SignOutAction extends AnAction {

    public SignOutAction() {
        super("Sign Out", "Sign out", AllIcons.Actions.PopFrame);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        //todo
    }
}
