package Cinema.Controller;

import Cinema.Cinema;
import Cinema.Staff;
import Cinema.User;
import Cinema.Manager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

public class ManageStaffController {

    @FXML
    public TextField usernameInput;

    @FXML
    public PasswordField passwordInput;

    @FXML
    public TableView<Staff> staffTable;

    @FXML
    public Label errorLabel;

    @FXML
    public TableColumn<Staff, String> usernameColumn;

    @FXML
    public TableColumn<Staff, String> passwordColumn;

    @FXML
    public TableColumn<Staff, Void> deleteStaffColumn;

    public ObservableList<Staff> staffMembers;

    private Cinema cinema;
    private User user;

    private Parent root;
    private Scene scene;
    private Stage stage;

    public void initialiseTable() {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        staffTable.setItems(getStaff());

        Callback<TableColumn<Staff, Void>, TableCell<Staff, Void>> deleteGiftCardCellFactory = new Callback<>() {
            @Override
            public TableCell<Staff, Void> call(TableColumn<Staff, Void> param) {
                final TableCell<Staff, Void> cell = new TableCell<>() {
                    private final Button deleteStaffMember = new Button("Delete");

                    {
                        deleteStaffMember.setOnAction((ActionEvent actionEvent) -> {
                            Staff staffMember = getTableView().getItems().get(getIndex());
                            staffTable.getItems().remove(staffMember);
                            cinema.deleteStaff(staffMember, (Manager) user);

                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        }
                        else {
                            setGraphic(deleteStaffMember);
                        }
                    }
                };
                return cell;
            }
        };

        deleteStaffColumn.setCellFactory(deleteGiftCardCellFactory);
    }

    public void addStaffButtonOnAction(ActionEvent actionEvent) {
        String username = usernameInput.getText();
        String password = passwordInput.getText();
        if (username.equals("") || password.equals("")) {
            errorLabel.setText("No empty values allowed. ");
        }
        else {
            if(this.cinema.getStaffList().size() == this.cinema.addStaff(username, password, (Manager) this.user).size()) {
                errorLabel.setText("Username already taken. ");
            }
            else {
                errorLabel.setText("Staff Member Added! Please refresh to see changes. ");
            }
        }
    }

    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ManagerFunctions.fxml"));
        root = loader.load();

        ManagerFunctionsController managerFunctionsController = loader.getController();
        managerFunctionsController.setCinema(cinema);
        managerFunctionsController.setUser(user);

        stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Manager Functions");
        scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
    }

    public ObservableList<Staff> getStaff() {
        staffMembers = FXCollections.observableArrayList();
        staffMembers.addAll(this.cinema.getStaffList());
        return staffMembers;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void refreshButtonOnAction(ActionEvent actionEvent) {
        staffTable.setItems(getStaff());
    }
}
