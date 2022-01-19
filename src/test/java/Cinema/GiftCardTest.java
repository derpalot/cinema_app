package Cinema;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GiftCardTest {

    private GiftCard giftCard;
    private GiftCard falseCard;

    @BeforeEach
    public void setup() {
        giftCard = new GiftCard("5081752983674488GC", true, 0.00);
        falseCard = new GiftCard("2837518232123157GC", false, 100.00);
    }

    @Test
    public void redeemTest() {

        assertTrue(giftCard.getStatus(), "Status is not true but should be!");

        assertTrue(giftCard.redeem(), "Status did not change from true to false!");

        assertFalse(giftCard.getStatus(), "Status is not false but should be!");

        assertFalse(falseCard.getStatus());
        assertFalse(falseCard.redeem());
    }

    @Test
    public void setBalanceTest() {
        assertEquals(giftCard.getBalance(), 0);
        giftCard.setBalance(100.0);
        assertEquals(giftCard.getBalance(), 100);
    }

    @Test
    public void useCardTest() {
       assertFalse(falseCard.useCard(120.0));
       assertTrue(falseCard.useCard(80.0));
       assertEquals(falseCard.getBalance(), 20);
    }

    @Test
    public void setNumberTest() {
        giftCard = new GiftCard("5081752983674488GC", true, 0.00);
        assertEquals("5081752983674488GC", giftCard.getNumber());
        giftCard.setNumber("9876543210987654GC");
        assertEquals("9876543210987654GC", giftCard.getNumber());
    }

    @Test
    public void setStatusTest() {
        assertTrue(giftCard.getStatus());
        giftCard.setStatus(false);
        assertFalse(giftCard.getStatus());
    }

    @Test
    public void isValidTest() {
        GiftCard invalid1 = new GiftCard(null, true, 1.00);
        assertFalse(invalid1.isValid());
        GiftCard invalid2 = new GiftCard("5081752983674488", true, 1.00);
        assertFalse(invalid2.isValid());
        GiftCard invalid3 = new GiftCard("5081752983674488GC", true, null);
        assertFalse(invalid3.isValid());
        GiftCard invalid4 = new GiftCard("ABC1752983674488GC", true, 20.00);
        assertFalse(invalid4.isValid());

        GiftCard valid = new GiftCard("5081752983674488GC", true, 1.00);
        assertTrue(valid.isValid());
    }
}
