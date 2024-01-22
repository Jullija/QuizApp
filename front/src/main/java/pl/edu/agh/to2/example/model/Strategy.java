package pl.edu.agh.to2.example.model;

import javafx.beans.property.SimpleStringProperty;

public class Strategy {
    private final int id;
    private final SimpleStringProperty name;

    public Strategy(int id, String name) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
    }

    public String getName() {
        return name.getValue();
    }

    public SimpleStringProperty getNameProperty() {
        return name;
    }

    @Override
    public String toString() {
        return this.name.getValue();
    }

    public int getId() {
        return id;
    }
}
