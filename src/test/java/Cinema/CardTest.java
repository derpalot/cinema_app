package Cinema;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    private Card card;

    @BeforeEach
    public void setup() {

        card = new Card("Ryan", "12345");
    }

    @Test
    public void checkNumberTest() {

        assertTrue(card.checkNumber("12345"), "checkNumber test 1 failed!");

        assertFalse(card.checkNumber("54321"), "checkNumber test 2 failed!");

    }

    @Test
    public void equalsTest() {
        Card wrongName = new Card("David", "00000");
        Card wrongNumber= new Card("Ryan", "00000");
        Card correct = new Card("Ryan", "12345");
        assertEquals(correct, card);
        assertFalse(card.equals(wrongName));
        assertFalse(card.equals(wrongNumber));
    }

}
