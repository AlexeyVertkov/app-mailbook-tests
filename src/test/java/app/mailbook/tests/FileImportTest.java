package app.mailbook.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class FileImportTest extends TestBase {

    @Test(groups = "Contacts")
    public void testImportMailbookFile() {
        int before = app.contact().count();
        app.goTo().importPage();
        File file = new File("src/test/resources/dump.xlsx");
        app.contact().importContactsFile(file);
        int importedContacts = app.contact().count();
        app.contact().confirmAttachedFile();
        int after = app.contact().count();

        assertThat(after, equalTo(before + importedContacts));
    }

    @AfterMethod
    public void clearContacts() {
        app.contact().deleteAllContacts();
    }
}
