package com.azure.nzook.data;

import com.azure.nzook.constant.StatusEnum;
import com.azure.nzook.data.logindata.UserLoginData;
import com.azure.nzook.service.ZookeeperOperationService;
import org.apache.zookeeper.ZooKeeper;

/**
 * @author niu
 * @since 1.0
 */
public class ZookeeperData {

    private ZookeeperData() {
    }
    /**
     * 状态
     **/
    private static StatusEnum status = StatusEnum.NOT_CONNECT;
    /**
     * zookeeper链接
     */
    public static ZooKeeper zooKeeper;


    public static UserLoginData userLoginData;

    public static final ZookeeperOperationService zookeeperOperationService = new ZookeeperOperationService();

    public static void setStatus(StatusEnum status) {
        ZookeeperData.status = status;
    }

    public static String getCurrentLoginString() {
       if(userLoginData != null) {
          return userLoginData.getCurrentLoginData().getConnectString();
       }
       return "";
    }

    public static StatusEnum getStatus() {
        return status;
    }
}
