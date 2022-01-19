package Cinema;

import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MovieTest {

    private Movie endMovie;

    @BeforeEach
    public void setup() {
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

        endMovie = new Movie("Interstellar", "spaceman goes to future past and change but doesnt change the future",
                                "M", LocalDate.parse("2014-05-11"), "Christopher Nolan", cast, "Parramatta",
                                LocalDateTime.parse("2021-10-30T15:00"), "gold", seatings);
    }

    @Test
    public void setMovieTitleTest() {
        assertEquals(endMovie.getTitle(), "Interstellar");
        endMovie.setMovieTitle("JoeyJoJoJuniorShabadoo");
        assertEquals(endMovie.getTitle(), "JoeyJoJoJuniorShabadoo");
        assertFalse(endMovie.getTitle().equals("GuyIncognito"));
    }

    @Test
    public void setSynopsisTest() {
        assertEquals(endMovie.getSynopsis(), "spaceman goes to future past and change but doesnt change the future");
        endMovie.setSynopsis("JoeyJoJoJuniorShabadoo");
        assertEquals(endMovie.getSynopsis(), "JoeyJoJoJuniorShabadoo");
        assertFalse(endMovie.getSynopsis().equals("GuyIncognito"));
    }

    @Test
    public void setRating() {
        assertEquals(endMovie.getRating(), "M");
        endMovie.setRating("JoeyJoJoJuniorShabadoo");
        assertEquals(endMovie.getRating(), "JoeyJoJoJuniorShabadoo");
        assertFalse(endMovie.getRating().equals("GuyIncognito"));
    }

    @Test
    public void setReleaseDate() {
        assertEquals(endMovie.getReleaseDate().toString(), "2014-05-11");
        endMovie.setReleaseDate(LocalDate.parse("2015-06-06"));
        assertEquals(endMovie.getReleaseDate().toString(), "2015-06-06");
        assertFalse(endMovie.getReleaseDate().toString().equals("2014-05-11"));
    }

    @Test
    public void setDirector() {
        assertEquals(endMovie.getDirector(), "Christopher Nolan");
        endMovie.setDirector("Steven Spielberg");
        assertEquals(endMovie.getDirector(), "Steven Spielberg");
        assertFalse(endMovie.getDirector().equals("CHristopher Nolan"));
    }

    @Test
    public void setCast() {
        //assertEquals(endMovie.getCast(), "Christopher Nolan");
        //endMovie.setDirector("Steven Spielberg");
        //assertEquals(endMovie.getDirector(), "Steven Spielberg");
        //assertFalse(endMovie.getDirector().equals("Christopher Nolan"));
    }

    @Test
    public void setLocation() {
        assertEquals(endMovie.getLocation(), "Parramatta");
        endMovie.setLocation("Burwood");
        assertEquals(endMovie.getLocation(), "Burwood");
        assertFalse(endMovie.getLocation().equals("Parramatta"));
    }

    @Test
    public void setScreenSize() {
        assertEquals(endMovie.getScreen(), "gold");
        endMovie.setScreenSize("silver");
        assertEquals(endMovie.getScreen(), "silver");
        assertFalse(endMovie.getScreen().equals("gold"));
    }

    @Test
    public void setTime() {
        assertEquals(endMovie.getTime().toString(), "2021-10-30T15:00");
        endMovie.setTime(LocalDateTime.parse("2021-12-15T09:00"));
        assertEquals(endMovie.getTime().toString(), "2021-12-15T09:00");
        assertFalse(endMovie.getTime().toString().equals("2021-10-30T15:00"));
    }

    @Test
    public void bookSeatTest() {
        ArrayList<String> cast = new ArrayList<>();
        cast.add("Matthew McConaughey");
        cast.add("Anne Hathaway");
        cast.add("Jessica Chastain");
        cast.add("Bill Irwin");
        cast.add("Ellen Burstyn");
        cast.add("Michael Caine");

        HashMap<String, Integer> seatings = new HashMap<>();
        seatings.put("front", 1);
        seatings.put("middle", 1);
        seatings.put("rear", 1);

        Movie bookMovie = new Movie("Interstellar", "spaceman goes to future past and change but doesnt change the future",
                                "M", LocalDate.parse("2014-05-11"), "Christopher Nolan", cast, "Parramatta",
                                LocalDateTime.parse("2021-10-30T15:00"), "gold", seatings);

        assertTrue(bookMovie.bookSeat("front"));
        assertFalse(bookMovie.bookSeat("front"));
    }

    @Test
    public void equalsTest() {
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

        ArrayList<String> newCast = new ArrayList<>();
        newCast.add("Jeff");
        newCast.add("Britta");
        newCast.add("Troy");
        newCast.add("Abed");
        newCast.add("Shirley");
        newCast.add("Pierce");

        HashMap<String, Integer> newSeatings = new HashMap<>();
        newSeatings.put("front", 15);
        newSeatings.put("middle", 45);
        newSeatings.put("rear", 15);

        Movie synopsis = new Movie("Interstellar", "Wow",
                "M", LocalDate.parse("2014-05-11"), "Christopher Nolan", cast, "Parramatta",
                LocalDateTime.parse("2021-10-30T15:00"), "gold", seatings);

        Movie rating = new Movie("Interstellar", "spaceman goes to future past and change but doesnt change the future",
                "PG", LocalDate.parse("2014-05-11"), "Christopher Nolan", cast, "Parramatta",
                LocalDateTime.parse("2021-10-30T15:00"), "gold", seatings);

        Movie releaseDate = new Movie("Interstellar", "spaceman goes to future past and change but doesnt change the future",
                "M", LocalDate.parse("2015-05-11"), "Christopher Nolan", cast, "Parramatta",
                LocalDateTime.parse("2021-10-30T15:00"), "gold", seatings);

        Movie director = new Movie("Interstellar", "spaceman goes to future past and change but doesnt change the future",
                "M", LocalDate.parse("2014-05-11"), "Chris Brown", cast, "Parramatta",
                LocalDateTime.parse("2021-10-30T15:00"), "gold", seatings);

        Movie castChange = new Movie("Interstellar", "spaceman goes to future past and change but doesnt change the future",
                "M", LocalDate.parse("2014-05-11"), "Christopher Nolan", newCast, "Parramatta",
                LocalDateTime.parse("2021-10-30T15:00"), "gold", seatings);

        Movie location = new Movie("Interstellar", "spaceman goes to future past and change but doesnt change the future",
                "M", LocalDate.parse("2014-05-11"), "Christopher Nolan", cast, "Burwood",
                LocalDateTime.parse("2021-10-30T15:00"), "gold", seatings);

        Movie time = new Movie("Interstellar", "spaceman goes to future past and change but doesnt change the future",
                "M", LocalDate.parse("2014-05-11"), "Christopher Nolan", cast, "Parramatta",
                LocalDateTime.parse("2021-10-30T17:15"), "gold", seatings);

        Movie screen = new Movie("Interstellar", "spaceman goes to future past and change but doesnt change the future",
                "M", LocalDate.parse("2014-05-11"), "Christopher Nolan", cast, "Parramatta",
                LocalDateTime.parse("2021-10-30T15:00"), "silver", seatings);

        Movie seats = new Movie("Interstellar", "spaceman goes to future past and change but doesnt change the future",
                "M", LocalDate.parse("2014-05-11"), "Christopher Nolan", cast, "Parramatta",
                LocalDateTime.parse("2021-10-30T15:00"), "gold", newSeatings);

        Movie copy = new Movie("Interstellar", "spaceman goes to future past and change but doesnt change the future",
                "M", LocalDate.parse("2014-05-11"), "Christopher Nolan", cast, "Parramatta",
                LocalDateTime.parse("2021-10-30T15:00"), "gold", seatings);

        assertEquals(copy, endMovie);
        assertFalse(endMovie.equals(null));
        assertFalse(endMovie.equals(rating));
        assertFalse(endMovie.equals(synopsis));
        assertFalse(endMovie.equals(director));
        assertFalse(endMovie.equals(location));
        assertFalse(endMovie.equals(screen));
        assertFalse(endMovie.equals(time));
        assertFalse(endMovie.equals(seats));
        assertFalse(endMovie.equals(releaseDate));
        assertFalse(endMovie.equals(castChange));
    }
}
