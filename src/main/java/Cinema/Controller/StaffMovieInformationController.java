package Cinema.Controller;

import Cinema.Cinema;
import Cinema.Movie;
import Cinema.User;
import Cinema.Staff;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

public class StaffMovieInformationController {

    @FXML
    public Label movieDirector;

    @FXML
    public Label movieSynopsis;

    @FXML
    public Label movieReleaseDate;

    @FXML
    public Label movieCast;

    @FXML
    public TextField directorInput;

    @FXML
    public TextField synopsisInput;

    @FXML
    public TextField dateInput;

    @FXML
    public TextField castInput;

    @FXML
    public Label editFailed;

    @FXML
    public TextField titleInput;

    @FXML
    public TextField classificationInput;

    @FXML
    public Label movieTitle;

    @FXML
    public Label movieClassification;

    @FXML
    public Spinner<Integer> frontRowSeatsAvailable;

    @FXML
    public Spinner<Integer> middleRowSeatsAvailable;

    @FXML
    public Spinner<Integer> backRowSeatsAvailable;

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

    public void editTitleButtonOnAction(ActionEvent actionEvent) throws IOException {
        String newTitle = titleInput.getText();

        if(newTitle.equals("")) {
            editFailed.setText("Enter a value for the Movie Title");
        }
        else {
            if (!this.cinema.modifyTitle(this.movie, (Staff) this.user, newTitle)) {
                editFailed.setText("Movie to be edited could not be found. ");
            }
            else {
                this.movieTitle.setText("Movie Title: " + newTitle);
            }
        }
    }

    public void editClassificationButtonOnAction(ActionEvent actionEvent) {
        String newRating = classificationInput.getText();

        if(newRating.equals("")) {
            editFailed.setText("Enter a value for the Movie Title");
        }
        else {
            if (!this.cinema.modifyRating(this.movie, (Staff) this.user, newRating)) {
                editFailed.setText("Movie to be edited could not be found. ");
            }
            else {
                this.movieClassification.setText("Movie Classification: " + newRating);
            }
        }
    }

    public void editDirectorButtonOnAction(ActionEvent actionEvent) throws IOException {
        String newDirector = directorInput.getText();

        if(newDirector.equals("")) {
            editFailed.setText("Enter a value for the Director name");
        }
        else {
            if (!this.cinema.modifyDirector(this.movie, (Staff) this.user, newDirector)) {
                editFailed.setText("Movie to be edited could not be found. ");
            }
            else {
                this.movieDirector.setText("Movie Director: " + newDirector);
            }
        }
    }

    public void editSynopsisButtonOnAction(ActionEvent actionEvent) throws IOException {
        String newSynopsis = synopsisInput.getText();

        if(newSynopsis.equals("")) {
            editFailed.setText("Enter a value for the Synopsis");
        }
        else {
            if (!this.cinema.modifySynopsis(this.movie, (Staff) this.user, newSynopsis)) {
                editFailed.setText("Movie to be edited could not be found. ");
            }
            else {
                this.movieSynopsis.setText("Movie Synopsis: " + newSynopsis);
            }
        }
    }

    public void editDateButtonOnAction(ActionEvent actionEvent) throws IOException {
        String newReleaseDate = dateInput.getText();

        if(newReleaseDate.equals("")) {
            editFailed.setText("Enter a value for the Release Date");
        }
        else {
            if (!this.cinema.modifyReleaseDate(this.movie, (Staff) this.user, LocalDate.parse(newReleaseDate))) {
                editFailed.setText("Movie to be edited could not be found. ");
            }
            else {
                this.movieReleaseDate.setText("Movie Release Date: " + newReleaseDate);
            }
        }
    }

    public void addCastMemberButtonOnAction(ActionEvent actionEvent) throws IOException {
        String newCastMember = castInput.getText();

        if(newCastMember.equals("")) {
            editFailed.setText("Enter a value for the Cast Member to add or delete");
        }
        else if(this.movie.getCast().contains(newCastMember)) {
            editFailed.setText("This cast member is already in the cast list.");
        }
        else {
            if (!this.cinema.addCastMember(this.movie, (Staff) this.user, newCastMember)) {
                editFailed.setText("Movie to be edited could not be found. ");
            }
            else {
                this.movieCast.setText("Movie Cast: " + this.movie.getCast().toString());
            }
        }
    }

    public void deleteCastMemberButtonOnAction(ActionEvent actionEvent) throws IOException {
        String newCastMember = castInput.getText();

        if(newCastMember.equals("")) {
            editFailed.setText("Enter a value for the Cast Member to add or delete");
        }
        else if(!this.movie.getCast().contains(newCastMember)) {
            editFailed.setText("This cast member is not in the cast list.");
        }
        else {
            if (!this.cinema.deleteCastMember(this.movie, (Staff) this.user, newCastMember)) {
                editFailed.setText("Movie to be edited could not be found. ");
            }
            else {
                this.movieCast.setText("Movie Cast: " + this.movie.getCast().toString());
            }
        }
    }

    public void editFrontRowSeatsButtonOnAction(ActionEvent actionEvent) {
        Integer newFrontRowSeatsAvailable = frontRowSeatsAvailable.getValue();
        this.cinema.modifySeatings(this.movie, (Staff) this.user, "front", newFrontRowSeatsAvailable);
    }

    public void editMiddleRowSeatsButtonOnAction(ActionEvent actionEvent) {
        Integer newMiddleRowSeatsAvailable = middleRowSeatsAvailable.getValue();
        this.cinema.modifySeatings(this.movie, (Staff) this.user, "middle", newMiddleRowSeatsAvailable);
    }

    public void editBackRowSeatsButtonOnAction(ActionEvent actionEvent) {
        Integer newBackRowSeatsAvailable = backRowSeatsAvailable.getValue();
        this.cinema.modifySeatings(this.movie, (Staff) this.user, "rear", newBackRowSeatsAvailable);
    }

    public void setSeatsAvailable() {
        SpinnerValueFactory<Integer> frontRowValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30, 30);
        frontRowSeatsAvailable.setValueFactory(frontRowValueFactory);
        SpinnerValueFactory<Integer> middleRowValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30, 30);
        middleRowSeatsAvailable.setValueFactory(middleRowValueFactory);
        SpinnerValueFactory<Integer> backRowValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30, 30);
        backRowSeatsAvailable.setValueFactory(backRowValueFactory);

    }

    public void setText() {
        this.movieTitle.setText("Movie Title: " + this.movie.getTitle());
        this.movieClassification.setText("Movie Classification: " + this.movie.getRating());
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
