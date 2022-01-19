package Cinema;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CancelBookingTest {
    private CancelBooking booking;
    private Customer customer;
    private Movie movie;

    @BeforeEach
    public void setup() {
        ArrayList<String> cast = new ArrayList<>();
        HashMap<String, Integer> seatings = new HashMap<>();
        seatings.put("front", 30);
        seatings.put("middle", 30);
        seatings.put("rear", 30);
        cast.add("I.P freely");
        customer = new Customer("Oliver clothesoff", "allOfYourClothesOff");
        Movie movie = new Movie("Interstellar", "spacetime", "P", LocalDate.parse("2018-03-01"),
                    "Chris Brown", cast, "nowhere", LocalDateTime.parse("2021-11-03T10:00"),
                        "gold", seatings);
        Booking book = new Booking(movie, "front", "children", "gold", "nowhere", LocalDateTime.parse("2021-11-03T10:00"));
        booking = new CancelBooking(book, "Timeout", LocalDateTime.parse("2021-11-01T14:00"), customer);
    }

    @Test
    public void getCustomerTest() {
        Customer customer = new Customer("Oliver clothesoff", "allOfYourClothesOff");
        Customer badCust = new Customer("Oliver twist", "fake");
        assertEquals(booking.getCustomer(), customer);
        assertFalse(booking.getCustomer().equals(badCust));
    }

    @Test
    public void getBookingTest() {
        ArrayList<String> anotherCast = new ArrayList<>();
        HashMap<String, Integer> anotherSeatings = new HashMap<>();
        anotherSeatings.put("front", 30);
        anotherSeatings.put("middle", 30);
        anotherSeatings.put("rear", 30);
        anotherCast.add("I.P freely");
        Movie movieOne = new Movie("Interstellar", "spacetime", "P", LocalDate.parse("2018-03-01"),
                    "Chris Brown", anotherCast, "nowhere", LocalDateTime.parse("2021-11-03T10:00"),
                        "silver", anotherSeatings);
        Booking books = new Booking(movieOne, "front", "children", "gold", "nowhere", LocalDateTime.parse("2021-11-03T10:00"));
        assertFalse(booking.getBooking().equals(books));

        ArrayList<String> cast = new ArrayList<>();
        HashMap<String, Integer> seatings = new HashMap<>();
        seatings.put("front", 31);
        seatings.put("middle", 30);
        seatings.put("rear", 30);
        cast.add("I.P freely");
        Movie movieCopy = new Movie("Interstellar", "spacetime", "P", LocalDate.parse("2018-03-01"),
                    "Chris Brown", cast, "nowhere", LocalDateTime.parse("2021-11-03T10:00"),
                        "gold", seatings);
        Booking book = new Booking(movieCopy, "front", "children", "gold", "nowhere", LocalDateTime.parse("2021-11-03T10:00"));
        assertEquals(booking.getBooking(), book);
    }

    @Test
    public void getTimeTest() {
        LocalDateTime time = LocalDateTime.parse("2021-11-01T14:00");
        LocalDateTime badTime = LocalDateTime.parse("2021-12-01T14:00");
        assertEquals(booking.getTime(), time);
        assertFalse(booking.getTime().equals(badTime));
    }

    @Test
    public void getReasonTest() {
        String reason = "Timeout";
        String otherReason = "Payment Method Failed";
        assertEquals(booking.getReason(), reason);
        assertFalse(booking.getReason().equals(otherReason));
    }

    @Test
    public void equalsTest() {
        ArrayList<String> cast = new ArrayList<>();
        HashMap<String, Integer> seatings = new HashMap<>();
        seatings.put("front", 31);
        seatings.put("middle", 30);
        seatings.put("rear", 30);
        cast.add("I.P freely");

        Movie movieCopy = new Movie("Interstellar", "spacetime", "P", LocalDate.parse("2018-03-01"),
                    "Chris Brown", cast, "nowhere", LocalDateTime.parse("2021-11-03T10:00"),
                        "gold", seatings);

        Booking book = new Booking(movieCopy, "middle", "senior", "silver", "bankstown", LocalDateTime.parse("2021-11-04T10:00"));
        Booking bookOne = new Booking(movieCopy, "front", "senior", "silver", "bankstown", LocalDateTime.parse("2021-11-04T10:00"));
        Booking bookTwo = new Booking(movieCopy, "front", "children", "silver", "bankstown", LocalDateTime.parse("2021-11-04T10:00"));
        Booking bookThree = new Booking(movieCopy, "front", "children", "gold", "nowhere", LocalDateTime.parse("2021-11-04T10:00"));
        Booking bookFour = new Booking(movieCopy, "front", "children", "gold", "nowhere", LocalDateTime.parse("2021-11-04T10:00"));
        Booking bookFive = new Booking(movieCopy, "front", "children", "bronze", "nowhere", LocalDateTime.parse("2021-11-04T10:00"));
        assertFalse(booking.getBooking().equals(null));
        assertTrue(booking.getBooking().equals(booking.getBooking()));
        assertFalse(booking.getBooking().equals(book));
        assertFalse(booking.getBooking().equals(bookOne));
        assertFalse(booking.getBooking().equals(bookTwo));
        assertFalse(booking.getBooking().equals(bookThree));
        assertFalse(booking.getBooking().equals(bookFour));
        assertFalse(booking.getBooking().equals(bookFive));
    }
}
