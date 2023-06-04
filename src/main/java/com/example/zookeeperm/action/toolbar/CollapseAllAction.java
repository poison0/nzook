package com.example.zookeeperm.action.toolbar;

import com.example.zookeeperm.gui.OperationWindow;
import com.example.zookeeperm.util.Bundle;
import com.example.zookeeperm.util.ExpandUtil;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.TreePath;

public class CollapseAllAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        ExpandUtil.expandAll(OperationWindow.tree,new TreePath(OperationWindow.tree.getModel().getRoot()), false);
    }
    public CollapseAllAction() {
        super(Bundle.getString("action.CollapseAllAction.text"), Bundle.getString("action.CollapseAllAction.description"), AllIcons.Actions.Collapseall);
    }
}