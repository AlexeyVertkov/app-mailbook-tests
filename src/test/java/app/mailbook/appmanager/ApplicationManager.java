package app.mailbook.appmanager;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class ApplicationManager {
    private final Properties properties;
    RemoteWebDriver driver;

    public final String browser;
    public final String driverType;

    private SessionHelper sessionHelper;
    private NavigationHelper navigationHelper;
    private ContactHelper contactHelper;

    public ApplicationManager(String browser, String driverType) {
        this.browser = browser;
        this.driverType = driverType;
        properties = new Properties();
    }

    public void init(String testName) throws IOException {
        properties.load(new FileReader("src/test/resources/local.properties"));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss");
        LocalDateTime currentTime = LocalDateTime.now();
        if (browser.equals("firefox") && driverType.equals("remote")) {
            FirefoxOptions options = new FirefoxOptions();
            options.setCapability("selenoid:options", new HashMap<String, Object>() {{
                put("name", testName);
                put("sessionTimeout", "2m");
                put("enableVideo", true);
                put("videoName", String.format("%s_%s_%s.mp4", dtf.format(currentTime), browser, testName));
                put("enableVNC", true);
                put("env", new ArrayList<String>() {{
                    add("TZ=UTC");
                }});
            }});
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
        } else if (browser.equals("chrome") && driverType.equals("remote")) {
            ChromeOptions options = new ChromeOptions();
            options.setCapability("selenoid:options", new HashMap<String, Object>() {{
                put("name", testName);
                put("sessionTimeout", "2m");
                put("enableVideo", true);
                put("videoName", String.format("%s_%s_%s.mp4", dtf.format(currentTime), browser, testName));
                put("enableVNC", true);
                put("env", new ArrayList<String>() {{
                    add("TZ=UTC");
                }});
            }});
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);

        } else if (browser.equals("firefox") && driverType.equals("local")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser.equals("chrome") && driverType.equals("local")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(properties.getProperty("web.baseUrl"));

        contactHelper = new ContactHelper(driver);
        navigationHelper = new NavigationHelper(driver);
        sessionHelper = new SessionHelper(driver);

        sessionHelper.login(properties.getProperty("web.login"), properties.getProperty("web.password"));
    }

    public ContactHelper contact() {
        return contactHelper;
    }

    public NavigationHelper goTo() {
        return navigationHelper;
    }

    public void stop() {
        driver.quit();
    }
}
