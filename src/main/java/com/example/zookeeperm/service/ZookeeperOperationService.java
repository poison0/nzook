package com.example.zookeeperm.service;

import com.example.zookeeperm.data.NodeData;
import com.example.zookeeperm.message.Notifier;
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
 **/
public class ZookeeperOperationService {
    /**
     * 连接zookeeper
     */
    public ZooKeeper connect(String connectString,int timeOut) throws IOException {
        try {
            return new ZooKeeper(connectString, timeOut, null);
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
            if("/".equals(nodeData.getPath())) {
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
     * 创建节点
     */
    public NodeData addNode(ZooKeeper zooKeeper,String path,String nodeValue,List<ACL> acl,CreateMode createMode){
        try {
            zooKeeper.create(path, nodeValue.getBytes(), acl, createMode);
            NodeData nodeData = new NodeData();
            nodeData.setPath(path);
            nodeData.setNodeValue(nodeValue);
            return nodeData;
        } catch (KeeperException | InterruptedException e) {
            Notifier.notify("创建节点出错："+e.getMessage(), MessageType.ERROR);
            Thread.currentThread().interrupt();
        }
        return null;
    }

    /**
     * 删除节点
     */
    public void deleteNode(ZooKeeper zooKeeper,String path) {
        try {
            zooKeeper.delete(path, -1);
        } catch (InterruptedException | KeeperException e) {
            Notifier.notify("删除节点出错：" + e.getMessage(), MessageType.ERROR);
            Thread.currentThread().interrupt();
        }
    }


    /**
     * 设置控制权限
     */
    public void setAcl(NodeData nodeData,ZooKeeper zooKeeper) throws InterruptedException, KeeperException {
        Stat stat = new Stat();
        List<ACL> aclList = zooKeeper.getACL(nodeData.getPath(), stat);

        //设置控制权限
        nodeData.setAclList(aclList.stream()
                .map(acl->new NodeData.Acl(acl.getId().getId(),getAclPerms(acl.getPerms()),acl.getId().getScheme())).collect(Collectors.toList()));
    }

    private String getAclPerms(int perms) {
        String binaryString = Integer.toBinaryString(perms);
        List<String> result = new ArrayList<>();
        char[] charArray = binaryString.toCharArray();
        if (charArray[0] == '1') {
            result.add("create");
        }
        if(charArray.length > 1 && charArray[1] == '1') {
            result.add("read");
        }
        if(charArray.length > 2 && charArray[2] == '1') {
            result.add("write");
        }
        if(charArray.length > 3 && charArray[3] == '1') {
            result.add("delete");
        }
        if(charArray.length > 4 && charArray[4] == '1') {
            result.add("admin");
        }
        return String.join(",",result);
    }
}
