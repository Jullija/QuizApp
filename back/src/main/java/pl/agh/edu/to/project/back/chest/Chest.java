package pl.agh.edu.to.project.back.chest;

import pl.agh.edu.to.project.back.award.Award;

import javax.persistence.*;
import java.util.List;

@Entity
public class Chest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


    private String name;

    public Chest() {
    }

    public Chest(String name) {
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
