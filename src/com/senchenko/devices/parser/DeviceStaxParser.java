package com.senchenko.devices.parser;

import com.senchenko.devices.entity.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.xml.stream.*;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class DeviceStaxParser extends AbstractDeviceParser{
    private final static Logger logger = LogManager.getLogger();
    private final static String DASH = "-";
    private final static String UNDERSCORE = "_";
    private Devices devices = new Devices();
    private XMLInputFactory xmlInputFactory;

    public DeviceStaxParser(){
        xmlInputFactory = XMLInputFactory.newInstance();
    }
    public Devices getDevices(){
        return devices;
    }
    public  void createListDevices(String sourceFileName){
        FileInputStream fileInputStream = null;
        XMLStreamReader xmlStreamReader;
        String name = null;
        try {
            fileInputStream = new FileInputStream(new File(sourceFileName));
            xmlStreamReader = xmlInputFactory.createXMLStreamReader(fileInputStream);
            while (xmlStreamReader.hasNext()){
                int kind = xmlStreamReader.next();
                if (kind == XMLStreamConstants.START_ELEMENT){
                    name = xmlStreamReader.getLocalName();
                }
                if (DeviceEnum.valueOf(name.replace(DASH, UNDERSCORE).toUpperCase()) == DeviceEnum.DEVICE){
                    Device device = createDevice(xmlStreamReader);
                    devices.getDevice().add(device);
                }
            }
        } catch (FileNotFoundException e) {
            logger.log(Level.ERROR, "File not found " + e);
        } catch (XMLStreamException e) {
            logger.log(Level.ERROR, "Error stax parser " + e);
        } finally {
            try {
                if (fileInputStream != null){
                    fileInputStream.close();
                }
            } catch (IOException e) {
                logger.log(Level.ERROR, "Can't close file " + e);
            }
        }
    }

    private Device createDevice(XMLStreamReader xmlStreamReader) throws XMLStreamException {
        Device device = new Device();
        String name;
        while (xmlStreamReader.hasNext()){
            if (xmlStreamReader.isStartElement()){
                device.setId(new BigInteger(xmlStreamReader.getAttributeValue(null, DeviceEnum.ID.getValue())));
            }
            int kind = xmlStreamReader.next();
            switch (kind){
                case XMLStreamConstants.START_ELEMENT:
                    name = xmlStreamReader.getLocalName();
                    switch (DeviceEnum.valueOf(name.replace(DASH, UNDERSCORE).toUpperCase())){
                        case NAME:
                            device.setName(getText(xmlStreamReader));
                            break;
                        case ORIGIN:
                            device.setOrigin(getText(xmlStreamReader));
                            break;
                        case PRICE:
                            device.setPrice(new BigDecimal(getText(xmlStreamReader)));
                            break;
                        case TYPE:
                            device.setType(getType(xmlStreamReader));
                            break;
                        case CRITICAL:
                            device.setCritical(Boolean.parseBoolean(getText(xmlStreamReader)));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = xmlStreamReader.getLocalName();
                    if (DeviceEnum.valueOf(name.replace(DASH, UNDERSCORE).toUpperCase()) == DeviceEnum.DEVICE){
                        return device;
                    }
                    break;
            }
        }
        throw new XMLStreamException("Unknown element");
    }

    private Type getType(XMLStreamReader xmlStreamReader) throws XMLStreamException {
        Type type = new Type();
        int kind;
        String name;
        type.setPortType(xmlStreamReader.getAttributeValue(null, DeviceEnum.PORT_TYPE.getValue()));
        while (xmlStreamReader.hasNext()){
            kind = xmlStreamReader.next();
            switch (kind){
                case XMLStreamConstants.START_ELEMENT:
                    name = xmlStreamReader.getLocalName();
                    switch (DeviceEnum.valueOf(name.replace(DASH, UNDERSCORE).toUpperCase())){
                        case IS_PERIPHERAL:
                            type.setIsPeripheral(Boolean.parseBoolean(getText(xmlStreamReader).replace(DASH,UNDERSCORE)));
                            break;
                        case POWER_USAGE:
                            type.setPowerUsage(getText(xmlStreamReader).replace(DASH,UNDERSCORE));
                            break;
                        case WITH_COOLER:
                            type.setWithCooler(Boolean.parseBoolean(getText(xmlStreamReader).replace(DASH,UNDERSCORE)));
                            break;
                        case COMPONENT_GROUP:
                            type.setComponentGroup(TypeDevice.fromValue(getText(xmlStreamReader).replace(DASH,UNDERSCORE)));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = xmlStreamReader.getLocalName();
                    if (DeviceEnum.valueOf(name.replace(DASH, UNDERSCORE).toUpperCase()) == DeviceEnum.TYPE){
                        return type;
                    }
                    break;
            }
        }
        throw new XMLStreamException("Unknown element");
    }
    private String getText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }
}
