package ru.stqa.mantis.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.stqa.mantis.common.CommonFunctions;
import ru.stqa.mantis.model.DeveloperMailUser;

import java.time.Duration;

public class UserRegistrationTests extends TestBase{
    DeveloperMailUser user;
 //   @Test
//    void canRegisterUser(){
//        var username = CommonFunctions.randomString(5);
//        var email = String.format("%s@localhost", username);
//
//        // создать пользователя на почтовом сервере (JamesHelper)
//        app.jamesApi().addUser(email,"password");
//
//        // заполянем форму создания и отправляем (браузер)
//        app.session().register(username, email);
//
//        // ждём и получаем почту (MailHelper)
//        var messages = app.mail().receive(email, "password", Duration.ofSeconds(10));
//        Assertions.assertEquals(1, messages.size());
//        System.out.println(messages);
//
//        // извлекаем ссылку из письма
//        var text = messages.get(0).content();
//        var pattern = Pattern.compile("http://\\S*");
//        var matcher = pattern.matcher(text);
//        if (matcher.find()) {
//            var url = text.substring(matcher.start(), matcher.end());
//            System.out.println(url);
//
//            // проходим по ссылке и завершаем регистрацию пользователя (браузер)
//            var password = "password";
//            app.session().confirmRegister(url, username, password);
//        }
//        // проверяем, что пользователь может залогиниться (HttpSessionHelper)
//        app.http().login(username, "password");
//        Assertions.assertTrue(app.http().isLoggedIn());
//    }
//    @Test
//    void canCreateUser(){
//        user = app.developerMail().addUser();
//        var email = String.format("%s@developermail.com", user.name());
//        var password = "password";
//
//        app.user().startCreation(user.name(), email);
//
//        var message = app.developerMail().receive(user, Duration.ofSeconds(10));
//        var url = CommonFunctions.extractUrl(message);
//         //  app.user().finishCreation(url, password);
//           app.session().confirmRegister(url, user.name(), password);
//
//        app.http().login(user.name(), "password");
//       Assertions.assertTrue(app.http().isLoggedIn());
//}
//@AfterEach
//void deleteMailUser(){
//    app.developerMail().deleteUser(user);
//    }
//}
@Test
void canAlternativeRegisterUser() {
    //Тест регистрирует новый адрес на почтовом сервере James, используя REST API.
        var realname = CommonFunctions.randomString(10);
        var password = "password";
        var username = CommonFunctions.randomString(5);
        var email = String.format("%s@localhost", username);
        app.jamesApi().addUser(email, password);
   // Сценарий начинает регистрацию нового пользователя в Mantis, используя REST API.
        app.rest().createUser(email, username, password, realname);
  // Mantis отправляет письмо на указанный адрес, тест должен получить это письмо, извлечь из него ссылку для подтверждения, пройти по этой ссылке и завершить регистрацию.
        var message = app.mail().receive(email, password, Duration.ofSeconds(10));
        Assertions.assertEquals(1, message.size());
        var text = message.get(0).content();
        var url = CommonFunctions.extractUrl(text);
        app.session().confirmRegister(url, username, password);
    //  Затем тест должен проверить, что пользователь может войти в систему с новым паролем. Этот шаг можно выполнить на уровне протокола HTTP.
        app.http().login(username, password);
        Assertions.assertTrue(app.http().isLoggedIn());
}
}