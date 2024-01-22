package pl.agh.edu.to.project.back.quiz;

import org.springframework.stereotype.Component;
import pl.agh.edu.to.project.back.award.Award;
import pl.agh.edu.to.project.back.chart.Question;
import pl.agh.edu.to.project.back.form.Form;
import pl.agh.edu.to.project.back.strategy.Strategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Component
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Strategy strategyID;

    @OneToMany
    private List<Form> forms;
    private String quizName;
    @OneToMany
    private List<Question> questions;

    public Quiz() {
    }

    public Quiz(String quizName) {
        this.quizName = quizName;
        this.forms = new ArrayList<>();
    }

    public Quiz(String quizName, Strategy strategyID) {
        this(quizName);
        this.strategyID = strategyID;
    }

    public void addForm(Form form) {
        forms.add(form);
    }

    public void removeForm(Form form) {
        forms.remove(form);
    }

    public int getId() {
        return id;
    }

    public void setId(int quizID) {
        this.id = quizID;
    }

    public Strategy getStrategyID() {
        return strategyID;
    }

    public void setStrategyID(Strategy strategyID) {
        this.strategyID = strategyID;
    }

    public List<Form> getForms() {
        return forms;
    }

    public void setForms(List<Form> forms) {
        this.forms = forms;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getQuizName() {
        return quizName;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public List<Award> getAwards() {
        List<Award> awardsList = new ArrayList<>();

        for (Form form : this.getForms()) {
            awardsList.add(form.getAward());
        }

        return awardsList;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "quizID=" + id +
                ", strategyID=" + strategyID +
                ", forms=" + forms.size() +
                ", quizName='" + quizName + '\'' +
                '}';
    }

}
