package app.mailbook.tests;

import app.mailbook.model.ContactData;
import app.mailbook.model.Contacts;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTest extends TestBase {

    @DataProvider(name = "data")
    public Iterator<Object[]> validContacts() {
        List<Object[]> list = new ArrayList<>();
        list.add(new Object[]{new ContactData().withFirstName("testName01").withAddress("testAddress01").withPostcode("testPostcode01").withCity("testCity01").withCountry("US")});
//        list.add(new Object[]{new ContactData().withFirstName("testName02").withPostcode("testPostcode02").withCity("testCity02")});

        return list.iterator();
    }

    @Test(groups = "Contacts", dataProvider = "data")
    public void testContactCreation(ContactData contact) {
        Contacts before = app.contact().all();
        app.contact().create(contact);
        assertThat(app.contact().count(), equalTo(before.size() + 1));
        Contacts after = app.contact().all();

        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToLong(c -> c.getId()).max().getAsLong()))));
    }
}
