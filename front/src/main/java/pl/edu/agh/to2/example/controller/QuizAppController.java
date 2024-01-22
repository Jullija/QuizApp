package pl.edu.agh.to2.example.controller;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.edu.agh.to2.example.model.Quiz;
import pl.edu.agh.to2.example.presenter.IPresenter;
import pl.edu.agh.to2.example.retrofit.client.RetrofitClient;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuizAppController {
    private static final String MAIN_SCREEN_DIALOG_PATH = "/view/MainScreen.fxml";
    private static final String ADD_CHEST_DIALOG_PATH = "/view/AddChestDialog.fxml";
    private static final String ADD_AWARD_DIALOG_PATH = "/view/AddAwardDialog.fxml";
    private static final String EDIT_AWARD_DIALOG_PATH = "/view/EditAwardDialog.fxml";
    private static final String CHANGE_STRATEGY_DIALOG_PATH = "/view/ChangeStrategyDialog.fxml";
    private static final String CHART_DIALOG_PATH = "/view/QuestionChart.fxml";
    private static final String ADD_STRATEGY_DIALOG_PATH = "/view/AddStrategyDialog.fxml";

    private static final String ADD_CHEST_DIALOG_TITLE = "Add chest dialog";
    private static final String ADD_AWARD_DIALOG_TITLE = "Add award dialog";
    private static final String EDIT_AWARD_DIALOG_TITLE = "Edit award dialog";
    private static final String CHANGE_STRATEGY_DIALOG_TITLE = "Change strategy dialog";
    private static final String SHOW_STATISTICS_DIALOG_TITLE = "show statistics dialog";
    private static final String ADD_STRATEGY_DIALOG_TITLE = "add strategy dialog";

    private static final String OPERATION_FAILED_TITLE = "Information";
    private static final String OPERATION_FAILED_HEADER = "Operation failed.";

    private static final String ACCEPTED_FILE_DESCRIPTION = "XLSX files (*.xlsx)";
    private static final String ACCEPTED_EXTENSION = "*.xlsx";

    private final Stage primaryStage;
    private MainScreenController mainScreenController;
    private final RetrofitClient retrofitClient = new RetrofitClient();

    private final SimpleBooleanProperty isAddQuizDialogDisplayed = new SimpleBooleanProperty(false);
    private final SimpleBooleanProperty isAddChestDialogDisplayed = new SimpleBooleanProperty(false);
    private final SimpleBooleanProperty isAddAwardDialogDisplayed = new SimpleBooleanProperty(false);
    private final SimpleBooleanProperty isEditAwardDialogDisplayed = new SimpleBooleanProperty(false);
    private final SimpleBooleanProperty isChangeStrategyDialogDisplayed = new SimpleBooleanProperty(false);
    private final SimpleBooleanProperty isShowStatisticsDialogDisplayed = new SimpleBooleanProperty(false);
    private final  SimpleBooleanProperty isExportQuizDialogDisplayedProperty = new SimpleBooleanProperty(false);
    private final SimpleBooleanProperty isAddStrategyDialogDisplayed = new SimpleBooleanProperty(false);


    public QuizAppController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initRootLayout() {
        try {
            // load layout from FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(QuizAppController.class.getResource(MAIN_SCREEN_DIALOG_PATH));
            BorderPane rootLayout = loader.load();

            // set initial data into controller
            mainScreenController = loader.getController();
            mainScreenController.setAppController(this);

            // load data
            LoadDataFromDatabase();

            // add layout to a scene and show them all
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void LoadDataFromDatabase() throws InterruptedException {
        List<Quiz> quizList = getQuizzesFromDatabase();
        mainScreenController.setQuizList(quizList);
    }

    private List<Quiz> getQuizzesFromDatabase() {
        try {
            return retrofitClient.getQuizClient().getAllQuizzes();
        } catch (Exception e) {
            System.err.println("Error while loading data: " + e.getMessage());
        }

        return new ArrayList<>();
    }

    public void showAddChestDialog() {
        showDialog(ADD_CHEST_DIALOG_TITLE, ADD_CHEST_DIALOG_PATH, isAddChestDialogDisplayed);
    }

    public void showAddAwardDialog() {
        showDialog(ADD_AWARD_DIALOG_TITLE, ADD_AWARD_DIALOG_PATH, isAddAwardDialogDisplayed);
    }

    public void showEditAwardDialog() {
        showDialog(EDIT_AWARD_DIALOG_TITLE, EDIT_AWARD_DIALOG_PATH, isEditAwardDialogDisplayed);
    }

    public void showChangeStrategyDialog() {
        showDialog(CHANGE_STRATEGY_DIALOG_TITLE, CHANGE_STRATEGY_DIALOG_PATH, isChangeStrategyDialogDisplayed);
    }

    public void showStatisticsDialog(){
        showDialog(SHOW_STATISTICS_DIALOG_TITLE, CHART_DIALOG_PATH, isShowStatisticsDialogDisplayed);
    }

    public void showAddStrategyDialog() {
        showDialog(ADD_STRATEGY_DIALOG_TITLE, ADD_STRATEGY_DIALOG_PATH, isAddStrategyDialogDisplayed);
    }

    public void showDialog(String dialogTitle, String resourcePath, SimpleBooleanProperty isDialogDisplayed) {
        try {
            // Load the fxml file and create a new stage for the dialog
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(QuizAppController.class.getResource(resourcePath));
            BorderPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = getDialogStage(dialogTitle, page);

            // Set the transaction into the presenter.
            IPresenter presenter = loader.getController();
            presenter.configure(dialogStage, mainScreenController, this, isDialogDisplayed);

            dialogStage.setOnShowing(event -> isDialogDisplayed.set(true));
            dialogStage.setOnHidden(event -> isDialogDisplayed.set(false));

            dialogStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Stage getDialogStage(String title, BorderPane page) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle(title);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        return dialogStage;
    }

    public Boolean handleExport(){
        isExportQuizDialogDisplayedProperty.set(true);

        try {
            Quiz quiz = mainScreenController.getCurrentQuiz();
            retrofitClient.getQuizClient().exportQuiz(quiz.getId());
            isExportQuizDialogDisplayedProperty.set(false);
            return true;

        } catch (Exception e) {
            showAlert(e.getMessage());
            isExportQuizDialogDisplayedProperty.set(false);
            return false;
        }
    }

    public void handleFileChoose() {
        isAddQuizDialogDisplayedProperty().set(true);

        Optional<File> file = getFileFromFileChooser();

        try {
            Quiz quiz = retrofitClient.getQuizClient().addFile(file.get());

            mainScreenController.addQuiz(quiz);
            isAddQuizDialogDisplayed.set(false);

        } catch (Exception e) {
            showAlert(e.getMessage());
            isAddQuizDialogDisplayed.set(false);
        }
    }

    private Optional<File> getFileFromFileChooser() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(ACCEPTED_FILE_DESCRIPTION, ACCEPTED_EXTENSION);
        fileChooser.getExtensionFilters().add(extFilter);
        return Optional.ofNullable(fileChooser.showOpenDialog(primaryStage));
    }

    public void showAlert(String error) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(QuizAppController.OPERATION_FAILED_TITLE);
        alert.setHeaderText(QuizAppController.OPERATION_FAILED_HEADER);

        alert.setContentText("error code: " + error);

        alert.showAndWait();
    }

    public boolean isIsAddQuizDialogDisplayed() {
        return isAddQuizDialogDisplayed.get();
    }

    public SimpleBooleanProperty isAddQuizDialogDisplayedProperty() {
        return isAddQuizDialogDisplayed;
    }

    public boolean isIsAddAwardDialogDisplayed() {
        return isAddAwardDialogDisplayed.get();
    }

    public SimpleBooleanProperty isAddAwardDialogDisplayedProperty() {
        return isAddAwardDialogDisplayed;
    }

    public boolean isIsEditAwardDialogDisplayed() {
        return isEditAwardDialogDisplayed.get();
    }

    public SimpleBooleanProperty isEditAwardDialogDisplayedProperty() {
        return isEditAwardDialogDisplayed;
    }

    public boolean isIsChangeStrategyDialogDisplayed() {
        return isChangeStrategyDialogDisplayed.get();
    }

    public SimpleBooleanProperty isChangeStrategyDialogDisplayedProperty() {
        return isChangeStrategyDialogDisplayed;
    }

    public SimpleBooleanProperty isAddChestDialogDisplayedProperty() {
        return isAddChestDialogDisplayed;
    }

    public SimpleBooleanProperty isExportQuizDialogDisplayedProperty(){
        return isExportQuizDialogDisplayedProperty;
    }

    public SimpleBooleanProperty isShowStatisticsDialogDisplayedProperty() {
        return isShowStatisticsDialogDisplayed;
    }

    public SimpleBooleanProperty isAddStrategyDialogDisplayedProperty() {
        return isAddStrategyDialogDisplayed;
    }
}
