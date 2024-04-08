package tests;

import model.GroupData;
import org.junit.jupiter.api.Test;

public class GroupCreationTests extends TestBase {


    @Test
    public void canCreateGroup() {
        app.groups().CreateCroup(new GroupData("group name", "group header", "group footer"));
    }

    @Test
    public void canCreateGroupWithEmptyName() {
        app.groups().CreateCroup(new GroupData());

    }
    @Test
    public void canCreateGroupWithNameOnly() {
        app.groups().CreateCroup(new GroupData().withName("some name"));

    }
}
