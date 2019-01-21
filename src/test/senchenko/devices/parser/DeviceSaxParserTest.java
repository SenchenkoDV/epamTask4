package test.senchenko.devices.parser;

import com.senchenko.devices.parser.DeviceSaxParser;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DeviceSaxParserTest {
    DeviceSaxParser deviceSaxParser;
    String sourceFileName;

    @BeforeMethod
    public void setUp() {
        deviceSaxParser = new DeviceSaxParser();
        sourceFileName = "input/devices.xml";
    }
    @AfterMethod
    public void tearDown() {
        deviceSaxParser = null;
        sourceFileName = null;
    }
    @Test
    public void testCreateListDevices() {
        deviceSaxParser.createListDevices(sourceFileName);
        int actual = deviceSaxParser.getDevices().getDevice().size();
        int expected = 16;
        Assert.assertEquals(actual, expected);
    }
}