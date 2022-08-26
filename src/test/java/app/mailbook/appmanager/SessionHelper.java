package app.mailbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SessionHelper extends HelperBase {

    public SessionHelper(RemoteWebDriver driver) {
        super(driver);
    }

    public void login(String login, String password) {
        click(By.cssSelector("a[href='/login']"));
        type(By.cssSelector("#email"), login);
        type(By.cssSelector("#password"), password);
        click(By.cssSelector("span > span"));
    }
}
