package pl.edu.agh.to2.example.presenter;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.edu.agh.to2.example.model.Chest;
import pl.edu.agh.to2.example.model.Choice;
import pl.edu.agh.to2.example.model.Strategy;
import pl.edu.agh.to2.example.model.StrategyType;

import java.util.List;

public class AddStrategyPresenter extends AbstractPresenter{
    // strategy type table
    @FXML
    public TableView<StrategyType> strategyTypesTable;
    @FXML
    public TableColumn<StrategyType, String> strategyTypesColumn;

    // chest table
    @FXML
    public TableView<Chest> chestOptionsTable;
    @FXML
    public TableColumn<Chest, String> chestOptionsColumn;

    // added chests table
    @FXML
    public TableView<Choice> addedChestsTable;
    @FXML
    public TableColumn<Choice, Chest> addedChestsColumn;
    @FXML
    public TableColumn<Choice, Integer> addedChestsNumberColumn;

    // text filed
    @FXML
    public TextField chestNumberTextField;

    // buttons
    @FXML
    public Button addChestButton;
    @FXML
    public Button deleteAddedChestButton;
    @FXML
    private Button okButton;

    @FXML
    private void initialize() {
        initStrategyTypesTable();
        initChestsOptionsTable();
        initAddedChestsTable();

        initButtonsDisableProperties();
    }

    private void initStrategyTypesTable() {
        strategyTypesTable.setItems(getStrategyTypesFromDatabase());
        strategyTypesTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        strategyTypesColumn.setCellValueFactory(dataValue -> dataValue.getValue().getNameProperty());

        if(!strategyTypesTable.getItems().isEmpty()){
            strategyTypesTable.getSelectionModel().select(0);
        }
    }

    private void initChestsOptionsTable() {
        chestOptionsTable.setItems(getChestsFromDatabase());
        chestOptionsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        chestOptionsColumn.setCellValueFactory(dataValue -> dataValue.getValue().getNameProperty());

        if(!chestOptionsTable.getItems().isEmpty()){
            chestOptionsTable.getSelectionModel().select(0);
        }
    }

    private void initAddedChestsTable(){
        addedChestsColumn.setCellValueFactory(dataValue -> dataValue.getValue().chestProperty());
        addedChestsNumberColumn.setCellValueFactory(dataValue -> dataValue.getValue().numberProperty().asObject());
    }

    private void initButtonsDisableProperties() {
        addChestButton.disableProperty().bind(
                Bindings.isEmpty(chestNumberTextField.textProperty())
                        .or(Bindings.isEmpty(chestOptionsTable.getSelectionModel().getSelectedItems()))
        );

        deleteAddedChestButton.disableProperty().bind(Bindings.isEmpty(addedChestsTable.getSelectionModel().getSelectedItems()));

        okButton.disableProperty().bind(
                Bindings.isEmpty(addedChestsTable.getItems())
                        .or(strategyTypesTable.getSelectionModel().selectedItemProperty().isNull())
        );


    }

    private ObservableList<Chest> getChestsFromDatabase() {
        try {
            return retrofitClient.getChestClient().getAllChests();
        } catch (Exception e) {
            System.err.println("Error while loading chests: " + e.getMessage());
        }

        return FXCollections.observableArrayList();
    }

    public ObservableList<StrategyType> getStrategyTypesFromDatabase() {
        try {
            return FXCollections.observableArrayList(retrofitClient.getStrategyClient().getAllStrategyTypes());
        } catch (Exception e) {
            System.err.println("Error while loading strategies: " + e.getMessage());
        }

        return FXCollections.observableArrayList();
    }

    @FXML
    public void handleAddChestAction(ActionEvent event) {
        try {
            Integer number = Integer.parseInt(chestNumberTextField.getText());
            Chest chest = chestOptionsTable.getSelectionModel().getSelectedItem();
            Choice choice = new Choice(number, chest);

            addedChestsTable.getItems().add(choice);
        } catch (Exception e) {
            showAlert(e);
        }
    }

    @FXML
    public void handleDeleteAddedChestAction(ActionEvent event) {
        int selectedIndex = addedChestsTable.getSelectionModel().getSelectedIndex();

        addedChestsTable.getItems().remove(selectedIndex);
        addedChestsTable.getSelectionModel().clearSelection();
    }

    @FXML
    public void handleOkAction(ActionEvent event) {
        List<Choice> choices = addedChestsTable.getItems();
        StrategyType strategyType = strategyTypesTable.getSelectionModel().getSelectedItem();

        try {
            retrofitClient.getStrategyClient().addStrategy(choices, strategyType);
            dialogStage.close();

        } catch (Exception e) {
            showAlert(e);
        }
    }

    @FXML
    public void handleCancelAction(ActionEvent event) {
        dialogStage.close();
    }
}
