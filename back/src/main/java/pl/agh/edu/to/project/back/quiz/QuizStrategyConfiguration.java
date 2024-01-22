package pl.agh.edu.to.project.back.quiz;

import pl.agh.edu.to.project.back.form.Form;

import java.util.Comparator;

public class QuizStrategyConfiguration {

    private Quiz quiz;

    public QuizStrategyConfiguration(Quiz quiz) {
        this.quiz = quiz;
    }

    private void sortQuiz() {
        quiz.getForms().sort(Comparator.comparing(Form::getPoints, Comparator.reverseOrder())
                .thenComparing(Form::getEndTime));
    }

    public void SortingAndStrategy() {
        sortQuiz();
    }

    public Quiz getQuiz() {
        return this.quiz;
    }


}
