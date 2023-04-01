package com.example.zookeeperm.data;

import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.Map;

/* *
 * @author nss
 * @description 节点数据
 * @date 22:49 2023/3/28
 **/
public class NodeData {

    private String path;
    /**
     * 数据
     */
    private String data;

    private String nodeValue;
    /**
     * 元数据
     */
    private Stat metaData;

    /**
     * 权限列表
     */
    private List<Acl> ACLList;
    /**
     * 子节点列表
     */
    private List<NodeData> childrenList;

    public NodeData(String path, String nodeValue) {
        this.path = path;
        this.nodeValue = nodeValue;
    }

    @Override
    public String toString() {
        return "NodeData{" +
                "nodeValue='" + nodeValue + '\'' +
                '}';
    }

    public NodeData() {
    }

    public String getNodeValue() {
        return nodeValue;
    }

    public void setNodeValue(String nodeValue) {
        this.nodeValue = nodeValue;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Stat getMetaData() {
        return metaData;
    }

    public void setMetaData(Stat metaData) {
        this.metaData = metaData;
    }

    public List<Acl> getACLList() {
        return ACLList;
    }

    public void setACLList(List<Acl> ACLList) {
        this.ACLList = ACLList;
    }

    public List<NodeData> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<NodeData> childrenList) {
        this.childrenList = childrenList;
    }
    public static class Acl{
        private String id;
        private String perms;
        private String scheme;

        public Acl(String id, String perms, String scheme) {
            this.id = id;
            this.perms = perms;
            this.scheme = scheme;
        }

        public String getScheme() {
            return scheme;
        }

        public void setScheme(String scheme) {
            this.scheme = scheme;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPerms() {
            return perms;
        }

        public void setPerms(String perms) {
            this.perms = perms;
        }
    }

    public static class Stat{
        private long czxid;
        private long mzxid;
        private long ctime;
        private long mtime;
        private int version;
        private int cversion;
        private int aversion;
        private long ephemeralOwner;
        private int dataLength;
        private int numChildren;
        private long pzxid;

        public Stat(org.apache.zookeeper.data.Stat stat) {
            this.czxid = stat.getCzxid();
            this.mzxid = stat.getMzxid();
            this.ctime = stat.getCtime();
            this.mtime = stat.getMtime();
            this.version = stat.getVersion();
            this.cversion = stat.getCversion();
            this.aversion = stat.getAversion();
            this.ephemeralOwner = stat.getEphemeralOwner();
            this.dataLength = stat.getDataLength();
            this.numChildren = stat.getNumChildren();
            this.pzxid = stat.getPzxid();
        }

        public long getCzxid() {
            return czxid;
        }

        public void setCzxid(long czxid) {
            this.czxid = czxid;
        }

        public long getMzxid() {
            return mzxid;
        }

        public void setMzxid(long mzxid) {
            this.mzxid = mzxid;
        }

        public long getCtime() {
            return ctime;
        }

        public void setCtime(long ctime) {
            this.ctime = ctime;
        }

        public long getMtime() {
            return mtime;
        }

        public void setMtime(long mtime) {
            this.mtime = mtime;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public int getCversion() {
            return cversion;
        }

        public void setCversion(int cversion) {
            this.cversion = cversion;
        }

        public int getAversion() {
            return aversion;
        }

        public void setAversion(int aversion) {
            this.aversion = aversion;
        }

        public long getEphemeralOwner() {
            return ephemeralOwner;
        }

        public void setEphemeralOwner(long ephemeralOwner) {
            this.ephemeralOwner = ephemeralOwner;
        }

        public int getDataLength() {
            return dataLength;
        }

        public void setDataLength(int dataLength) {
            this.dataLength = dataLength;
        }

        public int getNumChildren() {
            return numChildren;
        }

        public void setNumChildren(int numChildren) {
            this.numChildren = numChildren;
        }

        public long getPzxid() {
            return pzxid;
        }

        public void setPzxid(long pzxid) {
            this.pzxid = pzxid;
        }
    }
}
