package pl.edu.agh.to2.example.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

public class Award {
    private final int id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty description;
    private final ObservableList<Chest> chests;
    private final SimpleStringProperty chestsStringProperty;

    public Award(int id, String name, String desc, ObservableList<Chest> chests) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(desc);
        this.chests = chests;
        this.chestsStringProperty = new SimpleStringProperty(getChestsStringFromList(chests));
    }

    public Award(String name, String desc, ObservableList<Chest> chests) {
        this(-1, name, desc, chests);
    }

    private String getChestsStringFromList(ObservableList<Chest> chests) {
        int n = chests.size();
        StringBuilder chestsString = new StringBuilder();

        for (int i = 0; i < n; i++) {
            Chest chest = chests.get(i);
            chestsString.append(chest.getName());
            if (i != n - 1) {
                chestsString.append(", ");
            }
        }

        return chestsString.toString();
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty getNameProperty() {
        return name;
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty getDescriptionProperty() {
        return description;
    }

    public ObservableList<Chest> getChestsProperty() {
        return chests;
    }

    public ObservableValue<String> getChestsStringsProperty() {
        return chestsStringProperty;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return getName();
    }
}
