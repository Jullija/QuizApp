package pl.edu.agh.to2.example.presenter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import pl.edu.agh.to2.example.model.Award;
import pl.edu.agh.to2.example.model.Chest;

import java.util.List;

public class AddAwardPresenter extends AbstractPresenter {
    @FXML
    public TableView<Chest> chestsTable;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private TableColumn<Chest, String> chestNamesColumn;

    @FXML
    private void initialize() {
        chestsTable.setItems(getChestsFromDatabase());

        chestsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        chestNamesColumn.setCellValueFactory(dataValue -> dataValue.getValue().getNameProperty());
    }

    @FXML
    private void handleOkAction(ActionEvent event) {

        String name = nameTextField.getText();
        String description = descriptionTextField.getText();
        List<Chest> chests = chestsTable.getSelectionModel().getSelectedItems();

        Award award = new Award(name, description, FXCollections.observableList(chests));

        try {
            retrofitClient.getAwardClient().addAward(award);
            dialogStage.close();

        } catch (Exception e) {
            showAlert(e);
        }
    }

    @FXML
    private void handleCancelAction(ActionEvent event) {
        dialogStage.close();
    }

    private ObservableList<Chest> getChestsFromDatabase() {
        try {
            return retrofitClient.getChestClient().getAllChests();
        } catch (Exception e) {
            System.err.println("Error while loading chests: " + e.getMessage());
        }

        return FXCollections.observableArrayList();
    }
}
