package com.example.zookeeperm.service;

import com.example.zookeeperm.data.LoginData;
import com.example.zookeeperm.data.NodeData;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.zookeeperm.data.LoginData.zooKeeper;

/* *
 * @author nss
 * @description 登录
 * @date 14:39 2023/3/26
 **/
public class Login {
    public static NodeData login() throws IOException, InterruptedException, KeeperException {
        //todo 添加登录信息
        LoginData.ip = "101.33.208.136";
        LoginData.port = "2181";
        LoginData.timeout = 500000;

        ZookeeperOperationService zookeeperOperationService = new ZookeeperOperationService();
        zooKeeper = zookeeperOperationService.connect(LoginData.ip + ":" + LoginData.port, LoginData.timeout);
        NodeData nodeData = new NodeData("/","/");
        zookeeperOperationService.getAllNode(nodeData,zooKeeper);
        return nodeData;
    }

    public static void main(String[] args)throws IOException, InterruptedException, KeeperException {
        LoginData.ip = "101.33.208.136";
        LoginData.port = "2181";
        LoginData.timeout = 500000;

        ZookeeperOperationService zookeeperOperationService = new ZookeeperOperationService();
        zooKeeper = zookeeperOperationService.connect(LoginData.ip + ":" + LoginData.port, LoginData.timeout);
        NodeData nodeData = new NodeData("/","/");
        Stat stat = new Stat();
        List<ACL> aclList = zooKeeper.getACL("/", stat);

        //设置控制权限
        nodeData.setACLList(aclList.stream()
                .map(acl->new NodeData.Acl(acl.getId().getId(),acl.getId().getScheme(),String.valueOf(acl.getPerms()))).collect(Collectors.toList()));
        System.out.println(nodeData);
        zooKeeper.close();
    }


}
