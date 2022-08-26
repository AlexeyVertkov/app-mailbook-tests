package app.mailbook.tests;

import app.mailbook.model.ContactData;
import app.mailbook.model.Contacts;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTest extends TestBase {

    @Test(groups = "Contacts")
    public void testContactDeletion() {
        if (app.contact().count() == 0) {
            app.contact().create((new ContactData().withFirstName("NewTestContact")));
        }
        Contacts before = app.contact().all();
        ContactData deletedContact = before.iterator().next();
        app.contact().delete(deletedContact);
        assertThat(app.contact().count(), equalTo(before.size() - 1));
        Contacts after = app.contact().all();

        assertThat(after, equalTo(before.without(deletedContact)));
    }
}
