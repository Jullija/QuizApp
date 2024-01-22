package pl.agh.edu.to.project.back.form;

import pl.agh.edu.to.project.back.award.Award;
import pl.agh.edu.to.project.back.quiz.Quiz;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Form {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nick;
    private float points;
    private int timestamp;
    private String endTime;
    @OneToOne
    private Award award;
    @ElementCollection
    private List<String> awardPriority;
    @ElementCollection
    private List<String> answers;

    public Form(){}

    public Form(String nick, float points, int timestamp, String endTime, Award award, List<String> awardPriority, List<String> formAnswers) {
        this.nick = nick;
        this.points = points;
        this.timestamp = timestamp;
        this.endTime = endTime;
        this.award = award;
        this.awardPriority = awardPriority;
        this.answers = formAnswers;
    }

    public int getId() {
        return id;
    }

    public void setId(int formID) {
        this.id = formID;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public float getPoints() {
        return points;
    }

    public void setPoints(float points) {
        this.points = points;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public Award getAward() {
        return award;
    }

    public void setAward(Award award) {
        this.award = award;
    }

    public List<String> getAwardPriority() {
        return awardPriority;
    }

    public void setAwardPriority(List<String> awardPriority) {
        this.awardPriority = awardPriority;
    }

    @Override
    public String toString() {
        return "Form{" +
                "formID=" + id +
                ", nick='" + nick + '\'' +
                ", points=" + points +
                ", timestamp=" + timestamp +
                ", endTime='" + endTime + '\'' +
                ", award=" + award +
                ", awardPriority='" + awardPriority + '\'' +
                '}';
    }


}
