package Cinema;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    @Test
    public void canBookTest() {
        User user = new User();
        assertFalse(user.canBook());
    }

    @Test
    public void canEditTest() {
        User user = new User();
        assertFalse(user.canEdit());
    }

    @Test
    public void canManageTest() {
        User user = new User();
        assertFalse(user.canManageStaff());
    }
}
