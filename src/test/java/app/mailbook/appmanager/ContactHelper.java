package app.mailbook.appmanager;

import app.mailbook.model.ContactData;
import app.mailbook.model.Contacts;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.time.Instant;
import java.util.List;

public class ContactHelper extends HelperBase {

    public ContactHelper(RemoteWebDriver driver) {
        super(driver);
    }

    private Contacts contactCache = null;

    public void closeConfirmationAlert() {
        wait(By.cssSelector("div.addresses"));
        click(By.cssSelector("div.close > svg"));
        driver.navigate().refresh();
    }

    public void submitContactCreation() {
        click(By.cssSelector(".end > button"));
    }

    public void fillContactForm(ContactData contactData) {
        type(By.cssSelector("#firstName"), contactData.getFirstName());
        if (contactData.getLabel() != null) {
            type(By.xpath("//input[contains(@id,'react-select')]"), contactData.getLabel() + Keys.ENTER);
        }
        type(By.cssSelector("#streetname"), contactData.getAddress());
        type(By.cssSelector("#postcode"), contactData.getPostcode());
        type(By.cssSelector("#city"), contactData.getCity());
        select(By.cssSelector("#country"), contactData.getCountry());
    }

    public void selectContactById(long id) {
        Instant instant = Instant.ofEpochMilli(id);
        String datetime = instant.toString().replaceAll("Z", ".000Z");
        driver.findElement(By.cssSelector("time[datetime='" + datetime + "']")).click();
    }

    public void create(ContactData contactData) {
        initContactCreation();
        fillContactForm(contactData);
        submitContactCreation();
        contactCache = null;
        closeConfirmationAlert();
    }

    public void modify(ContactData contact) {
        selectContactById(contact.getId());
        fillContactForm(contact);
        submitContactCreation();
        contactCache = null;
        closeConfirmationAlert();
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        click(By.cssSelector(".delete"));
        acceptAlert();
        contactCache = null;
        closeConfirmationAlert();
    }

    public void deleteAllContacts() {
        click(By.cssSelector(".end > button.white"));
        click(By.xpath("//div[@class='field row end addressbook']/button[2]"));
        click(By.cssSelector("button.warning"));
        typeAlert("yes");
        waitStalenessOf(By.cssSelector("button.warning"));
    }

    public void initContactCreation() {
        click(By.cssSelector("div.addressbook > button"));
    }

    public int count() {
        wait(By.cssSelector("div.addresses"));
        return driver.findElements(By.cssSelector("tr:not(.header)")).size();
    }

    public Contacts all() {
        wait(By.cssSelector("div.addresses"));
        if (contactCache != null) {
            return new Contacts(contactCache);
        }
        contactCache = new Contacts();
        List<WebElement> elements = driver.findElements(By.cssSelector("tr:not(.header)"));

        for (WebElement element : elements) {
            String name = element.findElement(By.cssSelector(".to")).getText();
            Instant instant = Instant.parse(element.findElement(By.cssSelector("time[datetime]")).getAttribute("datetime"));
            long id = instant.toEpochMilli();
            contactCache.add(new ContactData().withId(id).withFirstName(name));
        }
        return new Contacts(contactCache);
    }

    public void importContactsFile(File file) {
        String originalWindow = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.equals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        attach(By.cssSelector("#input"), file);
        driver.switchTo().window(originalWindow);
    }

    public void confirmAttachedFile() {
        click(By.cssSelector("button:not(.disabled)"));
        click(By.cssSelector("button > span"));
        wait(By.cssSelector("tbody"));
        driver.navigate().refresh();
    }
}