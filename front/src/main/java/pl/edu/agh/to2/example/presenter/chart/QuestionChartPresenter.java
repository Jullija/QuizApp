package pl.edu.agh.to2.example.presenter.chart;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.edu.agh.to2.example.controller.MainScreenController;
import pl.edu.agh.to2.example.controller.QuizAppController;
import pl.edu.agh.to2.example.model.Chart.Question;
import pl.edu.agh.to2.example.model.Quiz;
import pl.edu.agh.to2.example.presenter.AbstractPresenter;

import java.util.List;


public class QuestionChartPresenter extends AbstractPresenter {
    final static int AXIS_Y_LOWER_BOUND = 0;
    final static int AXIS_Y_UPPER_BOUND = 100;
    final static int AXIS_Y_TICKS_UNITS = 5;

    static final String ANSWER_CHART_TITLE = "Answer chart";
    static final String PATH_VIEW_ANSWER_CHART_FXML = "/view/AnswerChart.fxml";

    @FXML
    private BarChart<String, Number> questionsBarChart;
    private Quiz quiz;
    private List<Question> questions;

    @FXML
    public void initialize() {}

    @Override
    public void configure(Stage dialogStage, MainScreenController mainScreenController, QuizAppController quizAppController, SimpleBooleanProperty isDialogDisplayed) {
        super.configure(dialogStage, mainScreenController, quizAppController, isDialogDisplayed);
        setDataFromController();
    }

    private void setDataFromController() {
        quiz = mainScreenController.getCurrentQuiz();
        questions = getQuestionsFromDatabase();
        generateChart();
    }

    private void generateChart(){
        setQuestionsBarChartData();

        configureYAxis();

        setChartTitle( "[" + quiz.getId() + "] " + quiz.getName());
    }

    private void setQuestionsBarChartData(){
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for(Question question : questions){
            addBar(series, question.getTitleShortened(), question.getSuccessRate(), question);
        }

        questionsBarChart.getData().setAll(series);
    }

    private void addBar(XYChart.Series<String, Number> series, String title, float successRate, Question question) {
        XYChart.Data<String, Number> data = new XYChart.Data<>(title, successRate);

        data.nodeProperty().addListener((obs, oldNode, newNode) -> {
            if (newNode != null) {
                newNode.setOnMouseClicked(event -> {
                    showAnswerChart(ANSWER_CHART_TITLE, PATH_VIEW_ANSWER_CHART_FXML, question);
                });
            }
        });

        series.getData().add(data);
    }

    private void configureYAxis(){
        NumberAxis yAxis = (NumberAxis) questionsBarChart.getYAxis();

        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(AXIS_Y_LOWER_BOUND);
        yAxis.setUpperBound(AXIS_Y_UPPER_BOUND);

        yAxis.setTickUnit(AXIS_Y_TICKS_UNITS);
    }


    private ObservableList<Question> getQuestionsFromDatabase() {
        try {
            return FXCollections.observableArrayList(retrofitClient.getQuizClient().getQuestions(quiz.getId()));
        } catch (Exception e) {
            System.err.println("Error while loading chests: " + e.getMessage());
        }
        return FXCollections.observableArrayList();
    }

    private void setChartTitle(String title){
        questionsBarChart.titleProperty().setValue(title);
    }

    public void showAnswerChart(String dialogTitle, String path, Question question) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(QuizAppController.class.getResource(path));
            BorderPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle(dialogTitle);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AnswerChartPresenter presenter = loader.getController();
            presenter.setQuestion(question);

            dialogStage.show();

        } catch (Exception e) {
            showAlert(e);
        }
    }
}
