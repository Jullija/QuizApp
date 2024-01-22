package pl.edu.agh.to2.example.presenter;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import pl.edu.agh.to2.example.controller.MainScreenController;
import pl.edu.agh.to2.example.controller.QuizAppController;
import pl.edu.agh.to2.example.model.Award;
import pl.edu.agh.to2.example.model.Form;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class EditAwardPresenter extends AbstractPresenter {
    private Form form;
    @FXML
    private TableView<Award> awardsTable;
    @FXML
    private TableColumn<Award, String> namesColumn;
    @FXML
    private TableColumn<Award, String> descriptionsColumn;
    @FXML
    private TableColumn<Award, String> chestsStringsColumn;

    @FXML
    private void initialize() {
        setAwards(getAwardsFromDatabase());

        awardsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        namesColumn.setCellValueFactory(dataValue -> dataValue.getValue().getNameProperty());
        descriptionsColumn.setCellValueFactory(dataValue -> dataValue.getValue().getDescriptionProperty());
        chestsStringsColumn.setCellValueFactory(dataValue -> dataValue.getValue().getChestsStringsProperty());
    }

    private ObservableList<Award> getAwardsFromDatabase() {
        try {
            return retrofitClient.getAwardClient().getAllAwards();
        } catch (Exception e) {
            System.err.println("Error while loading data: " + e.getMessage());
        }

        return FXCollections.observableArrayList();
    }

    public void setAwards(ObservableList<Award> awards) {
        awardsTable.setItems(awards);
    }

    @FXML
    public void handleOkAction(ActionEvent event) {
        try {
            Award award = awardsTable.getSelectionModel().getSelectedItem();

            Form form1 = retrofitClient.getFormClient().updateAward(form.getId(), award);
            updateModel(form1.getAward());

            dialogStage.close();
        } catch (Exception e) {
            showAlert(e);
        }
    }

    @FXML
    private void handleCancelAction(ActionEvent event) {
        dialogStage.close();
    }

    private void updateModel(Award award) {
        form.setAward(award);
    }

    @Override
    public void configure(Stage dialogStage, MainScreenController mainScreenController, QuizAppController quizAppController, SimpleBooleanProperty isDialogDisplayed) {
        super.configure(dialogStage, mainScreenController, quizAppController, isDialogDisplayed);
        setDataFromController();
    }

    private void setDataFromController() {
        form = mainScreenController.getFormToEditAward();

        Award award = form.getAward();

        if(award != null){
            focusOnAward(award);
        }
    }

    public void focusOnAward(Award award) {
        Optional<Integer> index = getIndexOf(award);

        if (index.isPresent()) {
            awardsTable.getSelectionModel().select(index.get());
            awardsTable.scrollTo(index.get());
            awardsTable.getFocusModel().focus(index.get());
        } else {
            quizAppController.showAlert("cannot find index of award.");
            dialogStage.close();
        }
    }

    public Optional<Integer> getIndexOf(Award award) {
        List<Award> awards = awardsTable.getItems();

        Optional<Integer> index = Optional.empty();

        int currIndex = 0;
        for (Award a : awards) {
            if (Objects.equals(a.getId(), award.getId())) {
                index = Optional.of(currIndex);
                break;
            }
            currIndex += 1;
        }

        return index;
    }
}