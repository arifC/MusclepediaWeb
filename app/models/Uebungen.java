package models;


import play.data.format.*;
import play.db.ebean.*;
import javax.persistence.*;
import play.data.validation.*;
import javax.persistence.Entity;
@Entity
public class Uebungen {
    @Id
    public int uebung_id;
    @Constraints.Required
    private String name;
    @Constraints.Required
    private Muskel mu;
    @Constraints.Required
    private String beschreibung;
    @Constraints.Required
    private String schwierigkeit;

    public Uebungen(String name, Muskel muskel,String beschreibung,String schwierigkeit){
        this.name=name;
        this.mu= muskel;
        this.beschreibung = beschreibung;
        this.schwierigkeit=schwierigkeit;
    }


}
