package com.senchenko.devices.parser;

public class DeviceBuilderFactory {
    private enum TypeParser{
        DOM, SAX, STAX
    }
    public AbstractDeviceParser createDeviceParser(String typeParser){
        AbstractDeviceParser abstractDeviceParser;
        TypeParser type = TypeParser.valueOf(typeParser.toUpperCase());
        switch (type){
            case DOM:
                abstractDeviceParser = new DeviceDomParser();
                break;
            case SAX:
                abstractDeviceParser = new DeviceSaxParser();
                break;
            case STAX:
                abstractDeviceParser = new DeviceStaxParser();
                break;
            default:
                throw new EnumConstantNotPresentException(type.getDeclaringClass(), type.name());
        }
        return abstractDeviceParser;
    }
}
