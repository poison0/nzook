package com.example.zookeeperm.action.menu;

import com.example.zookeeperm.gui.pop.DefaultDialog;
import com.example.zookeeperm.util.Bundle;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class DeleteAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        DefaultDialog dialog = new DefaultDialog(anActionEvent.getProject(), Bundle.getString("action.DeleteAction.text"),"Confirm deletion of node 'node'",true);
        dialog.show();
    }
    public DeleteAction() {
        super(Bundle.getString("action.DeleteAction.text"), Bundle.getString("action.DeleteAction.description"), AllIcons.General.Remove);
    }
}
