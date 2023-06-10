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
/**
 * @author niu
 */
public class AddAction extends AbstractAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        new UpdateNode(Bundle.getString("updateNodeDialog.title.addNode")).showAndGet();
    }
    public AddAction() {
        super(Bundle.getString("action.AddAction.text"), Bundle.getString("action.AddAction.description"), AllIcons.General.Add);
    }

    @Override
    protected boolean isEnabled(AnActionEvent e) {
        return LoginData.getStatus() == StatusEnum.CONNECTED;
    }
}
