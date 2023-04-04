package com.example.zookeeperm.action.menu;

import com.example.zookeeperm.util.Bundle;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class EditAction  extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

    }

    public EditAction() {
        super(Bundle.getString("action.EditAction.text"), Bundle.getString("action.EditAction.description"), AllIcons.Actions.Edit);
    }
}
