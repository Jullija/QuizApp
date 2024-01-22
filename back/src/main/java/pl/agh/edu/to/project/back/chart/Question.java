package pl.agh.edu.to.project.back.chart;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Question {
    @Id
    @GeneratedValue
    private int id;
    private String title;
    private float successRate;
    @ElementCollection
    private Set<String> answers;
    @Transient
    private List<Answer> answerList = new ArrayList<>();

    public Question(String title) {
        this.title = title;
    }

    public Question() {

    }

    public Set<String> getAnswers() {
        return answers;
    }

    public String getTitle() {
        return title;
    }

    public void setAnswers(Set<String> answers) {
        this.answers = answers;
    }

    public void addAnswerForStats(Answer answer) {
        this.answerList.add(answer);
    }

    public int getId() {
        return id;
    }

    public float getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(float successRate) {
        this.successRate = successRate;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }
}
