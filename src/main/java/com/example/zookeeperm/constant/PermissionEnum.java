package com.example.zookeeperm.constant;
/**
 * @auth nss
 */
public enum PermissionEnum {
    READ("Read"),
    WRITE("Write"),
    CREATE("Create"),
    DELETE("Delete"),
    ADMIN("Admin");

    private final String name;

    PermissionEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
