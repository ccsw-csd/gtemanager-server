package com.ccsw.gtemanager.common.criteria;

public class TernarySearchCriteria {

    private String key;
    private String key2;
    private String key3;
    private String operation;
    private Object value;

    public TernarySearchCriteria(String key, String key2, String key3, String operation, Object value) {
        this.key = key;
        this.key2 = key2;
        this.key3 = key3;
        this.operation = operation;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }

    public String getKey3() {
        return key3;
    }

    public void setKey3(String key3) {
        this.key3 = key3;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
