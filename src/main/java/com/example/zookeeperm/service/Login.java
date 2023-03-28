package com.example.zookeeperm.service;

import com.example.zookeeperm.data.LoginData;
import com.example.zookeeperm.data.NodeData;
import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.List;

/* *
 * @author nss
 * @description 登录
 * @date 14:39 2023/3/26
 **/
public class Login {
    public static void login() throws IOException, InterruptedException, KeeperException {
        //todo 添加登录信息
        LoginData.ip = "101.33.208.136";
        LoginData.port = "2181";
        LoginData.timeout = 50000;

        ZookeeperOperationService zookeeperOperationService = new ZookeeperOperationService();
        LoginData.zooKeeper = zookeeperOperationService.connect(LoginData.ip + ":" + LoginData.port, LoginData.timeout);

        List<String> children = LoginData.zooKeeper.getChildren("/", false);
        for (String child : children) {
            System.out.println(child);
        }
        LoginData.zooKeeper.close();

    }

    public static void main(String[] args)throws IOException, InterruptedException, KeeperException {
        LoginData.ip = "101.33.208.136";
        LoginData.port = "2181";
        LoginData.timeout = 50000;

        ZookeeperOperationService zookeeperOperationService = new ZookeeperOperationService();
        LoginData.zooKeeper = zookeeperOperationService.connect(LoginData.ip + ":" + LoginData.port, LoginData.timeout);
        NodeData nodeData = new NodeData();
        nodeData.setPath("/");
        zookeeperOperationService.getAllNode(nodeData,LoginData.zooKeeper);
        System.out.println(nodeData);
        LoginData.zooKeeper.close();
    }


}
