package manager;

import model.ContactData;
import org.openqa.selenium.By;

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
    public void removeContact() {
        selectContact();
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
    }
    private void submitContactCreation() {
        click(By.name("submit"));
    }
    private void selectContact() {
        click(By.name("selected[]"));
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
}
