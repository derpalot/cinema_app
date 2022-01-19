package Cinema.Controller;

import Cinema.Cinema;
import Cinema.User;
import Cinema.GiftCard;
import Cinema.Staff;

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
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.DoubleStringConverter;

import java.io.IOException;

public class GiftCardsController {

    @FXML
    public TextField giftCardNumberInput;

    @FXML
    public TextField giftCardBalanceInput;

    @FXML
    public TableView<GiftCard> giftCardsTable;

    @FXML
    public TableColumn<GiftCard, String> giftCardNumberColumn;

    @FXML
    public TableColumn<GiftCard, Double> giftCardBalanceColumn;

    @FXML
    public TableColumn<GiftCard, Boolean> giftCardStatusColumn;

    @FXML
    public TableColumn<GiftCard, Void> redeemButtonColumn;

    @FXML
    public TableColumn<GiftCard, Void> deleteButtonColumn;

    @FXML
    public Label errorLabel;

    private ObservableList<GiftCard> giftCards;

    private Cinema cinema;
    private User user;

    private Parent root;
    private Scene scene;
    private Stage stage;

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
        else {
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
    }

    public void initialiseTableView() {
        giftCardNumberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        giftCardBalanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
        giftCardStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        giftCardsTable.setItems(getGiftCards());

        giftCardsTable.setEditable(true);

        giftCardNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        giftCardNumberColumn.setOnEditCommit(e -> {
            GiftCard giftCard = e.getRowValue();
            GiftCard newGiftCard = new GiftCard(e.getNewValue(), giftCard.getStatus(), giftCard.getBalance());
            if (newGiftCard.isValid()) {
                giftCard.setNumber(e.getNewValue());
                errorLabel.setText("Gift Card Number changed!");
            }
            else {
                errorLabel.setText("Gift Card Number must be of length 18 with the first 16 characters" +
                        "\n being digits and the last two characters GC. ");
                giftCardsTable.getColumns().get(0).setVisible(false);
                giftCardsTable.getColumns().get(0).setVisible(true);
            }
        });
        giftCardBalanceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        giftCardBalanceColumn.setOnEditCommit(e -> {
            GiftCard giftCard = e.getRowValue();
            GiftCard newGiftCard = new GiftCard(giftCard.getNumber(), giftCard.getStatus(), e.getNewValue());
            if (newGiftCard.isValid()) {
                giftCard.setBalance(e.getNewValue());
                errorLabel.setText("Gift Card Balance changed!");

            }
            else {
                errorLabel.setText("Gift Card balance must not be 0 or less. ");
                giftCardsTable.getColumns().get(1).setVisible(false);
                giftCardsTable.getColumns().get(1).setVisible(true);
            }
        });
        giftCardStatusColumn.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanStringConverter()));
        giftCardStatusColumn.setOnEditCommit(e -> {
            GiftCard giftCard = e.getRowValue();
            giftCard.setStatus(e.getNewValue());
        });

        Callback<TableColumn<GiftCard, Void>, TableCell<GiftCard, Void>> deleteGiftCardCellFactory = new Callback<>() {
            @Override
            public TableCell<GiftCard, Void> call(TableColumn<GiftCard, Void> param) {
                final TableCell<GiftCard, Void> cell = new TableCell<>() {
                    private final Button deleteGiftCard = new Button("Delete");

                    {
                        deleteGiftCard.setOnAction((ActionEvent actionEvent) -> {
                            GiftCard giftCard = getTableView().getItems().get(getIndex());
                            giftCardsTable.getItems().remove(giftCard);
                            cinema.deleteGiftCard(giftCard, (Staff) user);

                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        }
                        else {
                            setGraphic(deleteGiftCard);
                        }
                    }
                };
                return cell;
            }
        };

        deleteButtonColumn.setCellFactory(deleteGiftCardCellFactory);

        Callback<TableColumn<GiftCard, Void>, TableCell<GiftCard, Void>> redeemGiftCardCellFactory = new Callback<>() {
            @Override
            public TableCell<GiftCard, Void> call(TableColumn<GiftCard, Void> param) {
                final TableCell<GiftCard, Void> cell = new TableCell<>() {
                    private final Button redeemGiftCard = new Button("Redeem");

                    {
                        redeemGiftCard.setOnAction((ActionEvent actionEvent) -> {
                            GiftCard giftCard = getTableView().getItems().get(getIndex());
                            cinema.redeemGiftCard(giftCard.getNumber(), (Staff) user);
                            giftCardsTable.getColumns().get(2).setVisible(false);
                            giftCardsTable.getColumns().get(2).setVisible(true);
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        }
                        else {
                            setGraphic(redeemGiftCard);
                        }
                    }
                };
                return cell;
            }
        };

        redeemButtonColumn.setCellFactory(redeemGiftCardCellFactory);
    }

    private ObservableList<GiftCard> getGiftCards() {
        giftCards = FXCollections.observableArrayList();
        giftCards.addAll(this.cinema.getGiftCardList());
        return giftCards;
    }

    public void addGiftCardButtonOnAction(ActionEvent actionEvent) {
        String number = giftCardNumberInput.getText();
        String strBalance = giftCardBalanceInput.getText();
        if (number.equals("") || strBalance.equals("")) {
            errorLabel.setText("No empty values allowed. ");
        }
        else {
            Double balance = Double.parseDouble(strBalance);

            GiftCard giftCard = new GiftCard(number, true, balance);
            if (giftCard.isValid()) {
                if (this.cinema.addGiftCard(giftCard, (Staff) this.user)) {
                    errorLabel.setText("New GiftCard Added! Refresh Table to see the new Card.");
                }
                else {
                    errorLabel.setText("Gift Card with the same number already exists.\nPlease try again.");
                }
            }
            else {
                errorLabel.setText("Gift Card Number must be of length 18 with the first 16 characters" +
                        "\n being digits and the last two characters GC.\nGift Card Balance cannot be 0 or less. ");
            }
        }
    }



    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void refreshTableButtonOnAction(ActionEvent actionEvent) {
        giftCardsTable.setItems(getGiftCards());
    }
}
