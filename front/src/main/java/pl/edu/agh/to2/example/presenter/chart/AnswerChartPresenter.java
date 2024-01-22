package pl.edu.agh.to2.example.presenter.chart;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import pl.edu.agh.to2.example.model.Chart.Answer;
import pl.edu.agh.to2.example.model.Chart.Question;


public class AnswerChartPresenter {
    final static int AXIS_Y_LOWER_BOUND = 0;
    final static int AXIS_Y_UPPER_BOUND = 100;
    final static int AXIS_Y_TICKS_UNITS = 5;

    @FXML
    private BarChart<String, Number> answersBarChart;
    private Question question;

    @FXML
    public void initialize() {}

    public void setQuestion(Question question) {
        this.question = question;
        generateChart();
    }

    private void generateChart(){
        setAnswersBarChartData();

        configureYAxis(answersBarChart);

        setChartTitle(answersBarChart, question.getTitle());
    }

    private void setAnswersBarChartData() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for (Answer answer : question.getAnswers()) {
            XYChart.Data<String, Number> dataPoint = new XYChart.Data<>(answer.getTitleShortened(), answer.getSuccessRate());
            series.getData().add(dataPoint);
        }

        answersBarChart.getData().setAll(series);
    }

    private void configureYAxis(BarChart<String, Number> barChart){
        NumberAxis yAxis = (NumberAxis) barChart.getYAxis();

        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(AXIS_Y_LOWER_BOUND);
        yAxis.setUpperBound(AXIS_Y_UPPER_BOUND);

        yAxis.setTickUnit(AXIS_Y_TICKS_UNITS);
    }

    private void setChartTitle(BarChart<String, Number> barChart, String title){
        barChart.titleProperty().setValue(title);
    }
}
