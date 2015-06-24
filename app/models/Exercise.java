package models;


import javax.persistence.*;

import com.avaje.ebean.Ebean;
import play.data.validation.*;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

@Entity
public class Exercise {
    /**
     * Eindeutige ID
     *
     */
    @Id
    public int exercise_id;
    /**
     * Name der Übung
     *
     */
    @Constraints.Required
    private String name;
    /**
     * Verbindung zum Plan table
     *
     */

    @ManyToMany(mappedBy = "uebungsliste")
    private List<Plan> uebungen;
    /**
     * Verbindung zu Muscle table
     *
     */
    private List<Muscle> muskeln = new ArrayList<Muscle>();
    @Constraints.Required
    /**
     * Beschreibung der Übung
     *
     */
    private String beschreibung;
    @Constraints.Required
    private String schwierigkeit;
    /**
     * Constructor mit Name als Übergabeparameter
     *
     */
    public Exercise(String name){
        this.name=name;
    }
    /**
     * Constructor mit Name, Muskel, Beschreibung und Schwierigkeit
     *
     */

    public Exercise(String name, Muscle muskel, String beschreibung, String schwierigkeit){
        this.name=name;
        this.muskeln.add(muskel);
        this.beschreibung = beschreibung;
        this.schwierigkeit=schwierigkeit;
        this.uebungen = new ArrayList<Plan>();
    }
    /**
     *
     *@return gibt die Beschreibung zurück
     */
    public String getBeschreibung() {
        return beschreibung;
    }

    /**
     *
     *@return gibt den Namen zurück
     */
    public String getName() {
        return name;
    }



}
