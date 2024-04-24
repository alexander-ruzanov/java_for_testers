package manager;

import model.ContactData;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {
    public ContactHelper(ApplicationManager manager) {
        super(manager);
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
        if (!manager.isElementPresent(By.name("selected[]"))) {
            click(By.linkText("home"));
        }
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
}
