package Cinema;

import java.util.HashMap;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {
    private Movie movie;
    private Customer customer;

    @BeforeEach
    public void setup() {
        customer = new Customer("Ryan Zangari", "helicopter");

        ArrayList<String> cast = new ArrayList<>();
        cast.add("Matthew McConaughey");
        cast.add("Anne Hathaway");
        cast.add("Jessica Chastain");
        cast.add("Bill Irwin");
        cast.add("Ellen Burstyn");
        cast.add("Michael Caine");

        HashMap<String, Integer> seatings = new HashMap<>();
        seatings.put("front", 30);
        seatings.put("middle", 30);
        seatings.put("rear", 30);

        Movie movie = new Movie("Interstellar", "spaceman goes to future past and change but doesnt change the future",
                                "M", LocalDate.parse("2014-05-11"), "Christopher Nolan", cast, "Parramatta",
                                LocalDateTime.parse("2021-10-30T15:00"), "gold", seatings);
    }

    @Test
    public void canBookTest() {
        assertTrue(customer.canBook());
    }

    @Test
    public void canEditTest() {
        assertFalse(customer.canEdit());
    }

    @Test
    public void usernameCheckTest() {
        assertTrue(customer.usernameCheck("Ryan Zangari"));
        assertFalse(customer.usernameCheck("Ivana Tinkle"));
    }

    @Test
    public void bookingTest() {

        assertEquals(0, customer.getBookings().size());

        Booking booking = new Booking(movie, "middle", "senior", "gold", "nowhere", LocalDateTime.parse("2021-11-02T12:00"));

        customer.makeBooking(booking);
        assertEquals(1, customer.getBookings().size());

        customer.cancel();
        assertEquals(0, customer.getBookings().size());

    }

    @Test
    public void saveCardTest() {

        assertNull(customer.getCard());

        Card c = new Card("Ryan", "12345");
        customer.saveCardDetails(c);

        assertNotNull(customer.getCard());
    }

    @Test
    public void giftCardTest() {

        assertNull(customer.getGiftCard());

        GiftCard g = new GiftCard("5081752983674488GC", true, 12.00);
        customer.setCredit(g);

        assertNotNull(customer.getGiftCard());
    }

    @Test
    public void passwordTest() {
        assertTrue(customer.passwordCheck("helicopter"));
        assertFalse(customer.passwordCheck("elephant"));
        assertFalse(customer.passwordCheck(null));
    }

    @Test
    public void equalsTest() {
       Customer cus= new Customer("Ryan Zangari", "helicopter");
       Customer copy = new Customer("Ryan Zangari", "helicopter");
       Customer book = new Customer("Ryan Zangari", "helicopter");
       Card c = new Card("Ryan", "12345");
       copy.saveCardDetails(c);
       cus.saveCardDetails(c);
       book.saveCardDetails(c);

       Booking booking = new Booking(movie, "middle", "senior", "gold", "nowhere", LocalDateTime.parse("2021-11-02T12:00"));

       book.makeBooking(booking);

       Customer noCard = new Customer("Ryan Zangari", "helicopter");
       Customer badCard = new Customer("Ryan Zangari", "helicopter");
       Card d = new Card("Dave", "12345");
       badCard.saveCardDetails(d);
       Customer badPass = new Customer("Ryan Zangari", "train");
       assertTrue(customer.equals(customer));
       assertTrue(copy.equals(cus));
       assertFalse(customer.equals(null));
       assertFalse(customer.equals(badPass));
       assertFalse(customer.equals(badCard));
       assertFalse(copy.equals(noCard));
       assertFalse(copy.equals(book));
    }

    @Test
    public void setAutoFillTest() {
        assertFalse(customer.getAutoFill());
        
        customer.setAutoFill(true);
        assertTrue(customer.getAutoFill());
    }
}
