package models;

import javax.persistence.*;

import com.avaje.ebean.Ebean;
import play.data.validation.*;

import javax.persistence.Entity;
import java.util.*;

@Entity
public class Plan {
    /**
     *
     *Eindeutige ID
     */
    @Id
    public UUID plan_id;
    /**
     *
     *Plantyp (Schwer,Leicht, PersönlicherPlan)
     */
    @Constraints.Required
    private String type;
    /**
     *
     *Verbindung zu den beninhalteten Übungen
     */
    @ManyToMany
    private List<Exercise> uebungsliste;
    /**
     *Constructor für den Plan erwartet den Typ als Übergabeparameter
     */
    public Plan(String type){
        this.plan_id = UUID.randomUUID();
        this.type = type;
        this.uebungsliste = new ArrayList<Exercise>();

    }

    /**
     * Fügt die übergebene Übung den Plan hinzu
     */
    public void addUebung(Exercise u){
        uebungsliste.add(u);
        Ebean.save(this);
    }

    /**
     * Löscht die übergebene Übung aus dem Plan
     */
    public void deleteUebung(Exercise u){

        uebungsliste.remove(u);
        Ebean.save(this);
    }

    /**
     *@return gibt den ganzen Plan zurück
     */
    public List<Exercise> showPlan(){
        return uebungsliste;
    }
    /**
     *@return gibt den Typ des Plans zurück
     */
    public String getType() {
        return type;
    }
}
