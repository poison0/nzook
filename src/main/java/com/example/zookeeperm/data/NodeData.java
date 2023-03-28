package com.example.zookeeperm.data;

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
    /**
     * 元数据
     */
    private Map<String,String> metaDataList;

    /**
     * 权限列表
     */
    private List<Acl> ACLList;
    /**
     * 子节点列表
     */
    private List<NodeData> childrenList;

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

    public Map<String, String> getMetaDataList() {
        return metaDataList;
    }

    public void setMetaDataList(Map<String, String> metaDataList) {
        this.metaDataList = metaDataList;
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
}
