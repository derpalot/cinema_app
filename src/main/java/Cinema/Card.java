package Cinema;

public class Card {
    private String name;
    private String number;

    public Card(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return this.name;
    }

    public String getNumber() {
        return this.number;
    }

    public boolean checkNumber(String number) {
        return this.number.equals(number);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null) {
            return false;
        }
        Card card = (Card) o;
        return this.name.equals(card.getName()) &&
            this.number.equals(card.getNumber());
    }
}
