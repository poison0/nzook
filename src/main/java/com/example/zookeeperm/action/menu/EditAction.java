package com.example.zookeeperm.action.menu;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class EditAction  extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

    }

    public EditAction() {
        super("Edit Node", "Edit node", AllIcons.Actions.Edit);
    }
}
