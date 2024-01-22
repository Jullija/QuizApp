package pl.edu.agh.to2.example.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Choice {
    private SimpleObjectProperty<Chest> chest;
    private SimpleIntegerProperty number;

    public Choice(Integer number, Chest chest) {
        this.chest = new SimpleObjectProperty<>(chest);
        this.number = new SimpleIntegerProperty(number);
    }

    public Chest getChest() {
        return chest.get();
    }

    public SimpleObjectProperty<Chest> chestProperty() {
        return chest;
    }


    public Integer getNumber() {
        return number.get();
    }

    public SimpleIntegerProperty numberProperty() {
        return number;
    }

    @Override
    public String toString(){
        return chest.get().getId() + " " + getNumber();
    }
}
