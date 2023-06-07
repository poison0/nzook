package com.example.zookeeperm.service;

import com.example.zookeeperm.data.ListItem;
import com.example.zookeeperm.data.LoginData;
import com.example.zookeeperm.data.LoginDataDto;
import com.example.zookeeperm.data.NodeData;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.zookeeperm.data.LoginData.zooKeeper;
import static com.example.zookeeperm.data.LoginData.zookeeperOperationService;

/* *
 * @author nss
 **/
public class Login {
    public static NodeData login(LoginDataDto data) throws IOException, InterruptedException, KeeperException {
        zooKeeper = zookeeperOperationService.connect(data.getIp() + ":" + data.getPort(), data.getTimeout());
        NodeData nodeData = new NodeData("/","/");
        zookeeperOperationService.getAllNode(nodeData,zooKeeper);
        zookeeperOperationService.setAcl(nodeData,zooKeeper);
        return nodeData;
    }
}
