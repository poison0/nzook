package com.example.zookeeperm.action.toolbar;

import com.example.zookeeperm.constant.StatusEnum;
import com.example.zookeeperm.data.LoginData;
import com.example.zookeeperm.gui.OperationWindow;
import com.example.zookeeperm.util.Bundle;
import com.example.zookeeperm.util.ExpandUtils;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.TreePath;

/**
 * @author niu
 */
public class CollapseAllAction extends AbstractAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        ExpandUtils.expandAll(OperationWindow.getTree(),new TreePath(OperationWindow.getTree().getModel().getRoot()), false);
    }
    public CollapseAllAction() {
        super(Bundle.getString("action.CollapseAllAction.text"), Bundle.getString("action.CollapseAllAction.description"), AllIcons.Actions.Collapseall);
    }

    @Override
    protected boolean isEnabled(AnActionEvent e) {
        return LoginData.getStatus() == StatusEnum.CONNECTED;
    }
}