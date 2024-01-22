package pl.edu.agh.to2.example.model.Chart;

public class Answer {
    private final String titleShortened;
    private final String title;
    private final float successRate;
    private final TitleConverter titleConverter = new TitleConverter();

    public Answer(String title, float successRate) {
        this.title = title;
        this.titleShortened = titleConverter.getTitleWithShortenedLength(title);
        this.successRate = successRate;
    }

    public String getTitleShortened() {
        return titleShortened;
    }

    public float getSuccessRate(){
        return successRate;
    }
}
