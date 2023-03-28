package com.example.zookeeperm.service;

import com.example.zookeeperm.data.LoginData;
import com.example.zookeeperm.message.Notifier;
import com.intellij.openapi.ui.MessageType;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;
import java.util.zip.ZipException;

/* *
 * @author nss
 * @description zookeeper链接
 * @date 15:08 2023/3/26
 **/
public class ZookeeperConnectService {
    public ZooKeeper connect(String connectString,int timeOut) throws IOException, InterruptedException {
        try {
            return new ZooKeeper(connectString, timeOut, null);
        } catch (IOException e) {
            e.printStackTrace();
            Notifier.notify("zookeeper链接出错："+e.getMessage(), MessageType.ERROR);
            throw e;
        }
    }
}
