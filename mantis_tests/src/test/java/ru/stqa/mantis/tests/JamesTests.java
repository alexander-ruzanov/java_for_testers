package ru.stqa.mantis.tests;

import org.junit.jupiter.api.Test;
import ru.stqa.mantis.common.CommonFunctions;

public class JamesTests extends TestBase{
    @Test
    void canCreateUser() {
        app.jamesCli().addUser(
                "user1@localhost","password");

    }
}
