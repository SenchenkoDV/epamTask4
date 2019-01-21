package test.senchenko.devices.parser;

import com.senchenko.devices.parser.DeviceDomParser;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DeviceDomParserTest {
    DeviceDomParser deviceDomParser;
    String sourceFileName;

    @BeforeMethod
    public void setUp() {
        deviceDomParser = new DeviceDomParser();
        sourceFileName = "input/devices.xml";
    }
    @AfterMethod
    public void tearDown() {
        deviceDomParser = null;
    }
    @Test
    public void testCreateListDevices() {
        deviceDomParser.createListDevices(sourceFileName);
        int actual = deviceDomParser.getDevices().getDevice().size();
        int expected = 16;
        Assert.assertEquals(actual, expected);
    }
}