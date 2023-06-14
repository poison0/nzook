package com.azure.nzook.data;

import com.azure.nzook.service.ZookeeperOperationService;
import com.azure.nzook.constant.StatusEnum;
import org.apache.zookeeper.ZooKeeper;

/**
 * @author niu
 * @version 1.0
 */
public class LoginData {

    /**
     * 状态
     **/
    private static StatusEnum status = StatusEnum.NOT_CONNECT;
    /**
     * zookeeper链接
     */
    public static ZooKeeper zooKeeper;


    public static LoginDataDto loginData;

    public static final ZookeeperOperationService zookeeperOperationService = new ZookeeperOperationService();

    public static void setStatus(StatusEnum status) {
        LoginData.status = status;
    }

    public static StatusEnum getStatus() {
        return status;
    }
}
