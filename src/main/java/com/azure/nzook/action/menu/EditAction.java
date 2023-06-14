package com.azure.nzook.action.menu;

import com.azure.nzook.action.toolbar.AbstractAction;
import com.azure.nzook.constant.PermissionEnum;
import com.azure.nzook.constant.StatusEnum;
import com.azure.nzook.data.LoginData;
import com.azure.nzook.data.NodeData;
import com.azure.nzook.data.UpdateNodeData;
import com.azure.nzook.gui.pop.UpdateNode;
import com.azure.nzook.action.ListWindowFactory;
import com.azure.nzook.util.Bundle;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
/**
 * @author niu
 * @version 1.0
 */
public class EditAction  extends AbstractAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        NodeData node = getNode();
        String nodeName = node.getNodeName();
        UpdateNodeData updateNodeData = new UpdateNodeData();
        updateNodeData.setNodeName(nodeName);
        updateNodeData.setNodeValue(node.getData());
        List<NodeData.Acl> aclList = node.getAclList();
        NodeData.Acl acl = aclList.get(0);
        if(acl != null){
            updateNodeData.setScheme(acl.getScheme());
            updateNodeData.setId(acl.getId());
            updateNodeData.setPermsList(PermissionEnum.getPerms(acl.getPerms()));
        }
        UpdateNode updateNode = new UpdateNode(Bundle.getString("updateNodeDialog.title.editNode"),updateNodeData,true);
        if(updateNode.showAndGet()){
            UpdateNodeData afterData = updateNode.getData();
            boolean isChangeAcl = false;
            if (!afterData.getScheme().equals(updateNodeData.getScheme())) {
                isChangeAcl = true;
            }
            if (!afterData.getId().equals(updateNodeData.getId())) {
                isChangeAcl = true;
            }
            if (!afterData.getPermsList().equals(updateNodeData.getPermsList())) {
                isChangeAcl = true;
            }

            NodeData newNodeData = LoginData.zookeeperOperationService.updateNode(
                    LoginData.zooKeeper,
                    node.getPath(),
                    afterData.getNodeValue(),
                    isChangeAcl?afterData.getAcl():null
            );
            node.setData(newNodeData.getData());
            node.setMetaData(newNodeData.getMetaData());
            ListWindowFactory.operationWindow.switchNode(node);
        }
    }

    public EditAction() {
        super(Bundle.getString("action.EditAction.text"), Bundle.getString("action.EditAction.description"), AllIcons.Actions.Edit);
    }
    @Override
    protected boolean isEnabled(AnActionEvent e) {
        return LoginData.getStatus() == StatusEnum.CONNECTED;
    }
}
