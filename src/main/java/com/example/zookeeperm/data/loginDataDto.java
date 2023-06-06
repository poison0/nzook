package com.example.zookeeperm.data;

/**
 * @auth nss
 */
public class loginDataDto {
    /*
     * @description 端口号
     **/
    private String port;
    /* *
     * @description ip地址
     **/
    private String ip;

    /**
     * 超时时间
     */
    private Integer timeout;

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
}
