package app.mailbook.tests;

import app.mailbook.appmanager.ApplicationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class TestBase {

    protected final ApplicationManager app = new ApplicationManager(System.getProperty("browser", "chrome"), System.getProperty("driverType", "local"));
    final Logger logger = LoggerFactory.getLogger(TestBase.class);

    @BeforeMethod
    public void beforeMethod(Method m) throws IOException {
        String testName = m.getName();
        app.init(testName);
        logger.info("Start test " + testName);
    }

    @AfterMethod
    public void afterMethod(Method m, Object[] p) {
        app.stop();
        logger.info("Stop test " + m.getName() + " with parameters " + Arrays.asList(p));
    }
}
