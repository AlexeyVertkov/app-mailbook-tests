package app.mailbook.tests;

import app.mailbook.model.ContactData;
import app.mailbook.model.Contacts;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTest extends TestBase {

    @Test(groups = "Contacts")
    public void testContactModification() {
        if (app.contact().count() == 0) {
            app.contact().create((new ContactData().withFirstName("NewTestContact")));
        }
        Contacts before = app.contact().all();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData()
                .withId(modifiedContact.getId()).withFirstName("ModifiedName");
        app.contact().modify(contact);
        assertThat(app.contact().count(), equalTo(before.size()));
        Contacts after = app.contact().all();

        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    }
}