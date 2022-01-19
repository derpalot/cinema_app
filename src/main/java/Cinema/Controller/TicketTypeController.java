package Cinema.Controller;

import Cinema.Customer;
import Cinema.Booking;
import Cinema.Cinema;
import Cinema.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class TicketTypeController {

    @FXML
    public TextField numberOfChildren;

    @FXML
    public TextField numberOfStudents;

    @FXML
    public TextField numberOfAdults;

    @FXML
    public TextField numberOfSeniors;

    @FXML
    public Button seatSelectionButton;

    @FXML
    public Label errorLabel;

    @FXML
    public Button logoutButton;

    @FXML
    public Button backButton;

    private Movie movie;
    private Cinema cinema;
    private Customer customer;
    private ArrayList<Booking> bookings;

    private Stage stage;
    private Scene scene;
    private Parent root;


    public void seatSelectionButtonOnAction(ActionEvent actionEvent) throws IOException {

        int children = Integer.parseInt(numberOfChildren.getText());
        int students = Integer.parseInt(numberOfStudents.getText());
        int adults = Integer.parseInt(numberOfAdults.getText());
        int seniors = Integer.parseInt(numberOfSeniors.getText());

        if (children < 0 || students < 0 || adults < 0 || seniors < 0) {
            errorLabel.setText("Number of person(s) cannot be negative. ");
        }
        else if ((children + students + adults + seniors) <= 0) {
            errorLabel.setText("Cannot book for 0 or less person(s). ");
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SeatSelection.fxml"));
            root = loader.load();

            SeatSelectionController seatSelectionController = loader.getController();
            seatSelectionController.setChildren(children);
            seatSelectionController.setStudents(students);
            seatSelectionController.setAdults(adults);
            seatSelectionController.setSeniors(seniors);
            seatSelectionController.setCinema(cinema);
            seatSelectionController.setCustomer(customer);
            seatSelectionController.setMovie(movie);

            stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Ticket Type Selection");
            scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
        }
    }

    public void logoutButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        root = loader.load();

        LoginController loginController = loader.getController();
        loginController.setCinema(cinema);

        stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Fancy Cinemas");
        scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
    }

    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpcomingMovies.fxml"));
        root = loader.load();

        UpcomingMoviesController upcomingMoviesController = loader.getController();
        upcomingMoviesController.setCinema(this.cinema);
        upcomingMoviesController.setUser(customer);
        upcomingMoviesController.initialiseTableView();
        upcomingMoviesController.setFilterCategory();

        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Upcoming Movies");
        scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
