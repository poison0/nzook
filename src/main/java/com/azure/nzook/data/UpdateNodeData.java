package com.azure.nzook.data;

import com.azure.nzook.constant.PermissionEnum;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;

import java.util.ArrayList;
import java.util.List;

/**
 * @auth nss
 */
public class UpdateNodeData {

    private String parentNodeName;
    private String nodeName;
    private String nodeValue;

    private CreateMode createMode;

    private Long ttl;

    private String scheme;

    private String id;

    private List<PermissionEnum> permsList;

    public UpdateNodeData() {
        parentNodeName = "";
        nodeName = "";
        nodeValue = "";
        createMode = CreateMode.PERSISTENT;
        scheme = "world";
        id = "anyone";
        permsList = PermissionEnum.getPermissionList();
    }

    public List<CheckBoxOptionDto> getPermsOptionList() {
        List<CheckBoxOptionDto> list = new ArrayList<>();
        for (PermissionEnum permissionEnum : PermissionEnum.values()) {
            CheckBoxOptionDto dto;
            if(permsList.contains(permissionEnum)) {
                dto = new CheckBoxOptionDto(permissionEnum.getName(), true);
            } else {
                dto = new CheckBoxOptionDto(permissionEnum.getName());
            }
            list.add(dto);
        }
        return list;
    }

    @Override
    public String toString() {
        return "UpdateNodeData{" +
                "parentNode='" + parentNodeName + '\'' +
                ", nodeName='" + nodeName + '\'' +
                ", nodeValue='" + nodeValue + '\'' +
                ", createMode=" + createMode +
                ", ttl=" + ttl +
                ", scheme='" + scheme + '\'' +
                ", id='" + id + '\'' +
                ", permsList=" + permsList +
                '}';
    }

    public List<ACL> getAcl() {
        List<ACL> acl = new ArrayList<>();
        Id sc = new Id(this.getScheme(),this.getId());
        acl.add(new ACL(PermissionEnum.getPerms(this.getPermsList()), sc));
        return acl;
    }

    public String getParentNodeName() {
        return parentNodeName;
    }

    public void setParentNodeName(String parentNodeName) {
        this.parentNodeName = parentNodeName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeValue() {
        return nodeValue;
    }

    public void setNodeValue(String nodeValue) {
        this.nodeValue = nodeValue;
    }

    public CreateMode getCreateMode() {
        return createMode;
    }

    public void setCreateMode(CreateMode createMode) {
        this.createMode = createMode;
    }

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
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

    public List<PermissionEnum> getPermsList() {
        return permsList;
    }

    public void setPermsList(List<PermissionEnum> permsList) {
        this.permsList = permsList;
    }
}
