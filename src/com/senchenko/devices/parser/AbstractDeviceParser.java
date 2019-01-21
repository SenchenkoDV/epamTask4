package com.senchenko.devices.parser;

import com.senchenko.devices.entity.Devices;

public abstract class AbstractDeviceParser {
    private Devices devices;
    public AbstractDeviceParser(){
        devices = new Devices();
    }
    public AbstractDeviceParser(Devices devices){
        this.devices = devices;
    }
    public Devices getDevices(){
        return devices;
    }
    abstract public void createListDevices(String sourceFileName);
}
