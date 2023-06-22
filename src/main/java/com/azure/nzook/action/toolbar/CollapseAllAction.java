package com.azure.nzook.action.toolbar;

import com.azure.nzook.constant.StatusEnum;
import com.azure.nzook.data.ZookeeperData;
import com.azure.nzook.gui.OperationWindow;
import com.azure.nzook.util.Bundle;
import com.azure.nzook.util.ExpandUtils;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.TreePath;

/**
 * @author niu
 * @since 1.0
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
        return ZookeeperData.getStatus() == StatusEnum.CONNECTED;
    }
}