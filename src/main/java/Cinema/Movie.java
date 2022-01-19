package Cinema;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

public class Movie {
    private String title;
    private String rating;
    private String director;
    private String synopsis;
    private String location;
    private String screen;
    private HashMap<String, Integer> seatings;
    private LocalDateTime time;
    private LocalDate releaseDate;
    private ArrayList<String> cast;

    public Movie(String title, String synopsis, String rating, LocalDate releaseDate,
                 String director, ArrayList<String> cast, String location, LocalDateTime time,
                 String screen, HashMap<String, Integer> seatings) {
        this.screen = screen;
        this.time = time;
        this.location = location;
        this.title = title;
        this.synopsis = synopsis;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.director = director;
        this.cast = cast;
        this.seatings = seatings;
    }

    public String getTitle() {
        return this.title;
    }

    public String getSynopsis() {
        return this.synopsis;
    }

    public String getRating() {
        return this.rating;
    }

    public LocalDate getReleaseDate() {
        return this.releaseDate;
    }

    public String getDirector() {
        return this.director;
    }

    public String getScreen() {
        return this.screen;
    }

    public LocalDateTime getTime() {
        return this.time;
    }

    public String getLocation() {
        return this.location;
    }

    public ArrayList<String> getCast() {
        return this.cast;
    }

    public HashMap<String, Integer> getSeatings() {
        return this.seatings;
    }

    public boolean bookSeat(String seat) {
        if(this.seatings.get(seat) != 0) {
            this.seatings.put(seat, this.seatings.get(seat) - 1);
            return true;
        }
        return false;
    }

    public void freeSeat(String seat) {
        this.seatings.put(seat, this.seatings.get(seat) + 1);
    }

    public void setMovieTitle(String title) { this.title = title; }

    public void setSynopsis(String synopsis) { this.synopsis = synopsis; }

    public void setRating(String rating) { this.rating = rating; }

    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }

    public void setDirector(String director) { this.director = director; }

    public void setCast(ArrayList<String> cast) { this.cast = cast; }

    public void setLocation(String location) { this.location = location; }

    public void setScreenSize(String screenSize) { this.screen = screenSize; }

    public void setTime(LocalDateTime time) { this.time = time; }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null ) {
            return false;
        }
        Movie movie = (Movie) o;
        return this.title.equals(movie.getTitle()) &&
            this.rating.equals(movie.getRating()) &&
            this.director.equals(movie.getDirector()) &&
            this.synopsis.equals(movie.getSynopsis()) &&
            this.location.equals(movie.getLocation()) &&
            this.screen.equals(movie.getScreen()) &&
            this.time.equals(movie.getTime()) &&
            this.releaseDate.equals(movie.getReleaseDate()) &&
            this.seatings.equals(movie.getSeatings()) &&
            this.cast.equals(movie.getCast());
    }
}
