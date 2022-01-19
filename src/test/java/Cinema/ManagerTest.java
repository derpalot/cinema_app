package Cinema;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.io.IOException;
import org.json.simple.parser.ParseException;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

public class ManagerTest {

    private Cinema cinema;
    private Manager manager;

    @BeforeEach
    public void setup() throws IOException, ParseException {
        cinema = new Cinema("src/test/resources/customer.json", "src/test/resources/staff.json","src/test/resources/manager.json",
                "src/test/resources/credit_cards.json",
                "src/test/resources/giftCards.json",
                "src/test/resources/movies.csv",
                "src/test/resources/booking.json");

        manager = new Manager("peterparker", "spiderman");
    }
    
    @Test
    public void canManageTest() {
        assertTrue(manager.canManageStaff());
    }

    @Test
    public void addStaffTest() {
        ArrayList<Staff> currentStaff = new ArrayList<Staff>();
        assertEquals(0, currentStaff.size(), "addStaffTest1 FAILED!");

        ArrayList<Staff> newStaff = new ArrayList<Staff>();
        ArrayList<Staff> newStaff2 = new ArrayList<Staff>();

        // adding staff
        currentStaff = manager.addStaff("Donald Trumpf", "Fake News!", currentStaff);
        assertEquals(1, currentStaff.size(), "addStaffTest2 FAILED!");

        // adding duplicate staff
        currentStaff = manager.addStaff("Donald Trumpf", "Fake News!", currentStaff);
        assertEquals(1, currentStaff.size(), "addStaffTest3 FAILED!");

        // adding another staff
        currentStaff = manager.addStaff("Twenty Dollars", "Peanuts", currentStaff);
        assertEquals(2, currentStaff.size());

        currentStaff = manager.addStaff("Donald Trumpf", "Fake News!", currentStaff);
        assertEquals(2, currentStaff.size());
    }

    @Test
    public void removeStaffTest() {

        ArrayList<Staff> currentStaff = new ArrayList<Staff>();
        Staff staff = new Staff("Jeff", "1234");
        currentStaff.add(staff);

        assertEquals(1, currentStaff.size(), "removeStaffTest1 FAILED!");


        ArrayList<Staff> newStaff = new ArrayList<Staff>();
        ArrayList<Staff> newStaff2 = new ArrayList<Staff>();

        // remove staff that doesnt exist
        newStaff = manager.removeStaff("Donald Trumpf", currentStaff);
        assertNull(newStaff, "removeStaffTest2 FAILED!");

        // remove staff that does exist
        newStaff2 = manager.removeStaff("Jeff", currentStaff);
        assertEquals(0, newStaff2.size(), "removeStaffTest3 FAILED!");
    }


}
