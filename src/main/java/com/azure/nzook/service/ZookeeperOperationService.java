package com.azure.nzook.service;

import com.azure.nzook.constant.Constant;
import com.azure.nzook.data.NodeData;
import com.azure.nzook.message.Notifier;
import com.azure.nzook.util.Bundle;
import com.azure.nzook.util.DataUtils;
import com.intellij.openapi.ui.MessageType;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author niu
 * @since 1.0
 */
public class ZookeeperOperationService {
    /**
     * 连接zookeeper
     */
    public ZooKeeper connect(String connectString,int timeOut) throws IOException {
        try {
            return new ZooKeeper(connectString, timeOut, null);
        } catch (IOException e) {
            Notifier.notify(Bundle.getString("notify.error.connection.io") +e.getMessage(), MessageType.ERROR);
            throw e;
        }
    }
    public List<String> getChildNodeName(String path,ZooKeeper zooKeeper) {
        try {
            return zooKeeper.getChildren(path, false);
        } catch (KeeperException | InterruptedException e ) {
            Notifier.notify(Bundle.getString("notify.error.connection.create")+e.getMessage(), MessageType.ERROR);
        }
        return null;
    }

    /**
     * 递归获取节点列表
     */
    public void getAllNode(NodeData nodeData,ZooKeeper zooKeeper) throws InterruptedException, KeeperException {
        Stat stat = new Stat();
        byte[] data = zooKeeper.getData(nodeData.getPath(), false, stat);
        nodeData.setMetaData(new NodeData.Stat(stat));
        if (data != null) {
            nodeData.setData(new String(data));
        }
        //设置数据
        List<String> childrenList = zooKeeper.getChildren(nodeData.getPath(), false);

        ArrayList<NodeData> childrenNodeList = new ArrayList<>();
        for (String child : childrenList) {
            NodeData childNode = new NodeData();
            if(Constant.ROOT.equals(nodeData.getPath())) {
                childNode.setPath(Constant.NODE_SPLIT + child);
            }else {
                childNode.setPath(nodeData.getPath()+Constant.NODE_SPLIT+child);
            }
            childNode.setNodeName(child);
            childrenNodeList.add(childNode);
            getAllNode(childNode,zooKeeper);
        }
        nodeData.setChildrenList(childrenNodeList);
    }

    public NodeData updateNode(ZooKeeper zooKeeper,String path,String nodeValue,List<ACL> acl) {
        try {
            Stat stat = zooKeeper.setData(path, nodeValue.getBytes(), -1);
            if(acl != null) {
                try {
                    zooKeeper.setACL(path, acl, -1);
                } catch (KeeperException | InterruptedException e) {
                    Notifier.notify(Bundle.getString("notify.error.connection.update.acl")+e.getMessage(), MessageType.ERROR);
                }
            }
            NodeData nodeData = new NodeData();
            nodeData.setMetaData(new NodeData.Stat(stat));
            nodeData.setData(nodeValue);
            return nodeData;
        } catch (KeeperException | InterruptedException e) {
            Notifier.notify(Bundle.getString("notify.error.connection.update")+e.getMessage(), MessageType.ERROR);
        }
        return null;
    }


    /**
     * 创建节点
     */
    public NodeData addNode(ZooKeeper zooKeeper,String path,String nodeName,String nodeValue,List<ACL> acl,CreateMode createMode,Long ttl){
        try {
            Stat stat = new Stat();
            if (ttl == null) {
                zooKeeper.create(path+Constant.NODE_SPLIT+nodeName, nodeValue.getBytes(), acl, createMode, stat);
            }else {
                zooKeeper.create(path+Constant.NODE_SPLIT+nodeName, nodeValue.getBytes(), acl, createMode, stat,ttl);
            }
            NodeData nodeData = new NodeData();
            nodeData.setPath(path+Constant.NODE_SPLIT+nodeName);
            nodeData.setNodeName(nodeName);
            nodeData.setMetaData(new NodeData.Stat(stat));
            nodeData.setData(nodeValue);
            return nodeData;
        } catch (KeeperException | InterruptedException e) {
            Notifier.notify(Bundle.getString("notify.error.connection.create")+e.getMessage(), MessageType.ERROR);
        }
        return null;
    }


    /**
     * 删除节点
     */
    public boolean deleteNode(ZooKeeper zooKeeper,String path) {
        try {
            zooKeeper.delete(path, -1);
            return true;
        } catch (InterruptedException | KeeperException e) {
            Notifier.notify(Bundle.getString("notify.error.connection.delete") + e.getMessage(), MessageType.ERROR);
        }
        return false;
    }
    public boolean deleteNodeRecursively(ZooKeeper zooKeeper,String path) throws Exception {
        // 获取节点的所有子节点
        List<String> children = zooKeeper.getChildren(path, false);
        // 遍历每个子节点
        for (String child : children) {
            // 拼接子节点的完整路径
            String childPath = path + Constant.NODE_SPLIT + child;
            // 递归删除子节点
            deleteNodeRecursively(zooKeeper,childPath);
        }
        // 删除当前节点
        zooKeeper.delete(path, -1);
        return true;
    }


    /**
     * 设置控制权限
     */
    public void setAcl(NodeData nodeData,ZooKeeper zooKeeper) throws InterruptedException, KeeperException {
        Stat stat = new Stat();
        List<ACL> aclList = zooKeeper.getACL(nodeData.getPath(), stat);

        //设置控制权限
        nodeData.setAclList(aclList.stream()
                .map(acl->new NodeData.Acl(acl.getId().getId(), DataUtils.getAclPerms(acl.getPerms()),acl.getId().getScheme())).collect(Collectors.toList()));
    }

}
