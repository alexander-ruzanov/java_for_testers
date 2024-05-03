package tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.CommonFunctions;
import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

    public static List<ContactData> contactProvider() throws IOException {
        var result = new ArrayList<ContactData>();
//        for (var firstname : List.of("", "contact firstname")){
//            for (var lastname : List.of("", "contact lastname")){
//                for (var address : List.of("", "contact address")){
//                    for (var home : List.of("", "contact home")) {
//                        for (var email : List.of("", "contact email")) {
//                            result.add(new ContactData().withFirstName(firstname).withLastName(lastname).withAddress(address));
//                        }
//                    }
//                }
//            }
//        }
        ObjectMapper mapper = new ObjectMapper();
        var value = mapper.readValue(new File("contacts.json"), new TypeReference<List<ContactData>>() {});
        result.addAll(value);
        return result;
    }
    @Test
    void canCreateContact(){
        var contact = new ContactData()
                .withFirstName(CommonFunctions.randomString(10))
                .withLastName(CommonFunctions.randomString(10))
                .withPhoto(randomFile("src/test/resources/images"));
        app.contacts().createContact(contact);
    }
    public static List<ContactData> negativeContactProvider() {
        var result = new ArrayList<ContactData>(List.of(
                new ContactData("", "contact name'", "", "", "", "", "")));
        return result;
    }
    public static List<ContactData> singleRandomContact() {
        return List.of(new ContactData().withFirstName(CommonFunctions.randomString(10))
                .withLastName(CommonFunctions.randomString(20))
                .withAddress(CommonFunctions.randomString(30)));
    }
    @ParameterizedTest
    @MethodSource("singleRandomContact")
    public void canCreateContact(ContactData contact) {
        var oldContacts = app.hbm().getContactList();
        app.contacts().createContact(contact);
        var newContacts = app.hbm().getContactList();
        Comparator<ContactData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newContacts.sort(compareById);
        var expectedList = new ArrayList<>(oldContacts);
        expectedList.add(contact.withId(newContacts.get(newContacts.size() - 1).id()));
        expectedList.sort(compareById);
        Assertions.assertEquals(newContacts, expectedList);
    }

    @ParameterizedTest
    @MethodSource("negativeContactProvider")
    public void canNotCreateContact(ContactData contact) {
        var oldContacts = app.contacts().getList();
        app.contacts().createContact(contact);
        var newContacts = app.contacts().getList();
        Assertions.assertEquals(oldContacts, newContacts);
    }
}
