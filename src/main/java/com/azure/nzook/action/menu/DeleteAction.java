package com.azure.nzook.action.menu;

import com.azure.nzook.action.toolbar.AbstractAction;
import com.azure.nzook.constant.Constant;
import com.azure.nzook.constant.StatusEnum;
import com.azure.nzook.data.LoginData;
import com.azure.nzook.data.NodeData;
import com.azure.nzook.message.Notifier;
import com.azure.nzook.gui.OperationWindow;
import com.azure.nzook.gui.pop.ConfirmDialog;
import com.azure.nzook.util.Bundle;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.MessageType;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class DeleteAction extends AbstractAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        Object selectedObject = OperationWindow.getTree().getLastSelectedPathComponent();
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedObject;
        NodeData userObject = (NodeData)selectedNode.getUserObject();

        if(userObject.getPath().equals(Constant.ROOT)){
            Notifier.notify(Bundle.getString("notify.warning.treeRootNodeNotDeleted"), MessageType.WARNING);
            return;
        }

        String message = Bundle.getString("confirmDialog.message.deleteNode")+" '"+userObject.getNodeName()+"'";
        if(userObject.getChildrenList() != null && !userObject.getChildrenList().isEmpty()){
            message = Bundle.getString("confirmDialog.message.RecursivelyDeleteNode")+" '"+userObject.getNodeName()+"'";
        }
        ConfirmDialog dialog = new ConfirmDialog(anActionEvent.getProject(), Bundle.getString("action.DeleteAction.text"),message,true);
        if (dialog.showAndGet()) {
            boolean success;
            try {
                success = LoginData.zookeeperOperationService.deleteNodeRecursively(LoginData.zooKeeper, userObject.getPath());
                if(success){
                    deleteNode();
                }
            } catch (Exception e) {
                Notifier.notify(Bundle.getString("notify.error.connection.delete") + e.getMessage(), MessageType.ERROR);
            }
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

    @Override
    protected boolean isEnabled(AnActionEvent e) {
        return LoginData.getStatus() == StatusEnum.CONNECTED;
    }
}
