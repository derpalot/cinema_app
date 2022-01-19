package Cinema;

public class GiftCard {
    private String number;
    private boolean status;
    private Double balance;

    public GiftCard(String number, boolean status, Double balance) {
        this.number = number;
        this.status = status;
        this.balance = balance;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public boolean redeem() {
        if(this.status) {
            this.status = false;
            return true;
        }
        return false;
    }

    public Double getBalance() {
        return this.balance;
    }

    public void setBalance(Double amount) {
        this.balance = amount;
    }

    public boolean useCard(Double amount) {
        if(amount > this.balance) {
            return false;
        }
       this.balance -= amount;
        return true;
    }

    public boolean isValid() {
        if (this.number == null || this.number.length() != 18 || this.balance == null) {
            return false;
        }
        String digits = this.number.substring(0, 16);
        String suffix = this.number.substring(16, 18);
        Double balance = this.balance;
        return balance > 0 && digits.matches("[0-9]+") && suffix.equals("GC");
    }
}
