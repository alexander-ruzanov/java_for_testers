package ru.stqa.mantis.manager;

import org.openqa.selenium.By;

public class SessionHelper extends HelperBase {
    public SessionHelper(ApplicationManager manager){super(manager);}

    public void login(String user, String password) {
    type(By.name("username"), user);
    click(By.cssSelector("input[type='submit']"));
    type(By.name("password"), password);
    click(By.cssSelector("input[type='submit']"));
    }

    public void register(String user, String email) {
        click(By.linkText("Signup for a new account"));
        type(By.name("username"), user);
        type(By.name("email"), email);
        click(By.cssSelector("input[type='submit']"));
        click(By.linkText("Proceed"));
    }
    public void confirmRegister(String url, String user, String password) {
        manager.driver().navigate().to(url);
        type(By.name("realname"), user);
        type(By.name("password"), password);
        type(By.name("password_confirm"), password);
        click(By.xpath("//button[@type='submit']"));
    }

    public boolean isLoggedIn() {
        return isElementPresent(By.cssSelector("span.user-info"));
    }
}
