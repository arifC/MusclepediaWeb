package models;

import javax.persistence.*;

import com.avaje.ebean.Ebean;
import play.data.validation.*;

import javax.persistence.Entity;
import java.util.*;

@Entity
public class Plan {
    @Id
    public UUID plan_id;
    @Constraints.Required
    private String type;

    @ManyToMany
    private List<Exercise> uebungsliste;

    public Plan(String type){
        this.plan_id = UUID.randomUUID();
        this.type = type;
        this.uebungsliste = new ArrayList<Exercise>();

    }

    public void addUebung(Exercise u){
        uebungsliste.add(u);
        Ebean.save(this);
    }
    public void deleteUebung(Exercise u){

        uebungsliste.remove(u);
        Ebean.save(this);
    }
    public List<Exercise> showPlan(){
        return uebungsliste;
    }

    public String getType() {
        return type;
    }
}
