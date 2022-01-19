package Cinema.Controller;

import Cinema.Cinema;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import Cinema.Customer;
import Cinema.User;

import java.io.IOException;


public class LoginController {

    @FXML
    private TextField loginUsername;

    @FXML
    private PasswordField loginPassword;

    @FXML
    private Button login;

    @FXML
    private Hyperlink enterAsGuest;

    @FXML
    private TextField registerUsername;

    @FXML
    private TextField registerPassword;

    @FXML
    private Button register;

    @FXML
    private Label loginFailed;

    @FXML
    private Label registerFailed;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private Cinema cinema;

    @FXML
    public void loginButtonOnAction(ActionEvent actionEvent) throws IOException {
        String username = loginUsername.getText();
        String password = loginPassword.getText();

        if (username.equals("") || password.equals("")) {
            loginFailed.setText("Username or password cannot be blank");
        }
        else {
            Customer customer = cinema.login(username);
            if (customer != null) {
                if (customer.passwordCheck(password)) {
                    if (customer.canManageStaff()) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ManagerFunctions.fxml"));
                        root = loader.load();

                        ManagerFunctionsController managerFunctionsController = loader.getController();
                        managerFunctionsController.setCinema(this.cinema);
                        managerFunctionsController.setUser(customer);

                        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        stage.setTitle("Manager Functions");
                        scene = new Scene(root);
                        stage.setScene(scene);

                        stage.show();
                    }
                    else if (customer.canEdit()) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/StaffFunctions.fxml"));
                        root = loader.load();

                        StaffFunctionsController staffFunctionsController = loader.getController();
                        staffFunctionsController.setCinema(this.cinema);
                        staffFunctionsController.setUser(customer);

                        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        stage.setTitle("Staff Functions");
                        scene = new Scene(root);
                        stage.setScene(scene);

                        stage.show();
                    }
                    else {
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
                }
                else {
                    loginFailed.setText("Incorrect password. Please try again. ");
                }
            }
            else {
                loginFailed.setText("No account with username: " + username + " exists. ");
            }

        }
    }

    @FXML
    public void registerButtonOnAction(ActionEvent actionEvent) throws IOException {
        String username = registerUsername.getText();
        String password = registerPassword.getText();

        if (username.equals("") || password.equals("")) {
            registerFailed.setText("Username or password cannot be blank");
        }
        else {
            Customer customer  = cinema.registerUser(username, password);
            if (customer != null) {
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
            else {
                registerFailed.setText("Username has been taken. ");
            }
        }
    }

    @FXML
    public void enterAsGuestOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpcomingMovies.fxml"));
        root = loader.load();

        User user = new User();

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

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }
}