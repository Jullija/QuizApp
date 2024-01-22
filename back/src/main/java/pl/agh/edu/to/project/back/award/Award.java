package pl.agh.edu.to.project.back.award;

import pl.agh.edu.to.project.back.chest.Chest;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Award {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    private String name;

    private String description;

    @ManyToMany
    private List<Chest> chests;


    public Award(){}

    public Award(String name, String description, List<Chest> chests){
        this.name = name;
        this.description = description;
        this.chests =  chests;
    }

    public int getId() {
        return id;
    }

    public void setId(int awardID) {
        this.id = awardID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Chest> getChest() {
        return chests;
    }

    public void setChest(List<Chest> chests) {
        this.chests = chests;
    }

    @Override
    public String toString(){
        return name;
    }

}
