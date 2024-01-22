package pl.edu.agh.to2.example.model;

import javafx.beans.property.*;

public class Form {
    private final int id;
    private final StringProperty nick;
    private final FloatProperty points;
    private final IntegerProperty time;
    private final StringProperty endTime;
    private ObjectProperty<Award> award;

    public Form(int id, String nick, Float points, Integer time, String endTime, Award award) {
        this.id = id;
        this.time = new SimpleIntegerProperty(time);
        this.nick = new SimpleStringProperty(nick);
        this.award = new SimpleObjectProperty<>(award);
        this.points = new SimpleFloatProperty(points);
        this.endTime = new SimpleStringProperty(endTime);
    }

    public final Integer getTime() {
        return time.get();
    }

    public final void setTime(Integer time) {
        this.time.set(time);
    }

    public final IntegerProperty getTimeProperty() {
        return time;
    }

    public final String getNick() {
        return nick.get();
    }

    public final void setNick(String nick) {
        this.nick.set(nick);
    }

    public final StringProperty getNickProperty() {
        return nick;
    }

    public final Award getAward() {
        return award.get();
    }

    public final void setAward(Award award) {
        this.award.set(award);
    }

    public final ObjectProperty<Award> getAwardProperty() {
        return award;
    }

    public final Float getPoints() {
        return points.get();
    }

    public final void setPoints(Float points) {
        this.points.set(points);
    }

    public final FloatProperty getPointsProperty() {
        return points;
    }

    @Override
    public String toString() {
        return nick.getValue();
    }

    public StringProperty getEndTimeProperty() {
        return endTime;
    }

    public String getEndTime() {
        return endTime.getValue();
    }

    public final void setEndTime(String endTime) {
        this.endTime.set(endTime);
    }

    public int getId() {
        return id;
    }
}