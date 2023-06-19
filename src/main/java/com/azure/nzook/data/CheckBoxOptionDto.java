package com.azure.nzook.data;

/**
 * @author niu
 * @since 1.0
 */
public class CheckBoxOptionDto {
    private String value;
    private Boolean isCheck;

    public CheckBoxOptionDto(String value) {
        this.value = value;
        this.isCheck = false;
    }

    public CheckBoxOptionDto(String value, Boolean isCheck) {
        this.value = value;
        this.isCheck = isCheck;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getCheck() {
        return isCheck;
    }

    public void setCheck(Boolean check) {
        isCheck = check;
    }
}
