package com.senchenko.devices.parser;

import com.senchenko.devices.entity.*;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.EnumSet;

public class DeviceHandler extends DefaultHandler {
    private final static String DASH = "-";
    private final static String UNDERSCORE = "_";
    private Devices devices;
    private Device currentDevice;
    private DeviceEnum currentEnum;
    private EnumSet<DeviceEnum> withText;

    public DeviceHandler() {
        devices = new Devices();
        withText = EnumSet.range(DeviceEnum.NAME, DeviceEnum.CRITICAL);
    }
    public Devices getDevices() {
        return devices;
    }
    public void startElement(String uri, String localName, String qName, Attributes attrs){
        if ("device".equals(localName)) {
            currentDevice = new Device();
            currentDevice.setId(new BigInteger(attrs.getValue(0)));
        }else if("type".equals(localName)){
            currentDevice.setType(new Type());
            currentDevice.getType().setPortType(attrs.getValue(0));
        } else {
            DeviceEnum temp = DeviceEnum.valueOf(localName.replace(DASH, UNDERSCORE).toUpperCase());
            if (withText.contains(temp)) {
                currentEnum = temp;
            }
        }
    }
    public void endElement(String uri, String localName, String qName){
        if ("device".equals(localName)){
            devices.getDevice().add(currentDevice);
        }
    }
    public void characters(char[] ch, int start, int length){
        String s = new String(ch, start, length).trim();
        if (currentEnum != null){
            switch (currentEnum){
                case NAME:
                    currentDevice.setName(s);
                    break;
                case ORIGIN:
                    currentDevice.setOrigin(s);
                    break;
                case PRICE:
                    currentDevice.setPrice(new BigDecimal(s));
                    break;
                case CRITICAL:
                    currentDevice.setCritical(Boolean.parseBoolean(s));
                    break;
                case IS_PERIPHERAL:
                    currentDevice.getType().setIsPeripheral(Boolean.parseBoolean(s));
                    break;
                case POWER_USAGE:
                    currentDevice.getType().setPowerUsage(s);
                    break;
                case WITH_COOLER:
                    currentDevice.getType().setWithCooler(Boolean.parseBoolean(s));
                    break;
                case COMPONENT_GROUP:
                    currentDevice.getType().setComponentGroup(TypeDevice.fromValue(s));
                    break;
                default:
                    throw new EnumConstantNotPresentException(
                            currentEnum.getDeclaringClass(), currentEnum.name());
            }
        }
        currentEnum = null;
    }
}
