package test.senchenko.devices.parser;

import com.senchenko.devices.parser.DeviceStaxParser;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DeviceStaxParserTest {
    DeviceStaxParser deviceStaxParser;
    String sourceFileName;

    @BeforeMethod
    public void setUp() {
        deviceStaxParser = new DeviceStaxParser();
        sourceFileName = "input/devices.xml";
    }
    @AfterMethod
    public void tearDown() {
        deviceStaxParser = null;
        sourceFileName = null;
    }
    @Test
    public void testCreateListDevices() {
        deviceStaxParser.createListDevices(sourceFileName);
        int actual = deviceStaxParser.getDevices().getDevice().size();
        int expected = 16;
        Assert.assertEquals(actual, expected);
    }
}