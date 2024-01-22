package pl.edu.agh.to2.example.model.Chart;

import java.util.List;

public class Question {
    private final String title;
    private final String titleShortened;
    private final float successRate;
    private final List<Answer> answers;
    private final TitleConverter titleConverter = new TitleConverter();

    public Question(String title, float successRate, List<Answer> answers) {
        this.title = title;
        this.titleShortened = titleConverter.getTitleWithShortenedLength(title);
        this.successRate = successRate;
        this.answers = answers;
    }

    public String getTitleShortened() {
        return titleShortened;
    }

    public float getSuccessRate(){ return successRate; }

    public List<Answer> getAnswers() {
        return answers;
    }

    public String getTitle() {
        return title;
    }
}
