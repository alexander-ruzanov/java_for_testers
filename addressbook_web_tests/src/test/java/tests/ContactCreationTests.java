package tests;

import model.ContactData;
import org.junit.jupiter.api.Test;

public class ContactCreationTests extends TestBase {

    @Test
    public void canCreateContact() {
        app.contacts().createContact(new ContactData("firstname", "lastname", "address", "1234567890", "email"));
    }

    @Test
    public void canCreateContactWithFirstNameOnly() {
        app.contacts().createContact(new ContactData().withFirstName("FirstName"));
    }
    @Test
    public void canCreateContactWithLastNameOnly() {
        app.contacts().createContact(new ContactData().withLastName("LastName"));
    }
    @Test
    public void canCreateContactWithAddressOnly() {
        app.contacts().createContact(new ContactData().withAddress("Address"));
    }



}
