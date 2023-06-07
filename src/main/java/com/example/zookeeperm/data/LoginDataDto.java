package com.example.zookeeperm.data;

/**
 * @auth nss
 */
public class LoginDataDto {
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

    private Boolean isSave;

    public Boolean getSave() {
        return isSave;
    }

    public void setSave(Boolean save) {
        isSave = save;
    }

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
