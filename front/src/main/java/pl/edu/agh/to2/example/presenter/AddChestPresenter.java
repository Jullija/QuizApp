package pl.edu.agh.to2.example.presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import pl.edu.agh.to2.example.model.Chest;

public class AddChestPresenter extends AbstractPresenter{
    @FXML
    private TextField nameTextField;

    @FXML
    private void initialize() {}

    @FXML
    private void handleOkAction(ActionEvent event) {

        String name = nameTextField.getText();

        Chest chest = new Chest(name);

        try {
            retrofitClient.getChestClient().addChest(chest);
            dialogStage.close();

        } catch (Exception e) {
            showAlert(e);
        }
    }

    @FXML
    private void handleCancelAction(ActionEvent event) {
        dialogStage.close();
    }

}
