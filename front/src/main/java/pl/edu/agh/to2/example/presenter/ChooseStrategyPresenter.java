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
import pl.edu.agh.to2.example.model.Quiz;
import pl.edu.agh.to2.example.model.Strategy;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ChooseStrategyPresenter extends AbstractPresenter {
    private Quiz quiz;
    @FXML
    private TableView<Strategy> strategiesTable;
    @FXML
    private TableColumn<Strategy, String> namesColumn;

    @FXML
    private void initialize() {
        strategiesTable.setItems(getStrategiesFromDatabase());

        strategiesTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        namesColumn.setCellValueFactory(dataValue -> dataValue.getValue().getNameProperty());
    }

    public ObservableList<Strategy> getStrategiesFromDatabase() {
        try {
            return FXCollections.observableArrayList(retrofitClient.getStrategyClient().getStrategies());
        } catch (Exception e) {
            System.err.println("Error while loading strategies: " + e.getMessage());
        }

        return FXCollections.observableArrayList();
    }

    @FXML
    private void handleOkAction(ActionEvent event) {
        Strategy strategy = strategiesTable.getSelectionModel().getSelectedItem();

        try {
            Quiz updatedQuiz = retrofitClient.getQuizClient().updateQuizStrategy(quiz.getId(), strategy);
            updateModel(updatedQuiz);

            dialogStage.close();

        } catch (Exception e) {
            showAlert(e);
        }
    }

    @FXML
    private void handleCancelAction(ActionEvent event) {
        dialogStage.close();
    }

    private void updateModel(Quiz quiz) {
        this.quiz.setStrategy(quiz.getStrategy());
        this.quiz.setForms(quiz.getFormsProperty());
    }

    @Override
    public void configure(Stage dialogStage, MainScreenController mainScreenController, QuizAppController quizAppController, SimpleBooleanProperty isDialogDisplayed) {
        super.configure(dialogStage, mainScreenController, quizAppController, isDialogDisplayed);
        setDataFromController();
    }

    private void setDataFromController() {
        quiz = mainScreenController.getCurrentQuiz();
        focusOnStrategy(quiz.getStrategy());
    }

    public void focusOnStrategy(Strategy strategy) {
        Optional<Integer> index = getIndexOf(strategy);

        if (index.isPresent()) {
            strategiesTable.getSelectionModel().select(index.get());
            strategiesTable.scrollTo(index.get());
            strategiesTable.getFocusModel().focus(index.get());
        } else {
            quizAppController.showAlert("cannot find index of strategy.");
            dialogStage.close();
        }
    }

    public Optional<Integer> getIndexOf(Strategy strategy) {
        List<Strategy> strategies = strategiesTable.getItems();

        Optional<Integer> index = Optional.empty();

        int currIndex = 0;
        for (Strategy s : strategies) {
            if (Objects.equals(s.getId(), strategy.getId())) {
                index = Optional.of(currIndex);
                break;
            }
            currIndex += 1;
        }

        return index;
    }
}
