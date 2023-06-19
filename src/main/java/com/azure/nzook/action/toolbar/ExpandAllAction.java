package com.azure.nzook.action.toolbar;

import com.azure.nzook.constant.StatusEnum;
import com.azure.nzook.data.LoginData;
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
public class ExpandAllAction extends AbstractAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        ExpandUtils.expandAll(OperationWindow.getTree(),new TreePath(OperationWindow.getTree().getModel().getRoot()), true);
    }



    public ExpandAllAction() {
        super(Bundle.getString("action.ExpandAllAction.text"), Bundle.getString("action.ExpandAllAction.description"), AllIcons.Actions.Expandall);
    }

    @Override
    protected boolean isEnabled(AnActionEvent e) {
        return LoginData.getStatus() == StatusEnum.CONNECTED;
    }
}