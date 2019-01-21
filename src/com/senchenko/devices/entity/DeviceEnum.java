package com.senchenko.devices.entity;

public enum  DeviceEnum {
    DEVICES("devices"),
    DEVICE("device"),
    TYPE("type"),
    PORT_TYPE("port-type"),
    ID("id"),
    NAME("name"),
    ORIGIN("origin"),
    PRICE("price"),
    IS_PERIPHERAL("is-peripheral"),
    POWER_USAGE("power-usage"),
    WITH_COOLER("with-cooler"),
    COMPONENT_GROUP("component-group"),
    CRITICAL("critical");
    private String value;

    DeviceEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
