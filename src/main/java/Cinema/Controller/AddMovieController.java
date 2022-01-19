package Cinema.Controller;

import Cinema.Cinema;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import Cinema.Movie;
import Cinema.User;
import Cinema.Staff;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class AddMovieController {

    @FXML
    public TextField titleInput;

    @FXML
    public TextField synopsisInput;

    @FXML
    public TextField ratingInput;

    @FXML
    public TextField releaseDateInput;

    @FXML
    public TextField directorInput;

    @FXML
    public TextField castInput;

    @FXML
    public TextField locationInput;

    @FXML
    public TextField timeInput;

    @FXML
    public TextField screenSizeInput;

    @FXML
    public TextField frontRowSeatsInput;

    @FXML
    public TextField middleRowSeatsInput;

    @FXML
    public TextField backRowSeatsInput;

    @FXML
    public Label errorLabel;

    private Cinema cinema;
    private User user;

    private Parent root;
    private Scene scene;
    private Stage stage;

    public void addButtonOnAction(ActionEvent actionEvent) {
        String title = titleInput.getText();
        String synopsis = synopsisInput.getText();
        String rating = ratingInput.getText();
        String releaseDate = releaseDateInput.getText();
        String director = directorInput.getText();
        String cast = castInput.getText();
        String location = locationInput.getText();
        String time = timeInput.getText();
        String screenSize = screenSizeInput.getText();
        String frontRowSeats = frontRowSeatsInput.getText();
        String middleRowSeats = middleRowSeatsInput.getText();
        String backRowSeats = backRowSeatsInput.getText();

        if (title.equals("") || synopsis.equals("") || rating.equals("") || releaseDate.equals("") || director.equals("")
                || cast.equals("") || location.equals("") || time.equals("") || screenSize.equals("") || frontRowSeats.equals("")
                || middleRowSeats.equals("") || backRowSeats.equals("")) {
            errorLabel.setText("No values can be empty. ");
        }
        else {
            ArrayList<String> movieCast = new ArrayList<>(Arrays.asList(cast.split(", ")));
            HashMap<String, Integer> seatings = new HashMap<>();
            seatings.put("front", Integer.parseInt(frontRowSeats));
            seatings.put("middle", Integer.parseInt(middleRowSeats));
            seatings.put("rear", Integer.parseInt(backRowSeats));
            Movie movie = new Movie(title, synopsis, rating, LocalDate.parse(releaseDate), director, movieCast, location,
                    LocalDateTime.parse(time), screenSize, seatings);
            if (cinema.addMovie(movie, (Staff) user) == null) {
                errorLabel.setText("Movie with the same name, location, time, screen size and classification already exists. ");
            }
            else {
                errorLabel.setText("New Movie Added!");
            }
        }
    }

    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpcomingMovies.fxml"));
        root = loader.load();

        UpcomingMoviesController upcomingMoviesController = loader.getController();
        upcomingMoviesController.setCinema(this.cinema);
        upcomingMoviesController.setUser(this.user);
        upcomingMoviesController.initialiseTableView();
        upcomingMoviesController.setFilterCategory();

        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Upcoming Movies");
        scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
