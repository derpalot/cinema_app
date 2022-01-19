package Cinema;

import java.time.LocalDateTime;

public class Booking {
    private Movie movie;
    private String seat;
    private String person;
    private String location;
    private String screen;
    private Double cost;
    private boolean valid;
    private LocalDateTime time;

    public Booking(Movie movie, String seat, String person, String screen, String location, LocalDateTime time) {
        this.cost = 0.00;
        this.movie  = movie;
        this.seat = seat;
        this.location = location;
        this.time = time;
        this.person = person;
        this.screen = screen;
        this.calculateCost();
        this.valid = true;
    }

    private void calculateCost(){
        //base cost of person
        if(this.person == "child") {
            this.cost += 15;
        } else if (person == "student") {
            this.cost += 16.50;
        } else if (person == "adult") {
            this.cost += 21.50;
        } else if (person == "senior") {
            this.cost += 14.50;
        }

        //additional cost on screen
        if(screen == "silver") {
            this.cost += 5;
        } else if (screen == "gold") {
            this.cost += 9;
        }
    }

    public Movie getMovie() {
        return this.movie;
    }

    public String getLocation() {
        return this.location;
    }

    public String getSeat() {
        return this.seat;
    }

    public String getPerson() {
        return this.person;
    }

    public String getScreen() {
        return this.screen;
    }

    public LocalDateTime getTime() {
        return this.time;
    }

    public Double getCost() {
        return this.cost;
    }

    public boolean isValid() {
        this.valid = this.movie.bookSeat(this.seat);
        return this.valid;
    }

    public void cancelBooking() {
        movie.freeSeat(this.seat);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null) {
            return false;
        }
        Booking booking = (Booking) o;
        return this.movie.equals(booking.getMovie()) &&
            this.seat.equals(booking.getSeat()) &&
            this.person.equals(booking.getPerson()) &&
            this.location.equals(booking.getLocation()) &&
            this.screen.equals(booking.getScreen()) &&
            this.time.equals(booking.getTime());
    }
}
