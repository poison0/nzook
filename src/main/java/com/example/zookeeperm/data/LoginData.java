package com.example.zookeeperm.data;

import com.example.zookeeperm.constant.StatusEnum;
import com.example.zookeeperm.service.ZookeeperOperationService;
import org.apache.zookeeper.ZooKeeper;

/* *
 * @author nss
 **/
public class LoginData {

    /**
     * 状态
     **/
    private static StatusEnum status = StatusEnum.NOT_SAVE;
    /*
     * @description 端口号
     **/
    public static String port;
    /* *
     * @description ip地址
     **/
    public static String ip;

    /**
     * 超时时间
     */
    public static Integer timeout;
    /**
     * zookeeper链接
     */
    public static ZooKeeper zooKeeper;

    public static ZookeeperOperationService zookeeperOperationService = new ZookeeperOperationService();

    public static void setStatus(StatusEnum status) {
        LoginData.status = status;
    }

    public static StatusEnum getStatus() {
        return status;
    }
}
