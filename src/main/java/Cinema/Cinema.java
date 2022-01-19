package Cinema;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.io.File;
import java.nio.file.Files;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class Cinema {
    private ArrayList<Movie> movieList;
    private ArrayList<Card> cardList;
    private ArrayList<Customer> customerList;
    private ArrayList<Staff> staffList;
    private ArrayList<Manager> managerList;
    private ArrayList<GiftCard> giftCardList;
    private ArrayList<CancelBooking> cancelBookings;
    private HashMap<Integer, Booking> bookingMap;
    private int transactionID;

    public Cinema(String customerFile, String staffFile, String managerFile, String cardFile, String giftCardFile,
                  String movieFile, String bookingFile) throws IOException, ParseException {
        this.transactionID = 0;
        this.cancelBookings = new ArrayList<>();
        this.movieList = new ArrayList<>();
        this.cardList = new ArrayList<>();
        this.customerList = new ArrayList<>();
        this.staffList = new ArrayList<>();
        this.managerList = new ArrayList<>();
        this.giftCardList = new ArrayList<>();
        this.bookingMap = new HashMap<>();
        this.constructMovies(movieFile);
        this.constructStaff(staffFile);
        this.constructManager(managerFile);
        this.constructCards(cardFile);
        this.constructGiftCards(giftCardFile);
        this.constructCustomers(customerFile);
        this.constructBookings(bookingFile);
    }

    /////////////////////////////////////////////////////////////////
    ///                      MOVIE FUNCTIONS                      ///
    /////////////////////////////////////////////////////////////////

    public Movie getMovie(String title, String location, LocalDateTime time ,String screen) {
        for(Movie movie: movieList) {
            if(movie.getTitle().equals(title)) {
                if(movie.getLocation().equals(location)) {
                    if(movie.getTime().equals(time)) {
                        if(movie.getScreen().equals(screen)) {
                            return movie;
                        }
                    }
                }
            }
        }
        return null;
    }

    public ArrayList<Movie> getMovieList() {
        ArrayList<Movie> upcomingMovie = new ArrayList<>();
        LocalDateTime time = LocalDateTime.now();
        int dayNum = time.getDayOfWeek().getValue();
        dayNum = 7 - dayNum;
        time = time.plusDays(dayNum);

        for(Movie movie: movieList) {
            if(movie.getTime().isBefore(time)) {
                upcomingMovie.add(movie);
            }
        }
        return upcomingMovie;
    }

    public ArrayList<Movie> getMovieListAll() {
        return this.movieList;
    }

    public String getMovieInfo(Movie movie) {
        StringBuilder stringBuilder = new StringBuilder();
        
        stringBuilder.append("Title: " + movie.getTitle() + "\n");
        stringBuilder.append("Rating: " + movie.getRating() + "\n");
        stringBuilder.append("Release Date " + movie.getReleaseDate().toString() + "\n");
        stringBuilder.append("Director: " + movie.getDirector() + "\n");
        stringBuilder.append("Synopsis: " + movie.getSynopsis() + "\n");
        stringBuilder.append("Cast: " + String.join(", ", movie.getCast()) + "\n");

        return stringBuilder.toString();
    }

    /////////////////////////////////////////////////////////////////
    ///                    CUSTOMER FUNCTIONS                     ///
    /////////////////////////////////////////////////////////////////

    public ArrayList<Customer> getCustomerList() {
        return this.customerList;
    }

    public Customer registerUser(String username, String password) {
        if(this.login(username) != null) {
            return null;
        }
        Customer newCustomer = new Customer(username, password);
        customerList.add(newCustomer);
        return newCustomer;
    }

    public Customer login(String username) {
        /* Checks the username written into the GUI are
                associated with a current customer
            Returns Customer if found otherwise null
        */

        // Check for username of customer
        for(Customer customer: customerList) {
            if (customer.getUsername().equals(username)) {
                return customer;
            }
        }

        // Check for username of staff
        for(Staff staff : staffList) {
            if(staff.getUsername().equals(username)) {
                return staff;
            }
        }

        for (Manager manager : managerList) {
            if (manager.getUsername().equals(username)) {
                return manager;
            }
        }
        return null;
    }

    public void cancelBooking(ArrayList<Booking> bookings, String reason, LocalDateTime time, Customer customer) {
        for(Booking booking: bookings) {
            for(Map.Entry<Integer,Booking> entry: bookingMap.entrySet()) {
                if(entry.getValue().equals(booking)) {
                    bookingMap.remove(entry.getKey());
                }
            }
            CancelBooking cancelBooking = new CancelBooking(booking, reason, time, customer);
            this.cancelBookings.add(cancelBooking);
        }
    }

    public HashMap<Integer, Booking> getBooking() {
        return this.bookingMap;
    }

    /////////////////////////////////////////////////////////////////
    ///                     STAFF FUNCTIONS                       ///
    /////////////////////////////////////////////////////////////////

    public ArrayList<Movie> addMovie(Movie movie, Staff staff) {
        return staff.addMovie(movie, this.movieList);
    }

    public ArrayList<Movie> deleteMovie(Movie movie, Staff staff) {
        return staff.deleteMovie(movie, this.movieList);
    }

    public boolean modifyTitle(Movie movie, Staff staff, String newTitle) {
        ArrayList<Movie> modifiedMovies = staff.modifyTitle(movie, this.movieList, newTitle);
        if (modifiedMovies == null) {
            return false;
        }
        this.movieList = modifiedMovies;
        return true;
    }

    public boolean modifySynopsis(Movie movie, Staff staff, String newSynopsis) {
        ArrayList<Movie> modifiedMovies = staff.modifySynopsis(movie, this.movieList, newSynopsis);
        if (modifiedMovies == null) {
            return false;
        }
        this.movieList = modifiedMovies;
        return true;
    }

    public boolean modifyRating(Movie movie, Staff staff, String newRating) {
        ArrayList<Movie> modifiedMovies = staff.modifyRating(movie, this.movieList, newRating);
        if (modifiedMovies == null) {
            return false;
        }
        this.movieList = modifiedMovies;
        return true;
    }

    public boolean modifyReleaseDate(Movie movie, Staff staff, LocalDate newDate) {
        ArrayList<Movie> modifiedMovies = staff.modifyReleaseDate(movie, this.movieList, newDate);
        if (modifiedMovies == null) {
            return false;
        }
        this.movieList = modifiedMovies;
        return true;
    }

    public boolean modifyDirector(Movie movie, Staff staff, String newDirector) {
        ArrayList<Movie> modifiedMovies = staff.modifyDirector(movie, this.movieList, newDirector);
        if (modifiedMovies == null) {
            return false;
        }
        this.movieList = modifiedMovies;
        return true;
    }

    public boolean addCastMember(Movie movie, Staff staff, String newCastMember) {
        ArrayList<Movie> modifiedMovies = staff.addCastMember(movie, this.movieList, newCastMember);
        if (modifiedMovies == null) {
            return false;
        }
        this.movieList = modifiedMovies;
        return true;
    }

    public boolean deleteCastMember(Movie movie, Staff staff, String newCastMember) {
        ArrayList<Movie> modifiedMovies = staff.deleteCastMember(movie, this.movieList, newCastMember);
        if (modifiedMovies == null) {
            return false;
        }
        this.movieList = modifiedMovies;
        return true;
    }

    public boolean modifySeatings(Movie movie, Staff staff, String seat, Integer seatValue) {
        ArrayList<Movie> modifiedMovies = staff.modifySeatings(movie, this.movieList, seat, seatValue);
        if (modifiedMovies == null) {
            return false;
        }
        this.movieList = modifiedMovies;
        return true;
    }

    public ArrayList<GiftCard> getGiftCardList() {
        return this.giftCardList;
    }

    public ArrayList<GiftCard> redeemGiftCard(String giftCardNumber, Staff staff) {
        return staff.redeemGiftCard(giftCardNumber, this.giftCardList);
    }

    public boolean addGiftCard(GiftCard giftCard, Staff staff) {
        ArrayList<GiftCard> modifiedGiftCards = staff.addGiftCard(giftCard.getNumber(), giftCard.getStatus(), giftCard.getBalance(), this.giftCardList);
        if (modifiedGiftCards == null) {
            return false;
        }
        this.giftCardList = modifiedGiftCards;
        return true;
    }

    public ArrayList<GiftCard> deleteGiftCard(GiftCard giftCard, Staff staff) {
        return staff.deleteGiftCard(giftCard, this.giftCardList);
    }


    public void upcomingMovieCSV() throws IOException {
        LocalDateTime currentTime = LocalDateTime.now();
        ArrayList<Movie> saveMovie = new ArrayList<>();

        for(Movie movie: this.getMovieList()) {
            if(movie.getTime().isAfter(currentTime)) {
                saveMovie.add(movie);
            }
        }

        try {
            CSVWriter writer = new CSVWriter(new FileWriter("src/main/staff_files/upcomingMovie.csv"));
            String header[] = {"Title", "Synopsis", "Rating", "Release Date", "Director", "Cast", "Location",
                    "Time", "Screen"};
            writer.writeNext(header);
            for(Movie movie: saveMovie) {
                String cast = String.join("/", movie.getCast());
                String line[] = {movie.getTitle(), movie.getSynopsis(), movie.getRating(),
                    movie.getReleaseDate().toString(), movie.getDirector(), cast, movie.getLocation(),
                        movie.getTime().toString(),movie.getScreen()};
                writer.writeNext(line);
            }
            writer.flush();
        } catch (IOException ex) {}
    }

    public void bookingSummary() throws IOException {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter("src/main/resources/bookingSummary.csv"));
            String header[] = {"Number of Bookings", "Movie", "Time", "Location", "Screen","Seats Booked", "Seats Available"};
            writer.writeNext(header);
            for(Movie movie: movieList) {
                HashMap<String, Integer> seatings = movie.getSeatings();
                int availSeats = 0;
                int bookSeats = 0;
                for(Map.Entry<String, Integer> entry : seatings.entrySet()) {
                    availSeats += entry.getValue();
                }
                for(Map.Entry<Integer, Booking> entry : bookingMap.entrySet()) {
                    if(entry.getValue().getMovie().equals(movie)) {
                        bookSeats ++;
                    }
                }
                String line[] = {Integer.toString(bookSeats), movie.getTitle(), movie.getTime().toString(), movie.getLocation(),
                    movie.getScreen(), Integer.toString(bookSeats), Integer.toString(availSeats)};
                writer.writeNext(line);
            }
            writer.flush();
        } catch (IOException ex) {}
    }

    /////////////////////////////////////////////////////////////////
    ///                    MANAGER FUNCTIONS                      ///
    /////////////////////////////////////////////////////////////////

    public void cancelSummary(String file) throws IOException {
        this.saveCancelInfo(file);
        File src = new File(file);
        File dest = new File("src/main/staff_files/cancelSummary.csv");
        if(dest.exists()){
            dest.delete();
        }
        Files.copy(src.toPath(), dest.toPath());
    }

    public ArrayList<Staff> getStaffList() {
        return this.staffList;
    }

    public ArrayList<Manager> getManagerList() {
        return this.managerList;
    }

    public ArrayList<Staff> deleteStaff(Staff staffMember, Manager user) {
        return user.removeStaff(staffMember.getUsername(), this.staffList);
    }

    public ArrayList<Staff> addStaff(String username, String password, Manager manager) {
        return manager.addStaff(username, password, this.staffList);
    }

    /////////////////////////////////////////////////////////////////
    ///                    TRANSACTION FUNCTION                   ///
    /////////////////////////////////////////////////////////////////

    public ArrayList<Booking> bookMovie(Movie movie, int children, int students, int adults, int seniors,
                                        int frontRowSeats, int middleRowSeats, int backRowSeats,
                             String screenSize, String location, String time, Customer customer) {
        /* Book movie for customer
            TODO:
            1) ADD check for if enough seats are left for movie

        */
        ArrayList<Booking> bookings = new ArrayList<Booking>();

        // Checks if the number of total number of tickets is equal to all categories
        if ((children + students + adults + seniors != (frontRowSeats + middleRowSeats + backRowSeats))) {
            // Set a label to error message

            return null;
        }

        // Checks enough seats are left

        int tickets = frontRowSeats + middleRowSeats + backRowSeats;
        // Completes booking for each ticket
        for (int i = 0; i < tickets; i++) {
            if (children > 0) {
                if (frontRowSeats > 0) {
                    Booking booking = new Booking(movie, "front", "child", screenSize, location, LocalDateTime.parse(time));
                    frontRowSeats--;
                    if(booking.isValid()) {
                        bookings.add(booking);
                    } else {
                        return null;
                    }
                }
                else if (middleRowSeats > 0) {
                    Booking booking = new Booking(movie, "middle", "child", screenSize, location, LocalDateTime.parse(time));
                    middleRowSeats--;
                    if(booking.isValid()) {
                        bookings.add(booking);
                    } else {
                        return null;
                    }
                }
                else {
                    Booking booking = new Booking(movie, "rear", "child", screenSize, location, LocalDateTime.parse(time));
                    backRowSeats--;
                    if(booking.isValid()) {
                        bookings.add(booking);
                    } else {
                        return null;
                    }
                }
                children --;
            }
            else if (students > 0) {
                if (frontRowSeats > 0) {
                    Booking booking = new Booking(movie, "front", "student", screenSize, location, LocalDateTime.parse(time));
                    frontRowSeats--;
                    if(booking.isValid()) {
                        bookings.add(booking);
                    } else {
                        return null;
                    }
                }
                else if (middleRowSeats > 0) {
                    Booking booking = new Booking(movie, "middle", "student", screenSize, location, LocalDateTime.parse(time));
                    middleRowSeats--;
                    if(booking.isValid()) {
                        bookings.add(booking);
                    } else {
                        return null;
                    }
                }
                else {
                    Booking booking = new Booking(movie, "rear", "student", screenSize, location, LocalDateTime.parse(time));
                    backRowSeats--;
                    if(booking.isValid()) {
                        bookings.add(booking);
                    } else {
                        return null;
                    }
                }
                students --;
            }
            else if (adults > 0) {
                if (frontRowSeats > 0) {
                    Booking booking = new Booking(movie, "front", "adult", screenSize, location, LocalDateTime.parse(time));
                    frontRowSeats--;
                    if(booking.isValid()) {
                        bookings.add(booking);
                    } else {
                        return null;
                    }
                }
                else if (middleRowSeats > 0) {
                    Booking booking = new Booking(movie, "middle", "adult", screenSize, location, LocalDateTime.parse(time));
                    middleRowSeats--;
                    if(booking.isValid()) {
                        bookings.add(booking);
                    } else {
                        return null;
                    }
                }
                else {
                    Booking booking = new Booking(movie, "rear", "adult", screenSize, location, LocalDateTime.parse(time));
                    backRowSeats--;
                    if(booking.isValid()) {
                        bookings.add(booking);
                    } else {
                        return null;
                    }
                }
                adults --;
            }
            else if (seniors > 0) {
                if (frontRowSeats > 0) {
                    Booking booking = new Booking(movie, "front", "senior", screenSize, location, LocalDateTime.parse(time));
                    frontRowSeats--;
                    if(booking.isValid()) {
                        bookings.add(booking);
                    } else {
                        return null;
                    }
                }
                else if (middleRowSeats > 0) {
                    Booking booking = new Booking(movie, "middle", "senior", screenSize, location, LocalDateTime.parse(time));
                    middleRowSeats--;
                    if(booking.isValid()) {
                        bookings.add(booking);
                    } else {
                        return null;
                    }
                }
                else {
                    Booking booking = new Booking(movie, "rear", "senior", screenSize, location, LocalDateTime.parse(time));
                    backRowSeats--;
                    if(booking.isValid()) {
                        bookings.add(booking);
                    } else {
                        return null;
                    }
                }
                seniors --;
            }
        }
        for(Booking booking: bookings) {
            this.transactionID ++;
            bookingMap.put(this.transactionID, booking);
        }
        return bookings;
    }

    public int payment(String cardName, String cardNumber, String paymentType, ArrayList<Booking> bookings) {
        /* Payment method for a customer:
            paymentType:
            1) "card" - for credit card
            2) "giftCard" = for giftcard card
            Return true if payment went through otherwise false
        */

        double totalCost = 0.0;
        for (Booking booking : bookings) {
            totalCost += booking.getCost();
        }

        if (paymentType.equals("card")) {
            // Ensure customer has an associated Card
            Card card = checkCard(cardName);
            if (card != null) {
                if (cardNumber.equals(card.getNumber())) {
                    return 1;
                }
            }
            return 0;
        } else if (paymentType.equals("giftCard")) {
            // Ensure customer has an associated giftCard
            // Plus check balance
            GiftCard giftCard = checkGiftCard(cardNumber);
            if (giftCard != null) {
                if(giftCard.getBalance() >= totalCost) {
                    if (giftCard.redeem()) {
                        giftCard.setBalance(giftCard.getBalance() - totalCost);
                        return 1;
                    }
                    return 2;
                }
                return 3;
            }
            return 4;
        }
        return 5;
    }

    public int transaction(ArrayList<Booking> bookings) {
        int curr_id = this.transactionID;
        for (Booking booking : bookings) {
            curr_id ++;
            bookingMap.put(curr_id, booking);
        }

        this.transactionID = curr_id;
        return curr_id;

    }
    /////////////////////////////////////////////////////////////////
    ///                      CARD FUNCTIONS                       ///
    /////////////////////////////////////////////////////////////////

    public GiftCard checkGiftCard(String giftNumber) {
        if(giftNumber.length() == 18) {
            for(GiftCard giftCard: giftCardList) {
                if(giftNumber.equals(giftCard.getNumber())) {
                    return giftCard;
                }
            }
        }
        return null;
    }

    public Card checkCard(String name) {
        for (Card card : this.cardList) {
            if(name.equals(card.getName())) {
                return card;
            }
        }
        return null;
    }

    /////////////////////////////////////////////////////////////////
    ///                   FILTER FUNCTIONS                      ///
    /////////////////////////////////////////////////////////////////

    public ArrayList<Movie> getLocationMovies(String location) {
        ArrayList<Movie> locationMovies = new ArrayList<>();
        for (Movie movie: movieList) {
            if(movie.getLocation().equals(location)) {
                locationMovies.add(movie);
            }
        }
        return locationMovies;
    }

    public ArrayList<Movie> getScreenMovies(String screen) {
        ArrayList<Movie> screenMovies = new ArrayList<>();
        for (Movie movie: movieList) {
            if(movie.getScreen().equals(screen)) {
                screenMovies.add(movie);
            }
        }
        return screenMovies;
    }

    /////////////////////////////////////////////////////////////////
    ///              SAVING OF INFO TO JSON FILES                 ///
    /////////////////////////////////////////////////////////////////

    public void saveInfo(String customerFile, String staffFile, String managerFile, String cardFile, String giftCardFile,
                         String movieFile, String bookingFile, String cancelFile) throws IOException {
        this.saveCardInfo(cardFile);
        this.saveGiftCardsInfo(giftCardFile);
        this.saveBookingInfo(bookingFile);
        this.saveCustomerInfo(customerFile);
        this.saveStaffInfo(staffFile);
        this.saveManagerInfo(managerFile);
        this.saveMovieInfo(movieFile);
        this.saveCancelInfo(cancelFile);
    }

    private void saveCancelInfo(String cancelFile) throws IOException {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(cancelFile, true));
            for(CancelBooking booking: this.cancelBookings) {
                String line[] = {booking.getBooking().getMovie().getTitle(), booking.getReason(),
                    booking.getTime().toString(), booking.getCustomer().getUsername()};
                writer.writeNext(line);
            }
            writer.flush();
        } catch (IOException ex) {}
    }


    private void saveMovieInfo(String movieFile) throws IOException {
        LocalDateTime currentTime = LocalDateTime.now();
        ArrayList<Movie> saveMovie = new ArrayList<>();

        for(Movie movie: movieList) {
            if(movie.getTime().isAfter(currentTime)) {
                saveMovie.add(movie);
            }
        }

        try {
            CSVWriter writer = new CSVWriter(new FileWriter(movieFile));
            for(Movie movie: saveMovie) {
                HashMap<String, Integer> seatings = movie.getSeatings();
                String cast = String.join("/", movie.getCast());
                String line[] = {movie.getTitle(), movie.getSynopsis(), movie.getRating(),
                    movie.getReleaseDate().toString(), movie.getDirector(), cast, movie.getLocation(),
                        movie.getTime().toString(),movie.getScreen(),String.valueOf(seatings.get("front")),
                            String.valueOf(seatings.get("middle")), String.valueOf(seatings.get("rear"))};
                writer.writeNext(line);
            }
            writer.flush();
        } catch (IOException ex) {}
    }

    private void saveCardInfo(String cardFile) throws IOException{
        try {
            File saveFile = new File(cardFile);
            saveFile.createNewFile();
            JSONArray jsonArray = new JSONArray();
            for (Card card: this.cardList){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", card.getName());
                jsonObject.put("number", card.getNumber());
                jsonArray.add(jsonObject);
            }
            FileWriter file = new FileWriter(cardFile);
            file.write(jsonArray.toJSONString());
            file.close();
        } catch (IOException ex) {}
    }

    private void saveGiftCardsInfo(String giftFile) throws IOException {
        try {
            File saveFile = new File(giftFile);
            saveFile.createNewFile();
            JSONArray jsonArray = new JSONArray();
            for (GiftCard giftCard: giftCardList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("number", giftCard.getNumber());
                jsonObject.put("status", giftCard.getStatus());
                jsonObject.put("balance", giftCard.getBalance());
                jsonArray.add(jsonObject);
            }
            FileWriter file = new FileWriter(giftFile);
            file.write(jsonArray.toJSONString());
            file.close();
        } catch (IOException ex) {}
    }

    private void saveBookingInfo(String bookingFile) throws IOException {
        try {
            File saveFile = new File(bookingFile);
            saveFile.createNewFile();
            JSONArray jsonArray = new JSONArray();
            for (Map.Entry<Integer , Booking> entry: bookingMap.entrySet()) {
                int transactionID = entry.getKey();
                Booking booking = entry.getValue();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("transactionID", transactionID);
                jsonObject.put("movie", booking.getMovie().getTitle());
                jsonObject.put("seat", booking.getSeat());
                jsonObject.put("person", booking.getPerson());
                jsonObject.put("screen", booking.getScreen());
                jsonObject.put("location", booking.getLocation());
                jsonObject.put("time", booking.getTime().toString());
                jsonArray.add(jsonObject);
            }
            for (Map.Entry<Integer , Booking> entry: bookingMap.entrySet()) {
                entry.getValue().cancelBooking();
            }
            FileWriter file = new FileWriter(bookingFile);
            file.write(jsonArray.toJSONString());
            file.close();
        } catch (IOException ex) {}
    }

    private void saveCustomerInfo(String customerFile) throws IOException {
        try {
            File saveFile = new File(customerFile);
            saveFile.createNewFile();
            JSONArray jsonArray = new JSONArray();
            for (Customer customer: this.customerList){
                JSONObject jsonObject = new JSONObject();

                if(customer.getCard() != null) {
                    Card card = customer.getCard();
                    jsonObject.put("cardName", card.getName());
                    jsonObject.put("cardNumber", card.getNumber());
                } else {
                    jsonObject.put("cardName", "null");
                    jsonObject.put("cardNumber", "null");
                }

                jsonObject.put("username", customer.getUsername());
                jsonObject.put("password", customer.getPassword());
                jsonArray.add(jsonObject);
            }
            FileWriter file = new FileWriter(customerFile);
            file.write(jsonArray.toJSONString());
            file.close();
        } catch (IOException ex) {}
    }

    private void saveStaffInfo(String staffFile) throws IOException {
        try {
            File saveFile = new File(staffFile);
            saveFile.createNewFile();
            JSONArray jsonArray = new JSONArray();
            for (Staff staff : this.staffList){
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("username", staff.getUsername());
                jsonObject.put("password", staff.getPassword());
                jsonArray.add(jsonObject);
            }
            FileWriter file = new FileWriter(staffFile);
            file.write(jsonArray.toJSONString());
            file.close();
        } catch (IOException ex) {}
    }

    private void saveManagerInfo(String managerFile) throws IOException {
        try {
            File saveFile = new File(managerFile);
            saveFile.createNewFile();
            JSONArray jsonArray = new JSONArray();
            for (Manager staff : this.managerList){
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("username", staff.getUsername());
                jsonObject.put("password", staff.getPassword());
                jsonArray.add(jsonObject);
            }
            FileWriter file = new FileWriter(managerFile);
            file.write(jsonArray.toJSONString());
            file.close();
        } catch (IOException ex) {}
    }

    /////////////////////////////////////////////////////////////////
    ///           CONSTRUCTION OF ALL CLASSES FROM FILE           ///
    /////////////////////////////////////////////////////////////////
    private void constructMovies(String movieCSV) throws IOException, ParseException {
        CSVReader reader = new CSVReader(new FileReader(movieCSV));
        StringBuffer buffer = new StringBuffer();
        String line[];
        Iterator it = reader.iterator();
        while(it.hasNext()) {
            line = (String[])it.next();
            HashMap<String, Integer> seatings = new HashMap<>();
            seatings.put("front", Integer.parseInt(line[9]));
            seatings.put("middle", Integer.parseInt(line[10]));
            seatings.put("rear", Integer.parseInt(line[11]));
            ArrayList<String> cast = new ArrayList<>(Arrays.asList(line[5].split("/")));
            Movie movie = new Movie(line[0], line[1], line[2], LocalDate.parse(line[3]), line[4],
                                    cast, line[6], LocalDateTime.parse(line[7]), line[8], seatings);
            LocalDateTime currentTime = LocalDateTime.now();
            if(movie.getTime().isAfter(currentTime)){
                movieList.add(movie);
            }
        }

    }

    private void constructCards(String cardFile) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(cardFile);
        JSONArray cards = (JSONArray) jsonParser.parse(reader);

        for (Object obj : cards) {
            JSONObject cardObj = (JSONObject) obj;

            String name = (String) cardObj.get("name");
            String number = (String) cardObj.get("number");

            Card card = new Card(name, number);

            //look for valid cards only
            if(name.length() != 0 && number.length() == 5) {
                if(checkCard(card.getName()) == null){
                    cardList.add(card);
                }
            }
        }
    }

    private void constructCustomers(String customerFile) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(customerFile);
        JSONArray customers = (JSONArray) jsonParser.parse(reader);

        for (Object obj : customers) {
            JSONObject customerObj = (JSONObject) obj;

            String username = (String) customerObj.get("username");
            String password = (String) customerObj.get("password");

            Customer customer = new Customer(username, password);
            Card card = new Card(null, null);
            customer.saveCardDetails(card);
            customerList.add(customer);
        }
    }

    private void constructGiftCards(String giftCardFile) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(giftCardFile);
        JSONArray giftCards = (JSONArray) jsonParser.parse(reader);

        for (Object obj: giftCards) {
            JSONObject giftCardObj = (JSONObject) obj;
            String number = (String) giftCardObj.get("number");
            Boolean status = (Boolean) giftCardObj.get("status");
            double amount = (double) giftCardObj.get("balance");
            GiftCard giftCard = new GiftCard(number, status, amount);
            giftCardList.add(giftCard);
        }
    }

    private void constructBookings(String bookingFile) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(bookingFile);
        JSONArray bookingsArray = (JSONArray) jsonParser.parse(reader);
        int cinemaTransactionId = 0;

        for (Object obj: bookingsArray) {
            JSONObject bookingObj= (JSONObject) obj;
            Long transactionID = (long) bookingObj.get("transactionID");
            String movieTitle = (String) bookingObj.get("movie");
            String seat = (String) bookingObj.get("seat");
            String person = (String) bookingObj.get("person");
            String screen = (String) bookingObj.get("screen");
            String location = (String) bookingObj.get("location");
            String time = (String) bookingObj.get("time");
            LocalDateTime timeLocal = LocalDateTime.parse(time);
            Movie movie = this.getMovie(movieTitle, location, timeLocal, screen);
            if(movie !=  null) {
                Booking booking = new Booking(movie, seat, person, screen, location, timeLocal);
                if(booking.isValid()){
                    int transactionInt = transactionID.intValue();
                    if(transactionInt > cinemaTransactionId) {
                        cinemaTransactionId = transactionInt;
                    }
                    bookingMap.put(transactionInt, booking);
                }
            }
        }
        this.transactionID = cinemaTransactionId;
    }

    private void constructStaff(String staffFile) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(staffFile);
        JSONArray staffArray = (JSONArray) jsonParser.parse(reader);

        for (Object obj: staffArray) {
            JSONObject staffObject = (JSONObject) obj;
            String username = (String) staffObject.get("username");
            String password = (String) staffObject.get("password");
            Staff staff = new Staff(username, password);
            staffList.add(staff);
        }
    }

    private void constructManager(String managerFile) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(managerFile);
        JSONArray managerArray = (JSONArray) jsonParser.parse(reader);

        for (Object obj: managerArray) {
            JSONObject staffObject = (JSONObject) obj;
            String username = (String) staffObject.get("username");
            String password = (String) staffObject.get("password");
            Manager manager = new Manager(username, password);
            managerList.add(manager);
        }
    }
}
