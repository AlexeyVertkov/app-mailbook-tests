package app.mailbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

public class NavigationHelper extends HelperBase {

    public NavigationHelper(RemoteWebDriver driver) {
        super(driver);
    }

    public void importPage() {
        wait(By.cssSelector("div.addresses"));
        click(By.cssSelector("a.name"));
        click(By.xpath("//li[4]"));
        click(By.cssSelector("button"));
        System.out.println(driver.getClass());
        String driverClass = String.valueOf(driver.getClass());
        if (driverClass.contains("RemoteWebDriver")) {
            driver.setFileDetector(new LocalFileDetector());
        }
    }
}
