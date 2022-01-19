package Cinema;

import java.io.IOException;
import java.util.Optional;

import Cinema.Controller.LoginController;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.json.simple.parser.ParseException;

public class CinemaApp extends Application {

    private Cinema cinema;

    @Override
    public void start(Stage primaryStage) throws IOException, ParseException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Parent root = loader.load();

        LoginController loginController = loader.getController();
        cinema = new Cinema("src/main/resources/customer.json", "src/main/resources/staff.json", "src/main/resources/manager.json", "src/main/resources/credit_cards.json", "src/main/resources/giftCards.json",
                "src/main/resources/movies.csv", "src/main/resources/booking.json");
        loginController.setCinema(cinema);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Fancy Cinemas");
        primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            try {
                closeApplication(primaryStage);
            }
            catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void closeApplication(Stage stage) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Close Window Alert");
        alert.setContentText("Do you want to clsoe the application?");
        Optional<ButtonType> choice = alert.showAndWait();
        if (choice.get() == ButtonType.OK) {
            cinema.saveInfo("src/main/resources/customer.json", "src/main/resources/staff.json", "src/main/resources/manager.json", "src/main/resources/credit_cards.json", "src/main/resources/giftCards.json", "src/main/resources/movies.csv", "src/main/resources/booking.json", "src/main/resources/cancelSummary.csv");
            stage.close();
        }
        else {
            return;
        }
    }
}
