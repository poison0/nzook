package com.example.zookeeperm.action.menu;

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


        ConfirmDialog dialog = new ConfirmDialog(anActionEvent.getProject(), Bundle.getString("action.DeleteAction.text"),"Confirm deletion of node 'node'",true);
        if (dialog.showAndGet()) {
            deleteNode();
        }

    }

    private void deleteNode() {
        DefaultTreeModel model = (DefaultTreeModel)OperationWindow.tree.getModel();
        Object selectedObject = OperationWindow.tree.getLastSelectedPathComponent();
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedObject;
        if (selectedNode != null) {
            if (selectedNode.isRoot ()) {
                Notifier.notify("the root node cannot be deleted!", MessageType.WARNING);
            }
            else {
                model.removeNodeFromParent (selectedNode); //如果不是根节点，就从父节点中删除
            }
        }
    }

    public DeleteAction() {
        super(Bundle.getString("action.DeleteAction.text"), Bundle.getString("action.DeleteAction.description"), AllIcons.General.Remove);
    }
}
