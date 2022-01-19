package Cinema.Controller;

import Cinema.Cinema;
import Cinema.Movie;
import Cinema.Booking;
import Cinema.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class SeatSelectionController {

    private int children;
    private int students;
    private int adults;
    private int seniors;

    private Cinema cinema;
    private Customer customer;
    private Movie movie;

    @FXML
    public Button logoutButton;

    @FXML
    public Button backButton;


    @FXML
    private TextField numberOfFrontRowSeats;

    @FXML
    private TextField numberOfMiddleRowSeats;

    @FXML
    private TextField numberOfBackRowSeats;

    @FXML
    public Label errorLabel;

    private Stage stage;
    private Scene scene;
    private Parent root;


    public void proceedToCheckoutButtonOnAction(ActionEvent actionEvent) throws IOException {
        //load payment screen
        int frontRowSeats = Integer.parseInt(numberOfFrontRowSeats.getText());
        int middleRowSeats = Integer.parseInt(numberOfMiddleRowSeats.getText());
        int backRowSeats = Integer.parseInt(numberOfBackRowSeats.getText());

        //error checking
        if (frontRowSeats < 0 || middleRowSeats < 0 || backRowSeats < 0) {
            errorLabel.setText("Number of seat(s) cannot be negative. ");
        }
        else if ((frontRowSeats + middleRowSeats + backRowSeats) <= 0) {
            errorLabel.setText("Cannot book for 0 or less seat(s). ");
        }

        else {
            ArrayList<Booking> bookings = cinema.bookMovie(this.movie, children, students, adults, seniors, frontRowSeats, middleRowSeats, backRowSeats,
                    this.movie.getScreen(), this.movie.getLocation(), this.movie.getTime().toString(), (Customer) this.customer);
            if (bookings == null) {
                errorLabel.setText("Number of person(s) and number of seat(s) do not match. Please try again. ");
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Payment.fxml"));
                root = loader.load();

                PaymentController paymentController = loader.getController();
                paymentController.setBookings(bookings);
                paymentController.setCinema(cinema);
                paymentController.setCustomer(customer);
                paymentController.setMovie(movie);
                if (this.customer.getAutoFill()) {
                    paymentController.setText(this.customer.getCard().getName(), this.customer.getCard().getNumber());
                }

                stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
                stage.setTitle("Payment");
                scene = new Scene(root);
                stage.setScene(scene);

                stage.show();
            }
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

    public void backButtonOnAction(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TicketTypes.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        TicketTypeController ticketTypeController = loader.getController();
        ticketTypeController.setMovie(movie);
        ticketTypeController.setCustomer(customer);
        ticketTypeController.setCinema(cinema);

        stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Ticket Type Selection");
        scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public void setStudents(int students) {
        this.students = students;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public void setSeniors(int seniors) {
        this.seniors = seniors;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
