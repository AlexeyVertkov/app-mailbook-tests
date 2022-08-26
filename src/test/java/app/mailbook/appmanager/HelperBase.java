package app.mailbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class HelperBase {
    protected final RemoteWebDriver driver;

    public HelperBase(RemoteWebDriver driver) {
        this.driver = driver;
    }

    protected void click(By locator) {
        driver.findElement(locator).click();
    }

    protected void type(By locator, String text) {
        if (text != null) {
            String existingText = driver.findElement(locator).getAttribute("value");
            if (!existingText.equals(text)) {
                driver.findElement(locator).clear();
                driver.findElement(locator).sendKeys(text);
            }
        }
    }

    protected void select(By locator, String value) {
        if (value != null) {
            Select select = new Select(driver.findElement(locator));
            select.selectByValue(value);
        }
    }

    protected void attach(By locator, File file) {
        sleep(3);
        if (!(file == null)) {
            driver.findElement(locator).sendKeys(file.getAbsolutePath());
        }
    }

    protected void acceptAlert() {
        driver.switchTo().alert().accept();
    }

    protected void typeAlert(String alertText) {
        driver.switchTo().alert().sendKeys(alertText);
        acceptAlert();
    }

    protected void wait(By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected void waitStalenessOf(By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.stalenessOf(driver.findElement(locator)));
    }

    protected void sleep(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
