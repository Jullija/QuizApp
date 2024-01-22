package pl.edu.agh.to2.example.presenter;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.stage.Stage;
import pl.edu.agh.to2.example.controller.MainScreenController;
import pl.edu.agh.to2.example.controller.QuizAppController;
import pl.edu.agh.to2.example.retrofit.client.RetrofitClient;

public abstract class AbstractPresenter implements IPresenter {
    public Stage dialogStage;
    public MainScreenController mainScreenController;
    public QuizAppController quizAppController;

    public RetrofitClient retrofitClient = new RetrofitClient();

    @Override
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @Override
    public void setQuizController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    @Override
    public void setAppController(QuizAppController quizAppController) {
        this.quizAppController = quizAppController;
    }

    @Override
    public void configure(Stage dialogStage, MainScreenController mainScreenController, QuizAppController quizAppController, SimpleBooleanProperty isDialogDisplayed) {
        setDialogStage(dialogStage);
        isDialogDisplayed.set(true);

        setQuizController(mainScreenController);
        setAppController(quizAppController);
    }

    public void showAlert(Exception e){
        quizAppController.showAlert(e.getMessage());
    }
}
