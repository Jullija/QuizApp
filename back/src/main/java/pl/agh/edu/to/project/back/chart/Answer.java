package pl.agh.edu.to.project.back.chart;

public class Answer {
    private String title;
    private float successRate;

    public Answer(String title, float successRate) {
        this.title = title;
        this.successRate = successRate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(float successRate) {
        this.successRate = successRate;
    }
}
