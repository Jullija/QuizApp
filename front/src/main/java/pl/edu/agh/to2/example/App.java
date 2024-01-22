package pl.edu.agh.to2.example;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.edu.agh.to2.example.controller.QuizAppController;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        primaryStage.setTitle("Quiz App");

        // Some time needed for localhost to start.
        Thread.sleep(3000);

        QuizAppController appController = new QuizAppController(primaryStage);

        appController.initRootLayout();
    }
}
