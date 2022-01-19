package Cinema;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

public class Staff extends Customer {

    public Staff(String username, String password) {
        super(username, password);
    }

    // public String getUsername() {
    //     return this.username;
    // }
    //
    // public boolean usernameCheck(String username) {
    //     return this.username.equals(username);
    // }
    //
    // public String getPassword() {
    //     return this.password;
    // }
    //
    // public boolean passwordCheck(String password) {
    //     return this.password.equals(password);
    // }

    public ArrayList<GiftCard> addGiftCard(String number, boolean status, Double balance, ArrayList<GiftCard> giftCardList) {
        //TODO
        // make sure card does not already exist
        // return null if card already exists
        // add to list

        for(GiftCard card: giftCardList) {
            // card already exists
            if (card.getNumber().equals(number)) {
                return null;
            }
        }

        GiftCard gc = new GiftCard(number, status, balance);
        giftCardList.add(gc);
        return giftCardList;

    }

    public ArrayList<GiftCard> redeemGiftCard(String number, ArrayList<GiftCard> giftCardList) {
        for(GiftCard card: giftCardList) {
            if (card.getNumber().equals(number)) {
                card.redeem();
                return giftCardList;
            }
        }

        // return null if card wasn't found
        return null;
    }

    public ArrayList<Movie> addMovie(Movie movie, ArrayList<Movie> currentMovies) {
        if (movie == null) {
            return null;
        }
        for(Movie m : currentMovies) {
            if(movie.getTitle().equals(m.getTitle()) && movie.getLocation().equals(m.getLocation())
                && movie.getTime().equals(m.getTime()) && movie.getScreen().equals(m.getScreen())
                && movie.getRating().equals(m.getRating())) {
                return null;
            }
        }
        currentMovies.add(movie);
        return currentMovies; // movie list has a new movie added
    }

    public ArrayList<Movie> deleteMovie(Movie movie, ArrayList<Movie> currentMovies) {
        if (movie == null) {
            return null;
        }
        Movie copy = new Movie(movie.getTitle(), movie.getSynopsis(), movie.getRating(), movie.getReleaseDate(),
                movie.getDirector(), movie.getCast(), movie.getLocation(), movie.getTime(), movie.getScreen(),
                movie.getSeatings());
        List<Movie> found = new ArrayList<>();
        for(Movie m : currentMovies) {
           if(m.getLocation().equals(copy.getLocation()) &&
                   m.getRating().equals(copy.getRating()) && m.getScreen().equals(copy.getScreen())
                   && m.getTime().equals(copy.getTime()) && m.getTitle().equals(copy.getTitle())) {
               found.add(m);
           }
        }
        if (currentMovies.removeAll(found)) {
            return currentMovies;
        }
        else return null; // movie was not found to delete
    }

    public ArrayList<Movie> modifyTitle(Movie movie, ArrayList<Movie> currentMovies, String newTitle) {
        if(movie == null || newTitle == null) {
            return null;
        }
        Movie copy = new Movie(movie.getTitle(), movie.getSynopsis(), movie.getRating(), movie.getReleaseDate(),
                movie.getDirector(), movie.getCast(), movie.getLocation(), movie.getTime(), movie.getScreen(),
                movie.getSeatings());
        boolean found = false;
        for(Movie m : currentMovies) {
            if(copy.getTitle().equals(m.getTitle())) {
                found = true;
                m.setMovieTitle(newTitle);
            }
        }
        if (found) return currentMovies;
        else return null;
    }

    public ArrayList<Movie> modifySynopsis(Movie movie, ArrayList<Movie> currentMovies, String newSynopsis) {
        if(movie == null || newSynopsis == null) {
            return null;
        }
        Movie copy = new Movie(movie.getTitle(), movie.getSynopsis(), movie.getRating(), movie.getReleaseDate(),
                movie.getDirector(), movie.getCast(), movie.getLocation(), movie.getTime(), movie.getScreen(),
                movie.getSeatings());
        boolean found = false;
        for(Movie m : currentMovies) {
            if(copy.getSynopsis().equals(m.getSynopsis())) {
                found = true;
                m.setSynopsis(newSynopsis);
            }
        }
        if (found) return currentMovies;
        else return null; // movie to be modified was not found
    }

    public ArrayList<Movie> modifyRating(Movie movie, ArrayList<Movie> currentMovies, String newRating) {
        if(movie == null || newRating == null) {
            return null;
        }
        Movie copy = new Movie(movie.getTitle(), movie.getSynopsis(), movie.getRating(), movie.getReleaseDate(),
                movie.getDirector(), movie.getCast(), movie.getLocation(), movie.getTime(), movie.getScreen(),
                movie.getSeatings());
        boolean found = false;
        for(Movie m : currentMovies) {
            if(copy.getRating().equals(m.getRating())) {
                found = true;
                m.setRating(newRating);
            }
        }
        if (found) return currentMovies;
        else return null; // movie to be modified was not found
    }

    public ArrayList<Movie> modifyReleaseDate(Movie movie, ArrayList<Movie> currentMovies, LocalDate newDate) {
        if(movie == null || newDate == null) {
            return null;
        }
        Movie copy = new Movie(movie.getTitle(), movie.getSynopsis(), movie.getRating(), movie.getReleaseDate(),
                movie.getDirector(), movie.getCast(), movie.getLocation(), movie.getTime(), movie.getScreen(),
                movie.getSeatings());
        boolean found = false;
        for(Movie m : currentMovies) {
            if(copy.getReleaseDate().equals(m.getReleaseDate()) &&
                    copy.getTitle().equals(m.getTitle())) {
                found = true;
                m.setReleaseDate(newDate);
            }
        }
        if(found) return currentMovies;
        else return null; // movie to be modified was not found
    }

    public ArrayList<Movie> modifyDirector(Movie movie, ArrayList<Movie> currentMovies, String newDirector) {
        if(movie == null || newDirector == null) {
            return null;
        }
        Movie copy = new Movie(movie.getTitle(), movie.getSynopsis(), movie.getRating(), movie.getReleaseDate(),
                movie.getDirector(), movie.getCast(), movie.getLocation(), movie.getTime(), movie.getScreen(),
                movie.getSeatings());
        boolean found = false;
        for(Movie m : currentMovies) {
            if(copy.getDirector().equals(m.getDirector()) &&
            copy.getTitle().equals(m.getTitle())) {
                found = true;
                m.setDirector(newDirector);
            }
        }
        if(found) return currentMovies;
        else return null; // movie to be modified was not found
    }

    public ArrayList<Movie> addCastMember(Movie movie, ArrayList<Movie> currentMovies, String newCastMember) {
        if(movie == null || newCastMember == null) {
            return null;
        }
        Movie copy = new Movie(movie.getTitle(), movie.getSynopsis(), movie.getRating(), movie.getReleaseDate(),
                movie.getDirector(), movie.getCast(), movie.getLocation(), movie.getTime(), movie.getScreen(),
                movie.getSeatings());
        boolean found = false;
        for(Movie m : currentMovies) {
            if(copy.getTitle().equals(m.getTitle())) {
                found = true;
                ArrayList<String> movieCast = m.getCast();

                if(! movieCast.contains(newCastMember)) {
                    movieCast.add(newCastMember);
                    m.setCast(movieCast);
                }
                else return null; // cast member already in cast list
            }
        }
        if(found) return currentMovies;
        else return null; // movie to be modified was not found
    }

    public ArrayList<Movie> deleteCastMember(Movie movie, ArrayList<Movie> currentMovies, String newCastMember) {
        if(movie == null || newCastMember == null) {
            return null;
        }
        Movie copy = new Movie(movie.getTitle(), movie.getSynopsis(), movie.getRating(), movie.getReleaseDate(),
                movie.getDirector(), movie.getCast(), movie.getLocation(), movie.getTime(), movie.getScreen(),
                movie.getSeatings());
        boolean found = false;
        for(Movie m : currentMovies) {
            if(copy.getTitle().equals(m.getTitle())) {
                found = true;
                ArrayList<String> movieCast = m.getCast();

                if(movieCast.contains(newCastMember)) {
                    movieCast.remove(newCastMember);
                    m.setCast(movieCast);
                }
                else return null; // cast member not found in cast list
            }
        }
        if(found) return currentMovies;
        else return null; // movie to be modified was not found
    }

    public ArrayList<Movie> modifySeatings(Movie movie, ArrayList<Movie> currentMovies, String seat, Integer seatValue) {
        if (movie == null) {
            return null;
        }
        Movie copy = new Movie(movie.getTitle(), movie.getSynopsis(), movie.getRating(), movie.getReleaseDate(),
                movie.getDirector(), movie.getCast(), movie.getLocation(), movie.getTime(), movie.getScreen(),
                movie.getSeatings());
        boolean found = false;
        for (Movie m : currentMovies) {
            if (copy.getLocation().equals(m.getLocation()) && copy.getTime().equals(m.getTime())
                && copy.getScreen().equals(m.getScreen()) && copy.getTitle().equals(m.getTitle())
                && copy.getRating().equals(m.getRating())) {
                found = true;
                m.getSeatings().put(seat, seatValue);
            }
        }
        if (found) {
            return currentMovies;
        }
        else {
            return null;
        }
    }

    public ArrayList<GiftCard> deleteGiftCard(GiftCard giftCard, ArrayList<GiftCard> currentGiftCards) {
        if (giftCard == null) {
            return null;
        }
        GiftCard copy = new GiftCard(giftCard.getNumber(), giftCard.getStatus(), giftCard.getBalance());
        List<GiftCard> found = new ArrayList<>();
        for(GiftCard g : currentGiftCards) {
            if(g.getNumber().equals(copy.getNumber()) && g.getStatus() == copy.getStatus()
                    && g.getBalance().equals(copy.getBalance())) {
                found.add(g);
            }
        }
        if (currentGiftCards.removeAll(found)) {
            return currentGiftCards;
        }
        else return null; // movie was not found to delete
    }

    @Override
    public boolean canEdit() {
        return true;
    }
}
