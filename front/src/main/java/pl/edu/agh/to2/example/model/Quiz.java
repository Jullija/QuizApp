package pl.edu.agh.to2.example.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;


public class Quiz {
    private final int id;
    private final StringProperty name;
    private final ObservableList<Form> forms;
    private final SimpleObjectProperty<Strategy> strategy;

    public Quiz(int id, String name, Strategy strategy, ObservableList<Form> forms) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.forms = forms;
        this.strategy = new SimpleObjectProperty<>(strategy);
    }

    public Quiz(int id, String name, Strategy strategy) {
        this(id, name, strategy, FXCollections.observableArrayList());
    }

    public final String getName() {
        return name.getValue();
    }

    public final StringProperty getNameProperty() {
        return name;
    }

    public final ObservableList<Form> getFormsProperty() {
        return forms;
    }

    public void setForms(List<Form> forms) {
        this.forms.setAll(forms);
    }

    public void setStrategy(Strategy strategy) {
        this.strategy.setValue(strategy);
    }

    public int getId() {
        return id;
    }

    public Strategy getStrategy() {
        return strategy.getValue();
    }

    public SimpleObjectProperty<Strategy> getStrategyProperty(){
        return strategy;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "name=" + getName() +
                ", strategy=" + getStrategy() +
                ", forms=" + forms +
                '}';
    }
}
