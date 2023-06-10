package com.example.zookeeperm.action.menu;

import com.example.zookeeperm.action.toolbar.AbstractAction;
import com.example.zookeeperm.constant.StatusEnum;
import com.example.zookeeperm.data.LoginData;
import com.example.zookeeperm.data.NodeData;
import com.example.zookeeperm.gui.OperationWindow;
import com.example.zookeeperm.message.Notifier;
import com.example.zookeeperm.util.Bundle;
import com.example.zookeeperm.util.DataUtils;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.MessageType;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.DefaultMutableTreeNode;

public class CopyPathAction extends AbstractAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        Object selectedObject = OperationWindow.getTree().getLastSelectedPathComponent();
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedObject;
        NodeData userObject = (NodeData)selectedNode.getUserObject();
        String path = userObject.getNodeValue();
        DataUtils.setClipboardString(path);
        Notifier.notify(Bundle.getString("notify.info.copyPathSuccess"), MessageType.INFO);
    }
    public CopyPathAction() {
        super(Bundle.getString("action.CopyPathAction.text"), Bundle.getString("action.CopyPathAction.description"), AllIcons.Actions.Copy);
    }
    @Override
    protected boolean isEnabled(AnActionEvent e) {
        return LoginData.getStatus() == StatusEnum.CONNECTED;
    }
}