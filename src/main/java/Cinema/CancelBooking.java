package Cinema;

import java.time.LocalDateTime;

public class CancelBooking {
    private LocalDateTime time;
    private String reason;
    private Booking booking;
    private Customer customer;

    public CancelBooking(Booking booking, String reason, LocalDateTime time, Customer customer) {
        this.booking = booking;
        this.reason = reason;
        this.customer = customer;
        this.time = time;
        booking.cancelBooking();
        customer.cancel();
    }

    public Booking getBooking() {
        return this.booking;
    }

    public String getReason() {
        return this.reason;
    }

    public LocalDateTime getTime() {
        return this.time;
    }

    public Customer getCustomer() {
        return this.customer;
    }
}
