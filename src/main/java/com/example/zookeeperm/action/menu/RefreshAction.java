package com.example.zookeeperm.action.menu;

import com.example.zookeeperm.action.toolbar.AbstractAction;
import com.example.zookeeperm.constant.Constant;
import com.example.zookeeperm.constant.StatusEnum;
import com.example.zookeeperm.data.LoginData;
import com.example.zookeeperm.data.NodeData;
import com.example.zookeeperm.gui.OperationWindow;
import com.example.zookeeperm.service.Login;
import com.example.zookeeperm.util.Bundle;
import com.example.zookeeperm.util.DataUtils;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import static com.example.zookeeperm.data.LoginData.zooKeeper;
import static com.example.zookeeperm.data.LoginData.zookeeperOperationService;

public class RefreshAction extends AbstractAction {
    public RefreshAction() {
        super(Bundle.getString("action.RefreshAction.text"), Bundle.getString("action.RefreshAction.description"), AllIcons.Actions.Refresh);
    }
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Object selectedObject = OperationWindow.getTree().getLastSelectedPathComponent();
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedObject;
        NodeData userObject = (NodeData)selectedNode.getUserObject();
        if(userObject.getNodeValue().equals(Constant.ROOT)){
            Login.close();
            Login.load(e.getProject(), DataUtils.getCurrentLoginData());
            return;
        }
        Login.addProgress(e.getProject(), progressIndicator -> {
            zookeeperOperationService.getAllNode(userObject, zooKeeper);
            DefaultTreeModel model = (DefaultTreeModel) OperationWindow.getTree().getModel();
            DefaultMutableTreeNode newTreeNode = OperationWindow.getTreeNode(userObject);
            DefaultMutableTreeNode parent = (DefaultMutableTreeNode) selectedNode.getParent();
            int index = parent.getIndex(selectedNode);
            parent.remove(selectedNode);
            parent.insert(newTreeNode, index);
            model.reload();
            TreeNode[] pathToRoot = model.getPathToRoot(newTreeNode);
            OperationWindow.getTree().setSelectionPath(new TreePath(pathToRoot));
        });
    }
    @Override
    protected boolean isEnabled(AnActionEvent e) {
        return LoginData.getStatus() == StatusEnum.CONNECTED;
    }
}
