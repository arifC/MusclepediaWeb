package models;


import play.data.format.*;
import play.db.ebean.*;
import javax.persistence.*;
import play.data.validation.*;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Entity
public class Uebung {
    @Id
    public int uebung_id;
    @Constraints.Required
    private String name;

    private List<Muskel> muskeln = new ArrayList<Muskel>();
    @Constraints.Required
    private String beschreibung;
    @Constraints.Required
    private String schwierigkeit;

    public Uebung(String name, Muskel muskel, String beschreibung, String schwierigkeit){
        this.name=name;
        this.muskeln.add(muskel);
        this.beschreibung = beschreibung;
        this.schwierigkeit=schwierigkeit;
    }


}
