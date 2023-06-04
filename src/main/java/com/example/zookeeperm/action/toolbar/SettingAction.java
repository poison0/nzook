package com.example.zookeeperm.action.toolbar;

import com.example.zookeeperm.gui.pop.LoginDialog;
import com.example.zookeeperm.gui.pop.UpdateNode;
import com.example.zookeeperm.util.Bundle;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class SettingAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        new LoginDialog(Bundle.getString("loginDialog.title")).showAndGet();
    }
    public SettingAction() {
        super(Bundle.getString("action.SettingAction.text"), Bundle.getString("action.SettingAction.description"), AllIcons.Actions.InlayGear);
    }
}
