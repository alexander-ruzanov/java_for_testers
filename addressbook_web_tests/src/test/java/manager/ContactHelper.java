package manager;

import model.ContactData;
import model.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import static jdk.internal.agent.Agent.getText;

public class ContactHelper extends HelperBase {
    public ContactHelper(ApplicationManager manager) {
        super(manager);
    }

    public void createContact(ContactData contact, GroupData group) {
        initContactCreation();
        fillContactForm(contact);
        selectGroup(group);
        submitContactCreation();
        openHomePage();
    }

    public void selectGroup(GroupData group) {
        new Select(manager.driver.findElement(By.name("new_group"))).selectByValue(group.id());
    }

    public void createContact(ContactData contact) {
        initContactCreation();
        fillContactForm(contact);
        submitContactCreation();
        openHomePage();
    }

    public void removeContact(ContactData contact) {
        selectContact(contact);
        removeSelectedContact();
        openHomePage();
    }

    public void openHomePage() {
            click(By.id("logo"));
    }

    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    private void fillContactForm(ContactData contact) {
        type(By.name("firstname"), contact.firstname());
        type(By.name("lastname"), contact.lastname());
        type(By.name("address"), contact.address());
        type(By.name("home"), contact.home());
        type(By.name("email"), contact.email());
        //      attach(By.name("photo"), contact.photo());
    }

    private void submitContactCreation() {
        click(By.name("submit"));
    }

    private void selectContact(ContactData contact) {
        click(By.cssSelector(String.format("input[value='%s']", contact.id())));
    }

    private void removeSelectedContact() {
        click(By.xpath("//input[@value='Delete']"));
    }

    public int getCount() {
        openHomePage();
        return manager.driver.findElements(By.name("selected[]")).size();
    }

    private void selectAllContacts() {
        click(By.id("MassCB"));
    }

    public void removeAllContacts() {
        openHomePage();
        selectAllContacts();
        removeSelectedContact();
    }

    public List<ContactData> getList() {
        var contacts = new ArrayList<ContactData>();
        var trows = manager.driver.findElements(By.cssSelector("tr[name=\"entry\"]"));
        for (var tr : trows) {
            var cells = tr.findElements(By.tagName("td"));
            var lastname = cells.get(1).getText();
            var firstname = cells.get(2).getText();
            var checkbox = tr.findElement(By.name("selected[]"));
            var id = checkbox.getAttribute("value");
            contacts.add(new ContactData().withId(id).withLastName(lastname).withFirstName(firstname));
        }
        return contacts;
    }

    public void modifyContact(ContactData contact, ContactData modifiedContact) {
        openHomePage();
        selectContact(contact);
        initContactModification(contact);
        fillContactForm(modifiedContact);
        submitContactModification();
        openHomePage();
    }

    private void initContactModification(ContactData contact) {
        click(By.xpath(String.format("//a[@href='edit.php?id=%s']", contact.id())));
    }

    private void submitContactModification() {
        click(By.name("update"));
    }

    public void addContactToGroup(ContactData contact, GroupData group) {
        openHomePage();
        selectContact(contact);
        selectGroupOnHomePage(group);
        addToGroup();
        openHomePage();
    }

    public void addToGroup() {
        click(By.xpath("//input[@value=\'Add to\']"));
    }

    public void selectGroupOnHomePage(GroupData group) {
        new Select(manager.driver.findElement(By.name("to_group"))).selectByValue(group.id());
    }

    public void removeContactFromGroup(ContactData contact, GroupData group) {
        openHomePage();
        selectGroupsOnHomePage(group);
        selectContact(contact);
        initRemoveContactFromGroup();
        openHomePage();
    }

    public void initRemoveContactFromGroup() {
        click(By.name("remove"));
    }
    public void selectGroupsOnHomePage(GroupData group) {
        new Select(manager.driver.findElement(By.name("group"))).selectByValue(group.id());
    }

    public String getPhones(ContactData contact) {
        return manager.driver.findElement(By.xpath(
                String.format("//input[@id='%s']/../../td[6]", contact.id()))).getText();
    }


    public Map<String, String> getPhones() {
        var result = new HashMap<String, String>();
       List<WebElement> rows = manager.driver.findElements(By.name("entry"));
       for (WebElement row : rows) {
           var id = row.findElement(By.tagName("input")).getAttribute("id");
           var phones = row.findElements(By.tagName("td")).get(5).getText();
           result.put(id, phones);
       }
       return result;
    }
    public Map<String, String> getAddress() {
        var result = new HashMap<String, String>();
        List<WebElement> rows = manager.driver.findElements(By.name("entry"));
        for (WebElement row : rows) {
            var id = row.findElement(By.tagName("input")).getAttribute("id");
            var address = row.findElements(By.tagName("td")).get(3).getText();
            result.put(id, address);
        }
        return result;
    }
    public Map<String, String> getEmails() {
        var result = new HashMap<String, String>();
        List<WebElement> rows = manager.driver.findElements(By.name("entry"));
        for (WebElement row : rows) {
            var id = row.findElement(By.tagName("input")).getAttribute("id");
            var emails = row.findElements(By.tagName("td")).get(4).getText();
            result.put(id, emails);
        }
        return result;
    }


}
