package com.azure.nzook.action.menu;

import com.azure.nzook.action.toolbar.AbstractAction;
import com.azure.nzook.constant.StatusEnum;
import com.azure.nzook.data.ZookeeperData;
import com.azure.nzook.data.NodeData;
import com.azure.nzook.action.ListWindowFactory;
import com.azure.nzook.data.UpdateNodeData;
import com.azure.nzook.gui.OperationWindow;
import com.azure.nzook.gui.pop.UpdateNode;
import com.azure.nzook.util.Bundle;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.util.List;

/**
 * @author niu
 * @since 1.0
 */
public class AddAction extends AbstractAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        NodeData node = getNode();
        String nodeName = node.getNodeName();
        UpdateNodeData updateNodeData = new UpdateNodeData();
        updateNodeData.setParentNodeName(nodeName);

        UpdateNode updateNode = new UpdateNode(Bundle.getString("updateNodeDialog.title.addNode"),updateNodeData,false);
        if(updateNode.showAndGet()){
            UpdateNodeData data = updateNode.getData();
            NodeData newNodeData = ZookeeperData.zookeeperOperationService.addNode(
                    ZookeeperData.zooKeeper,
                    node.getPath(),
                    data.getNodeName(),
                    data.getNodeValue(),
                    data.getAcl(),
                    data.getCreateMode(),
                    data.getTtl()
            );
            if (newNodeData != null) {

                List<String> childNodeName = ZookeeperData.zookeeperOperationService.getChildNodeName(node.getPath(), ZookeeperData.zooKeeper);
                if(childNodeName == null){
                    return;
                }

                //获取插入位置
                int index = 0;
                for (int i = 0; i < childNodeName.size(); i++) {
                    String child = childNodeName.get(i);
                    if (child.equals(newNodeData.getNodeName())) {
                        index = i;
                        break;
                    }
                }

                //添加成功
                DefaultTreeModel model = (DefaultTreeModel) OperationWindow.getTree().getModel();
                Object selectedObject = OperationWindow.getTree().getLastSelectedPathComponent();
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedObject;
                DefaultMutableTreeNode newNode = OperationWindow.getTreeNode(newNodeData);
                selectedNode.insert(newNode,index);
                model.reload();
                TreeNode[] pathToRoot = model.getPathToRoot(newNode);
                OperationWindow.getTree().setSelectionPath(new TreePath(pathToRoot));
                ListWindowFactory.operationWindow.switchNode(newNodeData);
            }
        }
    }


    public AddAction() {
        super(Bundle.getString("action.AddAction.text"), Bundle.getString("action.AddAction.description"), AllIcons.General.Add);
    }

    @Override
    protected boolean isEnabled(AnActionEvent e) {
        return ZookeeperData.getStatus() == StatusEnum.CONNECTED;
    }

}
