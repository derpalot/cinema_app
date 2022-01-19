package Cinema;

import java.util.ArrayList;

public class Customer extends User {

    private String username;
    private String password;
    private ArrayList<Booking> bookings;
    private Card card;
    private int credit;
    private GiftCard giftCard;
    private boolean autoFill;

    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
        this.bookings = new ArrayList<Booking>();
        this.credit = 0;
        this.autoFill = false;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean usernameCheck(String username) {
        return this.username.equals(username);
    }

    public String getPassword() {
        return this.password;
    }

    public boolean passwordCheck(String password) {
        return this.password.equals(password);
    }

    public ArrayList<Booking> getBookings() {
        return this.bookings;
    }

    public void makeBooking(Booking booking) {
        bookings.add(booking);
    }

    public void cancel() {
        bookings.clear();
    }

    public void saveCardDetails(Card card) {
        this.card = card;
    }

    public Card getCard() {
        return this.card;
    }

    public GiftCard getGiftCard() {
        return this.giftCard;
    }

    public void setCredit(GiftCard giftCard) {
        this.giftCard = giftCard;
    }

    public boolean getAutoFill() {
        return this.autoFill;
    }

    public void setAutoFill(boolean fill) {
        this.autoFill = fill;
    }

    @Override
    public boolean canBook() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null) {
            return false;
        }
        Customer customer = (Customer) o;
        if (this.card == null) {
            if(customer.getCard() != null) {
                return false;
            }
        } else if(!this.card.equals(customer.getCard())) {
            return false;
        }
        return this.username.equals(customer.getUsername()) &&
            this.password.equals(customer.getPassword()) &&
            this.bookings.equals(customer.getBookings());
    }
}
