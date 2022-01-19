package Cinema;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.io.IOException;
import org.json.simple.parser.ParseException;
import static org.junit.jupiter.api.Assertions.*;

public class CinemaTest {

    private Cinema cinema;
    private Staff staff;

    @BeforeEach
    public void setup() throws IOException, ParseException {
        cinema = new Cinema("src/test/resources/customer.json","src/test/resources/staff.json",
                "src/test/resources/manager.json", "src/test/resources/credit_cards.json",
                "src/test/resources/giftCards.json", "src/test/resources/movies.csv",
                "src/test/resources/booking.json");

        staff = new Staff("Johnny Depp", "Pirates");

    }

    @Test
    public void savingTest() throws IOException {
        cinema.saveInfo("src/test/resources/customer.json", "src/test/resources/staff.json",
                "src/test/resources/manager.json", "src/test/resources/credit_cards.json",
                "src/test/resources/giftCards.json", "src/test/resources/movies.csv",
                "src/test/resources/booking.json", "src/main/resources/cancelSummary.csv");
    }

    @Test 
    public void registerUserTest() {
        int customerSize = cinema.getCustomerList().size();
        Customer newUser = cinema.registerUser("beckham", "4321");

        int updatedSize = cinema.getCustomerList().size();
        assertNotNull(newUser);
        assertEquals(updatedSize, customerSize+1);

        //ADD user already exists
        Customer alreadyExists = cinema.registerUser("Homer Simpsons", "d0nut5");
        assertNull(alreadyExists);
    }

    @Test
    public void addMovieTest() {
        ArrayList<Movie> movieList = cinema.getMovieListAll();
        int movieSize = movieList.size();

        Movie newMovie = new Movie("Last Christmas", "A new christmas movie",
                "PG", LocalDate.parse("2021-12-25"), "Jacqui O'Sullivan", new ArrayList<String>(),
                "Burwood", LocalDateTime.parse("2021-10-26T17:00"), "bronze", new HashMap<String, Integer>());
        movieList = cinema.addMovie(newMovie, staff);

        int updatedSize = movieList.size();
        assertEquals(movieSize+1, updatedSize);

        // adding a movie already in the cinema
        Movie badMovie = cinema.getMovie("Inception", "Burwood", LocalDateTime.parse("2021-11-03T10:00"), "bronze");
        movieList = cinema.addMovie(badMovie, staff);
        assertNull(movieList);
    }

    @Test
    public void deleteMovieTest() {
        ArrayList<Movie> movieList = cinema.getMovieListAll();
        int movieSize = movieList.size();

        // deleting a movie already in the cinema
        Movie deleteMovie = cinema.getMovie("Inception", "Burwood", LocalDateTime.parse("2021-11-03T10:00"), "bronze");
        movieList = cinema.deleteMovie(deleteMovie, staff);

        //int updatedSize = movieList.size();
        //assertEquals(movieSize-1, updatedSize);

        // deleting a movie not in the cinema - unsuccessful
        Movie newMovie = new Movie("Last Christmas", "A new christmas movie",
                "PG", LocalDate.parse("2021-12-25"), "Jacqui O'Sullivan", new ArrayList<String>(),
                "Burwood", LocalDateTime.parse("2021-10-26T17:00"), "bronze", new HashMap<String, Integer>());
        movieList = cinema.deleteMovie(newMovie, staff);
        assertNull(movieList);
    }
    
    @Test
    public void getMovieInfoTest() {
        // Gets Batman begin info
        String info = cinema.getMovieInfo(cinema.getMovieListAll().get(0)); 

        assertEquals(info, "Title: Batman Begins\nRating: M\nRelease Date 2005-06-15\nDirector: Christopher Nolan\nSynopsis: Man scared of bats become bat\nCast: Christian Bale, Michael Caine, Liam Neeson, Katie Holmes, Gary Oldman, Cilian Murphy, Tom Wikinson, Rutger Hauer, Ken Watanabe, Morgan Freeman\n");
    }

    @Test
    public void getLocationMoviesTest() {
        ArrayList<Movie> movies = cinema.getLocationMovies("Parramatta");
        int moviesSize = movies.size();
        //assertEquals(21, moviesSize);

        ArrayList<Movie> none = cinema.getLocationMovies("NoWhere");
        int noneSize = none.size();
        assertEquals(0, noneSize);
    }

    @Test
    public void getScreenMoviesTest() {
        ArrayList<Movie> bronze = cinema.getScreenMovies("bronze");
        int bronzeSize = bronze.size();
        //assertEquals(12, bronzeSize);

        ArrayList<Movie> silver = cinema.getScreenMovies("silver");
        int silverSize = silver.size();
        //assertEquals(12, silverSize);

        ArrayList<Movie> gold = cinema.getScreenMovies("gold");
        int goldSize = gold.size();
        //assertEquals(21, goldSize);

        ArrayList<Movie> none = cinema.getScreenMovies("diamond");
        int noneSize = none.size();
        assertEquals(0, noneSize);
    }
    
    @Test
    public void modifySeatingsTest() {
        Movie movie = cinema.getMovieListAll().get(0);
        boolean modified = cinema.modifySeatings(movie, staff, "front", 10);
        assertTrue(modified);
        HashMap<String, Integer> test = new HashMap<>();
        test.put("rear", 27);
        test.put("middle", 27);
        test.put("front", 10);
        //assertEquals(test, movie.getSeatings());

        // Movie that's not part of list
        Movie newMovie = new Movie("Last Christmas", "A new christmas movie",
                "PG", LocalDate.parse("2021-12-25"), "Jacqui O'Sullivan", new ArrayList<String>(),
                "Burwood", LocalDateTime.parse("2021-10-26T17:00"), "bronze", new HashMap<String, Integer>());
        boolean denied = cinema.modifySeatings(newMovie, staff, "rear", 50);
        assertFalse(denied);

    }

    @Test
    public void modifyTitleTest() {
        Movie batman = cinema.getMovieListAll().get(0);
        boolean modified = cinema.modifyTitle(batman, staff, "Batman Ends");
        assertTrue(modified);
        assertEquals("Batman Ends", batman.getTitle());

        // Movie that doesn't exist
        Movie newMovie = new Movie("Last Christmas", "A new christmas movie",
                "PG", LocalDate.parse("2021-12-25"), "Jacqui O'Sullivan", new ArrayList<String>(),
                "Burwood", LocalDateTime.parse("2021-10-26T17:00"), "bronze", new HashMap<String, Integer>());
        boolean denied = cinema.modifyTitle(newMovie, staff, "Blah Blah");
        assertFalse(denied);
    }

    @Test
    public void modifySynopsisTest() {
        Movie batman = cinema.getMovieListAll().get(0);
        boolean modified = cinema.modifySynopsis(batman, staff, "Movie about bats... idk");
        assertTrue(modified);
        assertEquals("Movie about bats... idk", batman.getSynopsis());

        // Movie that doesn't exist
        Movie newMovie = new Movie("Last Christmas", "A new christmas movie",
                "PG", LocalDate.parse("2021-12-25"), "Jacqui O'Sullivan", new ArrayList<String>(),
                "Burwood", LocalDateTime.parse("2021-10-26T17:00"), "bronze", new HashMap<String, Integer>());
        boolean denied = cinema.modifySynopsis(newMovie, staff, "Blah Blah");
        assertFalse(denied);
    }

    @Test
    public void modifyRatingTest() {
        Movie batman = cinema.getMovieListAll().get(0);
        boolean modified = cinema.modifyRating(batman, staff, "G");
        assertTrue(modified);
        assertEquals("G", batman.getRating());

        // Movie that doesn't exist
        boolean denied = cinema.modifyRating(null, staff, "M");
        assertFalse(denied);
    }

    @Test 
    public void bookMovieTest() {
        ArrayList<Booking> bookFail = new ArrayList<Booking>();
        ArrayList<Booking> bookSuccess = new ArrayList<Booking>();
        ArrayList<Booking> bookBackRow = new ArrayList<Booking>();
        ArrayList<Booking> bookMixedSeats = new ArrayList<Booking>();
        ArrayList<Booking> bookFrontRow = new ArrayList<Booking>();
        ArrayList<Booking> childMid = new ArrayList<Booking>();
        ArrayList<Booking> studentBack = new ArrayList<Booking>();
        ArrayList<Booking> adultFront = new ArrayList<Booking>();
        ArrayList<Booking> senMid = new ArrayList<Booking>();

        // Movie newMovie = new Movie("Last Christmas", "A new christmas movie",
        //         "PG", LocalDate.parse("2021-12-25"), "Jacqui O'Sullivan", new ArrayList<String>(),
        //         "Burwood", LocalDateTime.parse("2021-10-26T17:00"), "bronze", new HashMap<String, Integer>());
        // cinema.addMovie(newMovie, staff);
        Movie movie = cinema.getMovieList().get(0);

        Customer customer = new Customer("Ian Snotball", "IAmASnotball");

        bookFail = cinema.bookMovie(null, 1, 1, 1, 1, 1, 1, 1, 
                        "silver", null, null, null);
        
        assertEquals(null, bookFail, "Error case was not reached");

        bookSuccess = cinema.bookMovie(movie, 1, 1, 1, 1, 2, 1, 1, 
                        "silver", null, "2021-10-26T17:00", customer);
        
        int bookSuccessSize = bookSuccess.size();
        assertEquals(4, bookSuccessSize, "bookSuccess was not made correctly");

        bookBackRow = cinema.bookMovie(movie, 3, 0, 2, 0, 0, 0, 5, 
                        "silver", null, "2021-10-26T17:00", customer);
        
        int bookBackRowSize = bookBackRow.size();
        assertEquals(5, bookBackRowSize, "bookBackRow was not made correctly");

        bookMixedSeats = cinema.bookMovie(movie, 3, 3, 3, 3, 4, 4, 4, 
                        "silver", null, "2021-10-26T17:00", customer);
        
        int bookMixedSeatsSize = bookMixedSeats.size();
        assertEquals(12, bookMixedSeatsSize, "bookMixedSeats was not made correctly");

        bookFrontRow = cinema.bookMovie(movie, 0, 2, 0, 2, 4, 0, 0, 
                        "silver", null, "2021-10-26T17:00", customer);
        
        int bookFrontRowSize = bookFrontRow.size();
        assertEquals(4, bookFrontRowSize, "bookFrontRowSize were not made correctly");

        childMid = cinema.bookMovie(movie, 1, 0, 0, 0, 0, 1, 0, 
                        "silver", null, "2021-10-26T17:00", customer);
        
        int childMidSize = childMid.size();
        assertEquals(1, childMidSize, "childMid was not made correctly");

        studentBack = cinema.bookMovie(movie, 0, 2, 0, 0, 0, 0, 2, 
                        "silver", null, "2021-10-26T17:00", customer);
        
        int studentBackSize = studentBack.size();
        assertEquals(2, studentBackSize, "studentBack was not made correctly");

        adultFront = cinema.bookMovie(movie, 0, 0, 3, 0, 3, 0, 0, 
                        "silver", null, "2021-10-26T17:00", customer);
        
        int adultFrontSize = adultFront.size();
        assertEquals(3, adultFrontSize, "adultFront was not made correctly");

        senMid = cinema.bookMovie(movie, 0, 0, 0, 2, 0, 2, 0, 
                        "silver", null, "2021-10-26T17:00", customer);
        
        int senMidSize = senMid.size();
        assertEquals(2, senMidSize, "senMid was not made correctly");
    }

    @Test
    public void loginTest() {
        Customer customer = cinema.login("Homer Simpsons");
        
        assertEquals("Homer Simpsons", customer.getUsername(), "Customer login error");

        Customer staffTest = cinema.login("Mr Staff");
        assertEquals("Mr Staff", staffTest.getUsername(), "Staff login error");
        
        Customer errorCheck = cinema.login("Nobody");
        assertEquals(null, errorCheck, "User got logged in without valid username");
    }

    @Test
    public void checkGiftCardTest() {
        String test = "1234567890123456GC";
        GiftCard gc = cinema.checkGiftCard(test);
        assertNotNull(gc, "giftCard check failed");

        String test2 = "1234GC";
        GiftCard gc2 = cinema.checkGiftCard(test2);
        assertEquals(null, gc2, "returned giftCard even though not valid");

        String test3 = "123456789012345678";
        GiftCard gc3 = cinema.checkGiftCard(test3);
        assertEquals(null, gc3,"returned giftCard even though not valid");
    }

    @Test
    public void checkCardTest() {
        String name = "Marge";
        Card card = cinema.checkCard(name);
        assertNotNull(card);

        String inv = "blah";
        Card none = cinema.checkCard(inv);
        assertEquals(null, none);
    }

    @Test
    public void alterGiftCards() {
        ArrayList<GiftCard> giftCards = cinema.getGiftCardList();

        // Add giftCard
        GiftCard gc = new GiftCard("5081752983674488GC", true, 20.00);
        boolean added = cinema.addGiftCard(gc, staff);
        assertTrue(added);

        GiftCard gc1 = new GiftCard("5081752983674488GC", true, null);
        boolean invalid = cinema.addGiftCard(gc1, staff);
        assertFalse(invalid);

        // Delete giftCard
        GiftCard toDelete = giftCards.get(0);
        int intialSize = giftCards.size();
        ArrayList<GiftCard> updatedList = cinema.deleteGiftCard(toDelete, staff);
        int updatedSize = updatedList.size();
        assertEquals(intialSize-1, updatedSize);

        ArrayList<GiftCard> nothing = cinema.deleteGiftCard(null, staff);
        assertNull(nothing);
    }

    @Test
    public void modifyReleaseDateTest() {
        ArrayList<Movie> currentMovies = cinema.getMovieListAll();
        Movie movie = currentMovies.get(1);
        LocalDate movieReleaseDate = movie.getReleaseDate();
        assertEquals(movieReleaseDate, LocalDate.parse("2005-06-15"));

        // modifying the release date of a movie in the cinema
        // with a valid date
        boolean modified = cinema.modifyReleaseDate(movie, staff, LocalDate.parse("2007-05-16"));
        assertTrue(modified);
        
        modified = cinema.modifyReleaseDate(null, staff, LocalDate.parse("2007-05-16"));
        assertFalse(modified);
        
    }

    @Test
    public void modifyDirectorTest() {
        ArrayList<Movie> currentMovies = cinema.getMovieListAll();
        Movie movie = currentMovies.get(2);
        String movieDirector = movie.getDirector();
        assertEquals(movieDirector, "Christopher Nolan");

        // modifying the director of a movie in the cinema
        boolean modified = cinema.modifyDirector(movie, staff, "Steven Spielberg");
        assertTrue(modified);
        
        modified = cinema.modifyDirector(null, staff, "The Director");
        assertFalse(modified);
    }

    @Test
    public void modifyCastTest() {
        ArrayList<Movie> currentMovies = cinema.getMovieListAll();

        Movie movie = currentMovies.get(7);
        ArrayList<String> castList = movie.getCast();
        //assertEquals(8, castList.size());

        // adding a cast member to this movie
        boolean added = cinema.addCastMember(movie, staff, "Colin Firth");
        assertTrue(added);
        // adding a null
        added = cinema.addCastMember(null, staff, "No name");
        assertFalse(added);
        // deleting a cast member from the movie
        boolean removed = cinema.deleteCastMember(movie, staff, "Scarlett Johansson");
        //assertTrue(removed);

        // deleting a nonexistent cast member
        removed = cinema.deleteCastMember(null, staff, "No name");
        assertFalse(removed);
        
    }

    @Test
    public void modifyStaffTest() {
        ArrayList<Staff> staffMembers = cinema.getStaffList();
        Manager manager = cinema.getManagerList().get(0);
        // Add staff
        int intialSize = staffMembers.size();
        ArrayList<Staff> updated = cinema.addStaff("Beckham", "password123", manager);
        int updatedSize = updated.size();
        assertEquals(intialSize+1, updatedSize);

        ArrayList<Staff> updated2 = cinema.addStaff("Mr Staff", "StaffMan", manager);
        assertEquals(updatedSize, updated2.size());

        // delete staff
        Staff member = staffMembers.get(0);
        ArrayList<Staff> deleted = cinema.deleteStaff(member, manager);
        int deletedSize = deleted.size();
        assertEquals(updatedSize-1, deletedSize);

        Staff newStaff = new Staff("Name", "password123");
        ArrayList<Staff> updated3 = cinema.deleteStaff(newStaff, manager);
        assertNull(updated3);
    }

    @Test
    public void cancelBookingTest() {
        //assertEquals(cinema.getBooking().size(), 1);
        Movie movie = cinema.getMovie("Batman Begins", "Parramatta", LocalDateTime.parse("2021-11-03T10:00"), "gold");
        //Booking booking = new Booking(movie, "front", "student", movie.getScreen(), movie.getLocation(), movie.getTime());
        ArrayList<Booking> bookingList = new ArrayList<>();
        //bookingList.add(booking);
        Customer tom = new Customer ("Joey jo", "Shabadoo");
        //cinema.cancelBooking(bookingList, "Timeout", LocalDateTime.now(), tom);
        //assertEquals(cinema.getBooking().size(), 0);
    }
}
