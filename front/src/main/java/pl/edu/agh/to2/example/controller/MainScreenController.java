package pl.edu.agh.to2.example.controller;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.edu.agh.to2.example.model.Award;
import pl.edu.agh.to2.example.model.Form;
import pl.edu.agh.to2.example.model.Quiz;

import java.util.List;


public class MainScreenController {
    private static final String QUIZ_NAME_START_LABEL = "quiz: ";
    private static final String QUIZ_STRATEGY_START_LABEL = "strategy: ";

    private QuizAppController appController;
    private Quiz displayedQuiz;

    // forms table
    @FXML
    private TableView<Form> formsTable;
    @FXML
    private TableColumn<Form, String> nickColumn;
    @FXML
    private TableColumn<Form, Float> pointsColumn;
    @FXML
    private TableColumn<Form, Integer> timestampColumn;
    @FXML
    private TableColumn<Form, String> endTimeColumn;
    @FXML
    private TableColumn<Form, Award> awardColumn;

    // quiz table
    @FXML
    private TableView<Quiz> quizTable;
    @FXML
    private TableColumn<Quiz, String> quizNameColumn;

    // buttons
    @FXML
    public Button exportQuizButton;
    @FXML
    public Button addQuizButton;
    @FXML
    private Button changeStrategyButton;
    @FXML
    public Button addChestButton;
    @FXML
    private Button addAwardButton;
    @FXML
    private Button editAwardButton;
    @FXML
    public Button showStatisticsButton;
    @FXML
    public Button addStrategyButton;


    // labels
    @FXML
    public Label quizNameLabel;
    @FXML
    public Label strategyLabel;


    @FXML
    private void initialize() {
        initFormsTable();
        initQuizTable();
    }

    private void initFormsTable() {
        timestampColumn.setCellValueFactory(dataValue -> dataValue.getValue().getTimeProperty().asObject());
        endTimeColumn.setCellValueFactory(dataValue -> dataValue.getValue().getEndTimeProperty());
        nickColumn.setCellValueFactory(dataValue -> dataValue.getValue().getNickProperty());
        awardColumn.setCellValueFactory(dataValue -> dataValue.getValue().getAwardProperty());
        pointsColumn.setCellValueFactory(dataValue -> dataValue.getValue().getPointsProperty().asObject());

        formsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private void initQuizTable() {
        quizNameColumn.setCellValueFactory(dataValue -> dataValue.getValue().getNameProperty());

        quizTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        quizTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setDisplayedQuiz(newValue);
            }
        });
    }

    public void setAppController(QuizAppController appController) {
        this.appController = appController;
        addDisablingButtonsProperties();
    }

    private void addDisablingButtonsProperties() {
        addQuizButton.disableProperty().bind(appController.isAddQuizDialogDisplayedProperty());
        addChestButton.disableProperty().bind(appController.isAddChestDialogDisplayedProperty());
        addAwardButton.disableProperty().bind(appController.isAddAwardDialogDisplayedProperty());

        editAwardButton.disableProperty().bind(
                appController.isEditAwardDialogDisplayedProperty()
                        .or(Bindings.size(formsTable.getSelectionModel().getSelectedItems()).isNotEqualTo(1))
        );

        changeStrategyButton.disableProperty().bind(
                appController.isChangeStrategyDialogDisplayedProperty()
                        .or(Bindings.size(quizTable.getItems()).isEqualTo(0))
        );

        exportQuizButton.disableProperty().bind(appController.isExportQuizDialogDisplayedProperty()
                .or(Bindings.size(quizTable.getItems()).isEqualTo(0))
        );

        showStatisticsButton.disableProperty().bind(appController.isShowStatisticsDialogDisplayedProperty()
                .or(Bindings.size(quizTable.getItems()).isEqualTo(0))
        );

        addStrategyButton.disableProperty().bind(appController.isAddStrategyDialogDisplayedProperty());
    }

    @FXML
    private void handleAddQuizAction(ActionEvent event) {
        appController.handleFileChoose();
    }

    @FXML
    private void handleExportQuizAction(ActionEvent event){
        appController.handleExport();
    }

    @FXML
    public void handleAddChestAction(ActionEvent event) {
        appController.showAddChestDialog();
    }

    @FXML
    public void handleAddAwardAction(ActionEvent event) {
        appController.showAddAwardDialog();
    }

    @FXML
    public void handleEditAwardAction(ActionEvent event) {
        appController.showEditAwardDialog();
    }

    @FXML
    public void handleChangeStrategyAction(ActionEvent event) {
        appController.showChangeStrategyDialog();
    }

    @FXML
    public void handleShowStatisticsAction(ActionEvent event) { appController.showStatisticsDialog(); }

    @FXML
    public void handleShowAddStrategyAction(ActionEvent event) {
        appController.showAddStrategyDialog();
    }

    public void setQuizList(List<Quiz> quizList) {
        quizTable.getItems().addAll(quizList);

        if (quizList.size() != 0) {
            Quiz quiz = quizList.get(0);
            setDisplayedQuiz(quiz);
        }
    }

    public void setDisplayedQuiz(Quiz quiz) {
        displayedQuiz = quiz;
        formsTable.setItems(quiz.getFormsProperty());
        focusOnQuiz(quiz);
        setQuizLabels();
    }

    private void setQuizLabels(){
        quizNameLabel.textProperty().bind(Bindings.concat(QUIZ_NAME_START_LABEL, displayedQuiz.getNameProperty()));
        strategyLabel.textProperty().bind(Bindings.concat(QUIZ_STRATEGY_START_LABEL, displayedQuiz.getStrategyProperty()));
    }

    private void focusOnQuiz(Quiz quiz) {
        if (quiz != null) {
            int index = quizTable.getItems().indexOf(quiz);

            quizTable.getSelectionModel().select(index);
            quizTable.scrollTo(index);
            quizTable.getFocusModel().focus(index);
        }
    }

    public void addQuiz(Quiz quiz) {
        quizTable.getItems().add(quiz);
        setDisplayedQuiz(quiz);
    }

    public Form getFormToEditAward() {
        return formsTable.getSelectionModel().getSelectedItem();
    }

    public Quiz getCurrentQuiz() {
        return displayedQuiz;
    }
}