package pl.edu.agh.to2.example.presenter;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.stage.Stage;
import pl.edu.agh.to2.example.controller.MainScreenController;
import pl.edu.agh.to2.example.controller.QuizAppController;

public interface IPresenter {
    void setDialogStage(Stage dialogStage);

    void setQuizController(MainScreenController mainScreenController);

    void setAppController(QuizAppController quizAppController);

    void configure(Stage dialogStage, MainScreenController mainScreenController, QuizAppController quizAppController, SimpleBooleanProperty isDialogDisplayed);
}
