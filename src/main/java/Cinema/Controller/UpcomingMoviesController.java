package Cinema.Controller;

import Cinema.Cinema;
import Cinema.Movie;
import Cinema.User;
import Cinema.Staff;
import Cinema.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.LocalDateTimeStringConverter;


import java.io.IOException;
import java.time.LocalDateTime;

public class UpcomingMoviesController {

    @FXML
    public Button filterButton;

    @FXML
    public Label errorLabel;

    @FXML
    public Button backButton;

    @FXML
    private TableView<Movie> tableView;

    @FXML
    private TableColumn<Movie, String> titleColumn;

    @FXML
    private TableColumn<Movie, String> classificationColumn;

    @FXML
    private TableColumn<Movie, Void> bookButtonColumn;

    @FXML
    public TableColumn<Movie, Void> deleteMovieColumn;

    @FXML
    public TableColumn<Movie, String> locationColumn;

    @FXML
    public TableColumn<Movie, LocalDateTime> timeColumn;

    @FXML
    public TableColumn<Movie, Void> moreInformationColumn;

    @FXML
    private TableColumn<Movie, String> screenSizeColumn;

    @FXML
    private ChoiceBox<String> filterCategory;

    @FXML
    private TextField filterValue;

    private ObservableList<Movie> movies;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private Cinema cinema;
    private User user;

    @FXML
    public void initialiseTableView() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        classificationColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        screenSizeColumn.setCellValueFactory(new PropertyValueFactory<>("screen"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        tableView.setItems(getMovies());

        tableView.setEditable(this.user.canEdit());

        if (tableView.isEditable()) {
            locationColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            locationColumn.setOnEditCommit(e -> {
                Movie movie = e.getRowValue();
                movie.setLocation(e.getNewValue());
            });
            screenSizeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            screenSizeColumn.setOnEditCommit(e -> {
                    Movie movie = e.getRowValue();
                    movie.setScreenSize(e.getNewValue());
            });
            timeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateTimeStringConverter()));
            timeColumn.setOnEditCommit(e -> {
                Movie movie = e.getRowValue();
                movie.setTime(e.getNewValue());
            });
        }

        /*From GitHub https://github.com/hocinebouarara/operations-of-table/blob/master/src/tableView/TableViewController.java */

        Callback<TableColumn<Movie, Void>, TableCell<Movie, Void>> bookingCellFactory = new Callback<>() {
            @Override
            public TableCell<Movie, Void> call(TableColumn<Movie, Void> param) {
                final TableCell<Movie, Void> cell = new TableCell<>() {
                    private final Button book = new Button("Book");

                    {
                        book.setOnAction((ActionEvent actionEvent) -> {
                            if (user.canBook()) {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/TicketTypes.fxml"));
                                try {
                                    root = loader.load();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                TicketTypeController ticketTypeController = loader.getController();
                                ticketTypeController.setMovie(getTableView().getItems().get(getIndex()));
                                ticketTypeController.setCustomer((Customer) user);
                                ticketTypeController.setCinema(cinema);

                                stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
                                stage.setTitle("Ticket Type Selection");
                                scene = new Scene(root);
                                stage.setScene(scene);

                                stage.show();
                            }
                            else {
                                errorLabel.setText("Must be a customer to book movies.\nPlease go back and register. ");
                            }
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        }
                        else {
                            setGraphic(book);
                        }
                    }
                };
                return cell;
            }
        };

        bookButtonColumn.setCellFactory(bookingCellFactory);

        Callback<TableColumn<Movie, Void>, TableCell<Movie, Void>> deleteMovieCellFactory = new Callback<>() {
            @Override
            public TableCell<Movie, Void> call(TableColumn<Movie, Void> param) {
                final TableCell<Movie, Void> cell = new TableCell<>() {
                    private final Button deleteMovie = new Button("Delete");

                    {
                        deleteMovie.setOnAction((ActionEvent actionEvent) -> {
                            if (user.canEdit()) {
                                Movie movie = getTableView().getItems().get(getIndex());
                                tableView.getItems().remove(movie);
                                cinema.deleteMovie(movie, (Staff) user);
                            }
                            else {
                                errorLabel.setText("Must be a Cinema Staff Member to Delete Movies. ");
                            }
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        }
                        else {
                            setGraphic(deleteMovie);
                        }
                    }
                };
                return cell;
            }
        };

        deleteMovieColumn.setCellFactory(deleteMovieCellFactory);

        Callback<TableColumn<Movie, Void>, TableCell<Movie, Void>> moreInformationCellFactory = new Callback<>() {
            @Override
            public TableCell<Movie, Void> call(TableColumn<Movie, Void> param) {
                final TableCell<Movie, Void> cell = new TableCell<>() {
                    private final Hyperlink moreInformation = new Hyperlink("More Information");

                    {
                        moreInformation.setOnAction((ActionEvent actionEvent) -> {
                            FXMLLoader loader;
                            if(user.canEdit()) loader = new FXMLLoader(getClass().getResource("/StaffMovieInformation.fxml"));
                            else loader = new FXMLLoader(getClass().getResource("/MovieInformation.fxml"));
                            try {
                                root = loader.load();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if(user.canEdit()) {
                                StaffMovieInformationController staffMovieInformationController = loader.getController();
                                staffMovieInformationController.setCinema(cinema);
                                staffMovieInformationController.setUser(user);
                                staffMovieInformationController.setMovie(getTableView().getItems().get(getIndex()));
                                staffMovieInformationController.setText();
                                staffMovieInformationController.setSeatsAvailable();
                            }
                            else {
                                MovieInformationController movieInformationController = loader.getController();
                                movieInformationController.setCinema(cinema);
                                movieInformationController.setUser(user);
                                movieInformationController.setMovie(getTableView().getItems().get(getIndex()));
                                movieInformationController.setText();
                            }

                            stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
                            stage.setTitle("Movie Information");
                            scene = new Scene(root);
                            stage.setScene(scene);

                            stage.show();
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        }
                        else {
                            setGraphic(moreInformation);
                        }
                    }
                };
                return cell;
            }
        };

        moreInformationColumn.setCellFactory(moreInformationCellFactory);
    }

    public void filterButtonOnAction(ActionEvent actionEvent) {
        ObservableList<Movie> filteredMovies = FXCollections.observableArrayList();
        if (this.filterValue.getText().equals("")) {
            filteredMovies = FXCollections.observableArrayList(this.cinema.getMovieList());
        }
        else if (this.filterCategory.getValue().equals("Location")) {
            filteredMovies.addAll(this.cinema.getLocationMovies(this.filterValue.getText()));
        }
        else if (this.filterCategory.getValue().equals("Screen Size")) {
            filteredMovies.addAll(this.cinema.getScreenMovies(this.filterValue.getText()));
        }
        this.movies = filteredMovies;
        tableView.setItems(this.movies);
    }

    public ObservableList<Movie> getMovies() {
        movies = FXCollections.observableArrayList();
        movies.addAll(this.cinema.getMovieList());
        return movies;
    }

    public void addMovieOnAction(ActionEvent actionEvent) throws IOException {
        if (this.user.canEdit()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddMovie.fxml"));
            root = loader.load();

            AddMovieController addMovieController = loader.getController();
            addMovieController.setCinema(cinema);
            addMovieController.setUser(this.user);

            stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Add Movie");
            scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
        }
        else {
            errorLabel.setText("Must be a Cinema Staff Member to Add Movies. ");
        }
    }

    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        if (this.user.canManageStaff()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ManagerFunctions.fxml"));
            root = loader.load();

            ManagerFunctionsController managerFunctionsController = loader.getController();
            managerFunctionsController.setCinema(this.cinema);
            managerFunctionsController.setUser(this.user);

            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Manager Functions");
            scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
        }
        else if (this.user.canEdit()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/StaffFunctions.fxml"));
            root = loader.load();

            StaffFunctionsController staffFunctionsController = loader.getController();
            staffFunctionsController.setCinema(this.cinema);
            staffFunctionsController.setUser(this.user);

            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Staff Functions");
            scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
        }
        else {
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

    public void setUser(User user) {
        this.user = user;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public void setFilterCategory() {
        filterCategory.getItems().addAll("Screen Size", "Location");
    }
}
