package Cinema.Controller;

import Cinema.Cinema;
import Cinema.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// import java.awt.event.ActionEvent;
import java.io.IOException;

public class ManagerFunctionsController {

    private Parent root;
    private Scene scene;
    private Stage stage;

    private Cinema cinema;
    private User user;

    public void editUpcomingMoviesButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpcomingMovies.fxml"));
        root = loader.load();

        UpcomingMoviesController upcomingMoviesController = loader.getController();
        upcomingMoviesController.setCinema(this.cinema);
        upcomingMoviesController.setUser(user);
        upcomingMoviesController.initialiseTableView();
        upcomingMoviesController.setFilterCategory();

        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Upcoming Movies");
        scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
    }

    public void editGiftCardsButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GiftCards.fxml"));
        root = loader.load();

        GiftCardsController giftCardsController = loader.getController();
        giftCardsController.setCinema(this.cinema);
        giftCardsController.setUser(this.user);
        giftCardsController.initialiseTableView();

        stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Fancy Cinemas Gift Cards");
        scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
    }

    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
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

    public void downloadUpcomingMoviesButtonOnAction(ActionEvent actionEvent) throws IOException {
        this.cinema.upcomingMovieCSV();
    }

    public void downloadMovieBookingsButtonOnAction(ActionEvent actionEvent) throws IOException {
        this.cinema.bookingSummary();
    }

    public void editStaffButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ManageStaff.fxml"));
        root = loader.load();

        ManageStaffController manageStaffController = loader.getController();
        manageStaffController.setCinema(cinema);
        manageStaffController.setUser(user);
        manageStaffController.initialiseTable();

        stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Manage Staff");
        scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
    }

    public void cancelledTransactionsButtonOnAction(ActionEvent actionEvent) throws IOException {
        this.cinema.cancelSummary("src/main/resources/cancelSummary.csv");
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
