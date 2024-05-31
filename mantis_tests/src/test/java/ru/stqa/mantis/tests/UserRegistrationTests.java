package ru.stqa.mantis.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.stqa.mantis.common.CommonFunctions;

import java.time.Duration;
import java.util.regex.Pattern;

public class UserRegistrationTests extends TestBase{
    @Test
    void canRegisterUser(){
        var username = CommonFunctions.randomString(5);
        var email = String.format("%s@localhost", username);

        // создать пользователя на почтовом сервере (JamesHelper)
        app.jamesCli().addUser(email,"password");

        // заполянем форму создания и отправляем (браузер)
        app.session().register(username, email);

        // ждём и получаем почту (MailHelper)
        var messages = app.mail().receive(email, "password", Duration.ofSeconds(10));
        Assertions.assertEquals(1, messages.size());
        System.out.println(messages);

        // извлекаем ссылку из письма
        var text = messages.get(0).content();
        var pattern = Pattern.compile("http://\\S*");
        var matcher = pattern.matcher(text);
        if (matcher.find()) {
            var url = text.substring(matcher.start(), matcher.end());
            System.out.println(url);

            // проходим по ссылке и завершаем регистрацию пользователя (браузер)
            var password = "password";
            app.session().confirmRegister(url, username, password);
        }
        // проверяем, что пользователь может залогиниться (HttpSessionHelper)
        app.http().login(username, "password");
        Assertions.assertTrue(app.http().isLoggedIn());
    }
}
