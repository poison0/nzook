package com.example.zookeeperm.action.menu;

import com.example.zookeeperm.action.toolbar.AbstractAction;
import com.example.zookeeperm.constant.StatusEnum;
import com.example.zookeeperm.data.LoginData;
import com.example.zookeeperm.gui.pop.UpdateNode;
import com.example.zookeeperm.util.Bundle;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class EditAction  extends AbstractAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        new UpdateNode(Bundle.getString("updateNodeDialog.title.editNode")).showAndGet();
    }

    public EditAction() {
        super(Bundle.getString("action.EditAction.text"), Bundle.getString("action.EditAction.description"), AllIcons.Actions.Edit);
    }
    @Override
    protected boolean isEnabled(AnActionEvent e) {
        return LoginData.getStatus() == StatusEnum.CONNECTED;
    }
}
