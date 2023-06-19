package com.azure.nzook.constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author niu
 * @since 1.0
 */
public enum PermissionEnum {
    READ("Read", 1),
    WRITE("Write",1 << 1),
    CREATE("Create",1 << 2),
    DELETE("Delete",1 << 3),
    ADMIN("Admin",1 << 4);

    private final String name;
    private final int value;

    PermissionEnum(String name,int value) {
        this.name = name;
        this.value = value;
    }

    public static int getPerms(List<PermissionEnum> permissionEnums) {
        int value = 0;
        for (PermissionEnum permissionEnum : permissionEnums) {
            value = value | permissionEnum.getValue();
        }
        return value;
    }

    public static List<PermissionEnum> getPerms(String permsName) {
        if(permsName == null || permsName.isBlank()) {
            return Collections.emptyList();
        }
        return Arrays.stream(PermissionEnum.values()).filter(permissionEnum -> permsName.contains(permissionEnum.getName())).collect(Collectors.toList());
    }


    public static PermissionEnum getByName(String text) {
        return Arrays.stream(PermissionEnum.values()).filter(permissionEnum -> permissionEnum.getName().equals(text)).findFirst().orElse(null);
    }

    public static List<PermissionEnum> getPermissionList() {
        return Arrays.asList(PermissionEnum.values());
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
