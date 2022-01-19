package Cinema.Controller;

import Cinema.Cinema;
import Cinema.User;
import Cinema.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public class MovieInformationController {

    @FXML
    public Label movieDirector;

    @FXML
    public Label movieSynopsis;

    @FXML
    public Label movieReleaseDate;

    @FXML
    public Label movieCast;

    @FXML
    public Label numberOfSeatsAvailable;

    @FXML
    public Button backButton;

    private Cinema cinema;
    private User user;
    private Movie movie;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpcomingMovies.fxml"));
        root = loader.load();

        UpcomingMoviesController upcomingMoviesController = loader.getController();
        upcomingMoviesController.setCinema(this.cinema);
        upcomingMoviesController.setUser(this.user);
        upcomingMoviesController.initialiseTableView();

        stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Upcoming Movies");
        scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
    }

    public void setText() {
        this.movieDirector.setText("Movie Director: " + this.movie.getDirector());
        this.movieSynopsis.setText("Movie Synopsis: " + this.movie.getSynopsis());
        this.movieReleaseDate.setText("Movie Release Date: " + this.movie.getReleaseDate());
        StringBuilder cast = new StringBuilder();
        for (int i = 0; i < this.movie.getCast().size(); i++) {
            if (i == this.movie.getCast().size() - 1) {
                cast.append(this.movie.getCast().get(i));
            }
            else if (i % 4 == 0 && i != 0 && i != 8) {
                cast.append(this.movie.getCast().get(i) + ",\n ");
            }
            else {
                cast.append(this.movie.getCast().get(i) + ", ");
            }
        }
        this.movieCast.setText("Movie Cast: " + cast.toString());
       StringBuilder seatings = new StringBuilder();
        for (Map.Entry<String, Integer> seating : this.movie.getSeatings().entrySet()) {
            seatings.append(seating.getKey() + ": " + seating.getValue() + " ");
        }
        this.numberOfSeatsAvailable.setText("Number of Seats Available: " + seatings.toString());
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
