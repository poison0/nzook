package com.example.zookeeperm.action.menu;

import com.example.zookeeperm.data.NodeData;
import com.example.zookeeperm.gui.OperationWindow;
import com.example.zookeeperm.gui.pop.ConfirmDialog;
import com.example.zookeeperm.message.Notifier;
import com.example.zookeeperm.util.Bundle;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.MessageType;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class DeleteAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        Object selectedObject = OperationWindow.getTree().getLastSelectedPathComponent();
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedObject;
        NodeData userObject = (NodeData)selectedNode.getUserObject();
        ConfirmDialog dialog = new ConfirmDialog(anActionEvent.getProject(), Bundle.getString("action.DeleteAction.text"),Bundle.getString("confirmDialog.message.deleteNode")+" '"+userObject.getNodeValue()+"'",true);

        if (dialog.showAndGet()) {
            deleteNode();
        }

    }

    private void deleteNode() {
        DefaultTreeModel model = (DefaultTreeModel)OperationWindow.getTree().getModel();
        Object selectedObject = OperationWindow.getTree().getLastSelectedPathComponent();
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedObject;
        if (selectedNode != null) {
            if (selectedNode.isRoot ()) {
                Notifier.notify(Bundle.getString("notify.warning.treeRootNodeNotDeleted"), MessageType.WARNING);
            }
            else {
                model.removeNodeFromParent (selectedNode);
            }
        }
    }

    public DeleteAction() {
        super(Bundle.getString("action.DeleteAction.text"), Bundle.getString("action.DeleteAction.description"), AllIcons.General.Remove);
    }
}
