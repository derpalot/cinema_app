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
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class PaymentController {

    @FXML
    public TextField cardName;

    @FXML
    public TextField cardNumber;

    @FXML
    public TextField giftCardNumber;

    @FXML
    public Button payWithCardButton;

    @FXML
    public Button payWithGiftCardButton;

    @FXML
    public Label resultLabel;

    @FXML
    public Button cancelButton;

    private Movie movie;
    private Cinema cinema;
    private Customer customer;
    private ArrayList<Booking> bookings;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void payWithCardButtonOnAction(ActionEvent actionEvent) throws IOException {

        String name = cardName.getText();
        String number = cardNumber.getText();

        int result = this.cinema.payment(name, number, "card", this.bookings);
        if (result == 0) {
            resultLabel.setText("Card details incorrect, please try again. ");
        }
        else if (result == 5) {
            resultLabel.setText("Error carrying out transaction, please try again later. ");
        }
        else {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like" +
                    "your card details saved\nfor the next transaction?", yes, no);
            alert.setTitle("Autofill confirmation. ");
            Optional<ButtonType> res = alert.showAndWait();
            if (res.get() == yes) {
                this.customer.setAutoFill(true);
                this.customer.saveCardDetails(this.cinema.checkCard(cardName.getText()));
            }
            else {
                this.customer.setAutoFill(false);
            }
            System.out.println("Transaction successful: ");
            System.out.println("Transaction id: " + this.cinema.transaction(this.bookings));
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
    }

    public void payWithGiftCardButtonOnAction(ActionEvent actionEvent) throws IOException {

        String number = giftCardNumber.getText();
        int result = this.cinema.payment(null, number,"giftCard", this.bookings);

        if (result == 4) {
            resultLabel.setText("GiftCard details incorrect, please try again. ");
        }
        else if (result == 3) {
            resultLabel.setText("GiftCard has insufficient funds. ");
        }
        else if (result == 2) {
            resultLabel.setText("GiftCard has already been redeemed. ");
        }
        else if (result == 5) {
            resultLabel.setText("Error carrying out transaction, please try again later. ");
        }
        else {
            System.out.println("Transaction successful: ");
            System.out.println("Transaction id: " + this.cinema.transaction(this.bookings));
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

    public void setBookings(ArrayList<Booking> bookings) {
        this.bookings = bookings;
    }

    public void setText(String cardName, String cardNumber) {
        this.cardName.setText(cardName);
        this.cardNumber.setText(cardNumber);
    }

    public void cancelButtonOnAction(ActionEvent actionEvent) throws IOException {
        //revert booking
        String[] reasons = {"I no longer want to book for this movie", "card payment failed"};
        ChoiceDialog<String> reason = new ChoiceDialog<String>(reasons[0], reasons);
        reason.setContentText("What is your reason for\n" +
                "cancelling the transaction?");
        reason.setHeaderText("Cancel Transaction");
        Optional<String> res = reason.showAndWait();
        if (res.get().equals("I no longer want to book for this movie")) {
            this.cinema.cancelBooking(this.bookings, "user cancelled", LocalDateTime.now(), this.customer);
        }
        else {
            this.cinema.cancelBooking(this.bookings, res.get(), LocalDateTime.now(), this.customer);
        }

        System.out.println("Booking Transaction Cancelled");
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
}
