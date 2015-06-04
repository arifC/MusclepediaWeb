package models;


import javax.persistence.*;
import play.data.validation.*;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Exercise {
    @Id
    public int exercise_id;
    @Constraints.Required
    private String name;

    private List<Muscle> muskeln = new ArrayList<Muscle>();
    @Constraints.Required
    private String beschreibung;
    @Constraints.Required
    private String schwierigkeit;

    public Exercise(String name, Muscle muskel, String beschreibung, String schwierigkeit){
        this.name=name;
        this.muskeln.add(muskel);
        this.beschreibung = beschreibung;
        this.schwierigkeit=schwierigkeit;
    }

    public Exercise(String name){
        this.name=name;
    }


    public String getName() {
        return name;
    }
}
