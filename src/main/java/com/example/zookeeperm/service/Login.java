package com.example.zookeeperm.service;

import com.example.zookeeperm.data.LoginData;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

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

        ZookeeperConnectService zookeeperConnectService = new ZookeeperConnectService();
        LoginData.zooKeeper = zookeeperConnectService.connect(LoginData.ip + ":" + LoginData.port, LoginData.timeout);

        List<String> children = LoginData.zooKeeper.getChildren("/", false);
        for (String child : children) {
            System.out.println(child);
        }
        LoginData.zooKeeper.close();

    }


}
