package Cinema;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BookingTest {

    private Booking bookChild;
    private Booking bookStudent;
    private Booking bookAdult;
    private Booking bookSenior;

    @BeforeEach
    public void setup() {
        bookChild = new Booking(null, null, "child", "silver", null, null);
        bookStudent = new Booking(null, null, "student", "bronze", null, null);
        bookAdult = new Booking(null, null, "adult", "gold", null, null);
        bookSenior = new Booking(null, null, "senior", "bronze", null, null);
    }

    @Test
    public void calculateCostTest(){
        assertEquals(20.00, bookChild.getCost(), "Child booking for silver screen cost failed");

        assertEquals(16.50, bookStudent.getCost(), "Student booking for bronze screen cost failed");
        
        assertEquals(30.50, bookAdult.getCost(), "Adult booking for gold screen cost failed");
        
        assertEquals(14.50, bookSenior.getCost(), "Senior booking for bronze screen cost failed");
    }
}