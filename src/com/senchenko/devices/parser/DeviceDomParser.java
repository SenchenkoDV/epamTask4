package com.senchenko.devices.parser;

import com.senchenko.devices.entity.Device;
import com.senchenko.devices.entity.Devices;
import com.senchenko.devices.entity.Type;
import com.senchenko.devices.entity.TypeDevice;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class DeviceDomParser extends AbstractDeviceParser{
    private final static Logger logger = LogManager.getLogger();
    private Devices devices;
    private DocumentBuilder documentDeviceBuilder;

    public DeviceDomParser(){
        this.devices = new Devices();
        DocumentBuilderFactory deviceFactory = DocumentBuilderFactory.newInstance();
        try {
            documentDeviceBuilder = deviceFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.log(Level.ERROR, "Error parser configuration " + e);
        }
    }
    public Devices getDevices() {
        return devices;
    }
    public void createListDevices(String sourceFileName){
        Document deviceDocument;
        try {
            deviceDocument = documentDeviceBuilder.parse(sourceFileName);
            Element rootElement =deviceDocument.getDocumentElement();
            NodeList devicesList = rootElement.getElementsByTagName("device");
            for (int i = 0; i < devicesList.getLength(); i++) {
                devices.getDevice().add(createDevice((Element) devicesList.item(i)));
            }
        } catch (SAXException e) {
            logger.log(Level.ERROR, "Parsing error " + e);
        } catch (IOException e) {
            logger.log(Level.ERROR, "I/O error" + e);
        }
    }
    private Device createDevice(Element deviceElement){
        Device device = new Device();
        Type deviceType = new Type();
        device.setName(getElementContent(deviceElement, "name"));
        device.setOrigin(getElementContent(deviceElement, "origin"));
        device.setPrice(new BigDecimal(getElementContent(deviceElement, "price")));
        device.setId(new BigInteger(deviceElement.getAttribute("id")));
        device.setCritical(Boolean.parseBoolean(getElementContent(deviceElement, "critical")));
        Element typeElement = (Element) deviceElement.getElementsByTagName("type").item(0);
        deviceType.setIsPeripheral(Boolean.parseBoolean(getElementContent(typeElement, "is-peripheral")));
        deviceType.setPowerUsage(getElementContent(typeElement, "power-usage"));
        deviceType.setWithCooler(Boolean.parseBoolean(getElementContent(typeElement, "with-cooler")));
        deviceType.setComponentGroup(TypeDevice.fromValue(getElementContent(typeElement, "component-group")));
        deviceType.setPortType(typeElement.getAttribute("port-type"));
        device.setType(deviceType);
        return device;
    }
    private static String getElementContent(Element element, String elementName){
        return element.getElementsByTagName(elementName).item(0).getTextContent();
    }
}
