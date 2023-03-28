package com.example.zookeeperm.data;

import org.apache.zookeeper.ZooKeeper;

/* *
 * @author nss
 * @description 登录信息
 * @date 15:20 2023/3/26
 **/
public class LoginData {
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
}
