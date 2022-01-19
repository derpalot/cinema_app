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

public class StaffTest {

    private Cinema cinema;
    private Staff staff;

    @BeforeEach
    public void setup() throws IOException, ParseException {
        cinema = new Cinema("src/test/resources/customer.json", "src/test/resources/staff.json", "src/test/resources/manager.json",
                "src/test/resources/credit_cards.json",
                "src/test/resources/giftCards.json",
                "src/test/resources/movies.csv",
                "src/test/resources/booking.json");

        staff = new Staff("peterparker", "spiderman");
    }

    @Test
    public void canBookTest() {
        assertTrue(staff.canBook());
    }

    @Test
    public void canEditTest() {
        assertTrue(staff.canEdit());
    }
    
    @Test
    public void addGiftCardTest() {

        GiftCard gc = new GiftCard("0234567890123456GC", true, 50.0);
        ArrayList<GiftCard> currentGiftCards = new ArrayList<GiftCard>();
        currentGiftCards.add(gc);
        ArrayList<GiftCard> updatedGiftCards = new ArrayList<GiftCard>();
        assertEquals(0, updatedGiftCards.size(), "addGiftCardTest1 FAILED!");

        // adding a new giftcard
        updatedGiftCards = staff.addGiftCard("1234567890123456GC", true, 50.0, currentGiftCards);
        assertEquals(2, updatedGiftCards.size(), "addGiftCardTest2 FAILED!");

        // adding duplicate giftcard
        updatedGiftCards = staff.addGiftCard("0234567890123456GC", true, 500.0, currentGiftCards);
        assertNull(updatedGiftCards, "addGiftCardTest3 FAILED!");

    }

    @Test
    public void redeemGiftCardTest() {

        GiftCard gc = new GiftCard("0234567890123456GC", true, 50.0);
        ArrayList<GiftCard> currentGiftCards = new ArrayList<GiftCard>();
        currentGiftCards.add(gc);
        ArrayList<GiftCard> updatedGiftCards;

        //redeeming existing giftcard
        updatedGiftCards = staff.redeemGiftCard("0234567890123456GC", currentGiftCards);
        assertFalse(updatedGiftCards.get(0).getStatus(), "redeemGiftCardTest1 FAILED!");

        // try to redeem card that doesnt exist
        updatedGiftCards = staff.redeemGiftCard("1234567890123456GC", currentGiftCards);
        assertNull(updatedGiftCards, "redeemGiftCardTest2 FAILED!");
    }

    @Test
    public void addMovieTest() throws IOException {
        ArrayList<Movie> currentMovies = cinema.getMovieListAll();
        ArrayList<Movie> updatedMovies;

        // adding a movie with same info as one already in the cinema
        Movie oldMovie = currentMovies.get(0);
        updatedMovies = staff.addMovie(oldMovie, currentMovies);

        // movie should not have been successfully added as it already exists
        assertNull(updatedMovies);

        Movie newMovie = new Movie("Last Christmas", "A new christmas movie",
                "PG", LocalDate.parse("2021-12-25"), "Jacqui O'Sullivan", new ArrayList<String>(),
                "Burwood", LocalDateTime.parse("2021-10-26T17:00"), "bronze", new HashMap<String, Integer>());
        updatedMovies = staff.addMovie(newMovie, currentMovies);

        // movie should have been added successfully
        // because it's not already being played in the cinema
        assertNotNull(updatedMovies);
        assertTrue(updatedMovies.get(updatedMovies.size()-1).equals(newMovie));

        ArrayList<Movie> newList = staff.addMovie(null, currentMovies);
        assertNull(newList);
    }

    @Test
    public void deleteMovieTest() throws IOException {
        ArrayList<Movie> currentMovies = cinema.getMovieListAll();
        ArrayList<Movie> updatedMovies;

        // deleting a movie not already in the cinema
        Movie newMovie = new Movie("Last Christmas", "A new christmas movie",
                "PG", LocalDate.parse("2021-12-25"), "Jacqui O'Sullivan", new ArrayList<String>(),
                "Burwood", LocalDateTime.parse("2021-10-26T17:00"), "bronze", new HashMap<String, Integer>());
        updatedMovies = staff.deleteMovie(newMovie, currentMovies);

        // movie should not have been successfully deleted as it does not exist
        //assertNull(updatedMovies);

        // deleting a movie currently in the cinema
        Movie oldMovie = currentMovies.get(0);
        updatedMovies = staff.deleteMovie(oldMovie, currentMovies);

        // movie should have been deleted successfully
        // because it was previously playing in the cinema
        assertNotNull(updatedMovies);
        assertFalse(updatedMovies.contains(oldMovie));

        ArrayList<Movie> newList = staff.deleteMovie(null, currentMovies);
        assertNull(newList);
    }

    @Test
    public void modifyTitleTest() {
        ArrayList<Movie> currentMovies = cinema.getMovieListAll();
        Movie movie = currentMovies.get(0);
        String movieTitle = movie.getTitle();
        assertEquals(movieTitle, "Batman Begins");

        // modifying the title of a movie in the cinema
        currentMovies = staff.modifyTitle(movie, currentMovies, "Batman & Robin");
        Movie modifiedMovie = currentMovies.get(0);
        String modifiedTitle = modifiedMovie.getTitle();
        assertEquals(modifiedTitle, "Batman & Robin");
        // the movie title has changed
        assertNotEquals(movieTitle, modifiedTitle);

        assertNull(staff.modifyTitle(null, currentMovies, "Batman & Robin"));
    }

    @Test
    public void modifySynopsisTest() {
        ArrayList<Movie> currentMovies = cinema.getMovieListAll();

        Movie movie = currentMovies.get(0);
        String movieSynopsis = movie.getSynopsis();
        assertEquals(movieSynopsis, "Man scared of bats become bat");

        // modifying the synopsis of a movie in the cinema
        currentMovies = staff.modifySynopsis(movie, currentMovies, "The bats take over");
        Movie modifiedMovie = currentMovies.get(0);
        String modifiedSynopsis = modifiedMovie.getSynopsis();
        assertEquals(modifiedSynopsis, "The bats take over");
        // the movie synopsis has changed
        assertNotEquals(movieSynopsis, modifiedSynopsis);

        assertNull(staff.modifySynopsis(null, currentMovies, "The bats take over"));
    }

    @Test
    public void modifyRatingTest() {
        ArrayList<Movie> currentMovies = cinema.getMovieListAll();

        Movie movie = currentMovies.get(1);
        String movieRating = movie.getRating();
        assertEquals(movieRating, "M");

        // modifying the rating of a movie in the cinema
        currentMovies = staff.modifyRating(movie, currentMovies, "G");
        Movie modifiedMovie = currentMovies.get(1);
        String modifiedRating = modifiedMovie.getRating();
        assertEquals(modifiedRating, "G");
        // the movie rating has changed
        assertNotEquals(movieRating, modifiedRating);

        assertNull(staff.modifyRating(null, currentMovies, "M"));
    }

    @Test
    public void modifyReleaseDateTest() {
        ArrayList<Movie> currentMovies = cinema.getMovieListAll();

        Movie movie = currentMovies.get(1);
        LocalDate movieReleaseDate = movie.getReleaseDate();
        assertEquals(movieReleaseDate, LocalDate.parse("2005-06-15"));

        // modifying the release date of a movie in the cinema
        // with a valid date
        currentMovies = staff.modifyReleaseDate(movie, currentMovies, LocalDate.parse("2007-05-16"));
        Movie modifiedMovie = currentMovies.get(1);
        LocalDate modifiedReleaseDate = modifiedMovie.getReleaseDate();
        assertEquals(modifiedReleaseDate, LocalDate.parse("2007-05-16"));
        // the release date has changed
        assertNotEquals(movieReleaseDate, modifiedReleaseDate);

        ArrayList<Movie> newList = staff.modifyReleaseDate(null, currentMovies, LocalDate.parse("2007-05-16"));
        assertNull(newList);
    }

    @Test
    public void modifyDirectorTest() {
        ArrayList<Movie> currentMovies = cinema.getMovieListAll();

        Movie movie = currentMovies.get(2);
        String movieDirector = movie.getDirector();
        assertEquals(movieDirector, "Christopher Nolan");

        // modifying the director of a movie in the cinema
        currentMovies = staff.modifyDirector(movie, currentMovies, "Steven Spielberg");
        Movie modifiedMovie = currentMovies.get(2);
        String modifiedDirector = modifiedMovie.getDirector();
        assertEquals(modifiedDirector, "Steven Spielberg");
        // the director's name has changed
        assertNotEquals(movieDirector, modifiedDirector);

        ArrayList<Movie> newList = staff.modifyDirector(null, currentMovies, "No Name");
        assertNull(newList);
    }

    @Test
    public void modifyCastTest() {
        ArrayList<Movie> currentMovies = cinema.getMovieListAll();

        Movie movie = currentMovies.get(7);
        ArrayList<String> castList = movie.getCast();
        //assertEquals(8, castList.size());

        // adding a cast member to this movie
        currentMovies = staff.addCastMember(movie, currentMovies, "Colin Firth");
        Movie modifiedMovie = currentMovies.get(7);
        ArrayList<String> modifiedCast = modifiedMovie.getCast();
        //assertEquals(9, modifiedCast.size());
        //assertEquals(modifiedCast.get(8), "Colin Firth");

        // deleting a cast member from the movie
        currentMovies = staff.deleteCastMember(movie, currentMovies, "Scarlett Johansson");
        //modifiedMovie = currentMovies.get(7);
        modifiedCast = modifiedMovie.getCast();
        //assertEquals(8, modifiedCast.size());

        // deleting a nonexistent cast member
        //currentMovies = staff.deleteCastMember(movie, currentMovies, "Not A Cast Member");
        //assertNull(currentMovies);

        ArrayList<Movie> newList = staff.addCastMember(null, currentMovies, "No name");
        assertNull(newList);
        ArrayList<Movie> newList2 = staff.deleteCastMember(null, currentMovies, "No name");
        assertNull(newList2);
        
    }

    @Test
    public void deleteGiftCardTest() {
        ArrayList<GiftCard> giftCards = cinema.getGiftCardList();
        GiftCard toDelete = giftCards.get(0);
        int intialSize = giftCards.size();

        ArrayList<GiftCard> newList = staff.deleteGiftCard(toDelete, giftCards);
        int updatedSize = newList.size();
        
        assertEquals(intialSize-1, updatedSize);
        
        GiftCard gc = new GiftCard("0234567890123456GC", true, 50.0);
        ArrayList<GiftCard> newList2 = staff.deleteGiftCard(gc, giftCards);
        assertNull(newList2);
        
        ArrayList<GiftCard> newList3 = staff.deleteGiftCard(null, giftCards);
        assertNull(newList3);
    }
}
