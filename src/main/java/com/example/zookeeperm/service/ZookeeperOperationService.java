package com.example.zookeeperm.service;

import com.example.zookeeperm.data.NodeData;
import com.example.zookeeperm.message.Notifier;
import com.intellij.openapi.ui.MessageType;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/* *
 * @author nss
 * @description zookeeper操作
 * @date 15:08 2023/3/26
 **/
public class ZookeeperOperationService {
    /**
     * 连接zookeeper
     */
    public ZooKeeper connect(String connectString,int timeOut) throws IOException, InterruptedException {
        try {
            ZooKeeper zooKeeper = new ZooKeeper(connectString, timeOut, null);
            return zooKeeper;
        } catch (IOException e) {
            e.printStackTrace();
            Notifier.notify("zookeeper链接出错："+e.getMessage(), MessageType.ERROR);
            throw e;
        }
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
            if(nodeData.getPath().equals("/")) {
                childNode.setPath("/" + child);
            }else {
                childNode.setPath(nodeData.getPath()+"/"+child);
            }
            childNode.setNodeValue(child);
            childrenNodeList.add(childNode);
            getAllNode(childNode,zooKeeper);
        }
        nodeData.setChildrenList(childrenNodeList);
    }
    /**
     * 设置控制权限和元数据
     */
    public void setStatAndAcl(NodeData nodeData,ZooKeeper zooKeeper) throws InterruptedException, KeeperException {
        Stat stat = new Stat();
        List<ACL> aclList = zooKeeper.getACL(nodeData.getPath(), stat);

        //设置控制权限
        nodeData.setACLList(aclList.stream()
                .map(acl->new NodeData.Acl(acl.getId().getId(),acl.getId().getScheme(),String.valueOf(acl.getPerms()))).collect(Collectors.toList()));
        nodeData.setMetaData(new NodeData.Stat(stat));
    }
}
