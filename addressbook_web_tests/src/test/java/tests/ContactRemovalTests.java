package tests;

import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

public class ContactRemovalTests extends TestBase {

    @Test
    public void canRemoveContact() {
        if (app.hbm().getContactCount() == 0) {
            app.contacts().createContact(new ContactData("", "", "", "", "", "", "", "", "", "", "", ""));
        }
        var oldContacts = app.hbm().getContactList();
        var rnd = new Random();
        var index = rnd.nextInt(oldContacts.size());
        app.contacts().removeContact(oldContacts.get(index));
        var newContacts = app.hbm().getContactList();
        var expectedList = new ArrayList<>(oldContacts);
        expectedList.remove(index);
        Assertions.assertEquals(newContacts.size(), expectedList.size());
    }

    @Test
    void canRemoveAllContactsAtOnce() {
        if (app.hbm().getContactCount() == 0) {
            app.contacts().createContact(new ContactData("", "firstname", "", "", "", "", "", "", "", "", "", ""));
        }
        app.contacts().removeAllContacts();
        Assertions.assertEquals(0, app.hbm().getContactCount());
    }
    @Test
    void canRemoveContactFromGroup(){
        if (app.hbm().getContactCount() == 0) {
            app.contacts().createContact(new ContactData("", "contact name", "", "", "", "", "", "", "", "", "", ""));
        }
        if (app.hbm().getGroupCount() == 0){
            app.groups().createGroup(new GroupData("", "group name", "group header", "group footer"));
        }
        var allContacts = app.hbm().getContactList();
        var rnd = new Random();
        var index = rnd.nextInt(allContacts.size());
        var contact = allContacts.get(index);
        var group = app.hbm().getGroupList().get(0);
        if (!app.hbm().getContactsInGroup(group).contains(contact)){
       app.contacts().addContactToGroup(contact, group);
      }
        var oldRelated = app.hbm().getContactsInGroup(group);
        app.contacts().removeContactFromGroup(contact, group);
        var newRelated = app.hbm().getContactsInGroup(group);
        Assertions.assertEquals(oldRelated.size() - 1, newRelated.size());
    }
}
