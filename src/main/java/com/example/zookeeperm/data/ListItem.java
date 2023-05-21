package com.example.zookeeperm.data;

/**
 * @author nss
 */
public class ListItem {
    private String key;
    private String value;
    private Boolean isBlack;

    public ListItem() {
        this.isBlack = true;
    }

    public ListItem(String key, String value) {
        this.key = key;
        this.value = value;
        this.isBlack = false;
    }

    public String getKey() {
        return key;
    }

    public Boolean getBlack() {
        return isBlack;
    }

    public void setBlack(Boolean black) {
        isBlack = black;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
