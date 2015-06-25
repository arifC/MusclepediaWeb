package models;

import com.avaje.ebean.Ebean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.swing.*;

import java.util.UUID;

@Entity
public class Studio {
    @Id
    private UUID studio_id;
    private int plz;
    private String ort;
    private String strasse;
    private String name;

    /**
     * Jedes Studio enthält eine Liste verschiedener Rating-Objekte, die jeweils von verschiedenen Usern erstellt wurden
     */
    @OneToMany(cascade=CascadeType.ALL)
    private List<Rating> ratings;

    private double totalRating = 0;
    private double totalFacilities = 0;
    private double totalLocation = 0;
    private double totalPrice = 0;
    private double totalService = 0;

    /**
     * Konstruktor für das Erstellen eines Studios mit eindeutiger ID in der Datenbank. IDs werden generiert.
     * @param name
     * @param strasse
     * @param plz
     * @param ort
     */
    public Studio(String name, String strasse, int plz, String ort){
        ratings = new ArrayList<Rating>();
        this.name = name;
        this.strasse = strasse;
        this.plz = plz;
        this.ort = ort;
        this.studio_id = UUID.randomUUID();
    }

    /**
     * Berechnung der durchschnittlichen Bewertung eines jeweiligen Studios. Es wird durch die List der Ratings iteriert
     * und anschließend das arithmetische Mittel gebildet. Das Ergebnis wird in der Instanzvariable totalRating gespeichert.
     */
    public void calcAverage2(){
        double summe = 0;
        int counter = 0;
        List<Rating> ratings = Ebean.find(Rating.class).findList();
        for(Rating r : ratings){
            if(r.getStudio().getName().equals(this.name)){
                counter ++;
                summe = summe + r.getValue();
            }
        }
        totalRating = summe/counter;
    }

    /**
     * Ein übergebenes Rating wird der Liste von Ratings hinzugefügt und in der Datenbank gespeichert.
     * Nach dem Hinzufügen in der List wird mit der neuen List ein neuer Durchschnitt berechnet.
     * @param bw
     */
    public void addBewertung(Rating bw){
        ratings.add(bw);
        calcAverage2();
        Ebean.save(this);
    }

    public String getOrt() {
        return ort;
    }

    public int getPlz() {
        return plz;
    }

    public String getStrasse() {
        return strasse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotalRating() {
        return totalRating;
    }
}
