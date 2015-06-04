package models;

import javax.persistence.*;
import play.data.validation.*;
import play.db.ebean.Model;

import javax.persistence.Entity;
import java.util.Collection;
import java.util.TreeMap;
import java.util.SortedMap;
import java.util.UUID;

@Entity
public class Trainingsplan {
    @Id
    public UUID plan_id;
    @Constraints.Required
    private String typ;

    /*@ManyToMany(mappedBy="Trainingsplan")
    private SortedMap<Integer, Uebung> uebungsliste = new TreeMap<Integer, Uebung>();*/
    public Trainingsplan(String typ){
        this.plan_id = UUID.randomUUID();
        this.typ=typ;
    }

    /*public void addUebung(Uebung u){
        uebungsliste.put(u.uebung_id,u);
    }
    public void deleteUebung(Uebung u){
        uebungsliste.remove(u.uebung_id);
    }
    public Collection<Uebung> showPlan(){
        return uebungsliste.values();
    }*/

    public String getTyp() {
        return typ;
    }
}
