package pl.edu.agh.to2.example.model;

import javafx.beans.property.SimpleStringProperty;

public class StrategyType {
    private SimpleStringProperty name;

    public StrategyType(String name){
        this.name = new SimpleStringProperty(name);
    }

    public String getName() {
        return name.getValue();
    }

    public SimpleStringProperty getNameProperty() {
        return name;
    }

    @Override
    public String toString(){
        return getName();
    }
}
