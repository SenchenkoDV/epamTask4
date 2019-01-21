package com.senchenko.devices.parser;

import com.senchenko.devices.entity.Devices;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import java.io.IOException;

public class DeviceSaxParser extends AbstractDeviceParser{
    private final static Logger logger = LogManager.getLogger();
    private Devices devices;
    private DeviceHandler deviceHandler;
    private XMLReader xmlReader;
    public DeviceSaxParser(){
        deviceHandler = new DeviceHandler();
        try {
            xmlReader = XMLReaderFactory.createXMLReader();
            xmlReader.setContentHandler(deviceHandler);
        } catch (SAXException e) {
            logger.log(Level.ERROR, "Error sax parser " + e);
        }
    }
    public Devices getDevices() {
        return devices;
    }
    public void createListDevices(String sourceFileName){
        try {
            xmlReader.parse(sourceFileName);
        } catch (IOException e) {
            logger.log(Level.ERROR, "Error Sax parser " + e);
        } catch (SAXException e) {
            logger.log(Level.ERROR, "Error I/O " + e);
        }
        devices = deviceHandler.getDevices();
    }
}
