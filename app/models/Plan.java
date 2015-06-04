package models;

import javax.persistence.*;
import play.data.validation.*;

import javax.persistence.Entity;
import java.util.UUID;

@Entity
public class Plan {
    @Id
    public UUID plan_id;
    @Constraints.Required
    private String type;

    /*@ManyToMany(mappedBy="Plan")
    private SortedMap<Integer, Exercise> uebungsliste = new TreeMap<Integer, Exercise>();*/
    public Plan(String type){
        this.plan_id = UUID.randomUUID();
        this.type = type;
    }

    /*public void addUebung(Exercise u){
        uebungsliste.put(u.exercise_id,u);
    }
    public void deleteUebung(Exercise u){
        uebungsliste.remove(u.exercise_id);
    }
    public Collection<Exercise> showPlan(){
        return uebungsliste.values();
    }*/

    public String getType() {
        return type;
    }
}
