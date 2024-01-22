package pl.edu.agh.to2.example.model;

import javafx.beans.property.SimpleStringProperty;

public class Chest {
    private final int id;
    private final SimpleStringProperty name;

    public Chest(int id, String name) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
    }

    public Chest(String name) {
        this(-1, name);
    }

    public String getName() {
        return name.getValue();
    }

    public SimpleStringProperty getNameProperty() {
        return name;
    }

    @Override
    public String toString() {
        return name.getValue();
    }

    public int getId() {
        return id;
    }
}
