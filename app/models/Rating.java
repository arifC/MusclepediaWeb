package models;

import play.data.validation.*;

import javax.persistence.*;

import java.util.UUID;

@Entity
public class Rating {
    /**
     * Eindeutige ID
     *
     */
    @Id
    private UUID rating_id;
    /**
     * Bewertung für die Einrichtung
     *
     */
    @Constraints.Required
    private double facilities;
    /**
     * Bewertung für den Service
     *
     */
    @Constraints.Required
    private double service;
    /**
     *  Bewertung für den Preis
     *
     */
    @Constraints.Required
    private double price;
    /**
     * Bewertung für die Lage
     *
     */
    @Constraints.Required
    private double location;
    /**
     * Verbindung zu den Bewerteten Studio
     *
     */
    @OneToOne
    private Studio studio;
    /**
     * Verbindung zum User welcher die Bewrtung abgegeben hat
     *
     */
    @ManyToOne
    private User user;
    @Constraints.Required
    /**
     * Gesamtbewertung durch den User
     *
     */
    private int value;
    /**
     * Constructor welcher alle Unterpunkte benötigt wird derzeit nicht Aufgerufen
     *
     */
    public Rating(double facilities, double service, double price, double location){
        this.facilities = facilities;
        this.service = service;
        this.price = price;
        this.location = location;
    }
    /**
     * Constructor welcher das Studio, den User und den Wert benötigt
     *
     */
    public Rating(Studio studio, User user, int value){
        this.studio = studio;
        this.user = user;
        this.value=value;
    }
    /**
     *
     *@return gibt die Bewertung zurück
     */
    public int getValue() {
        return value;
    }
    /**
     *
     *@return gibt die Bewertung für die Einrichtung zurück
     */
    public double getFacilities() {
        return facilities;
    }
    /**
     *
     * setter Methode für die Einrichtungsbewertung
     */
    public void setFacilities(double facilities) {
        this.facilities = facilities;
    }
    /**
     *
     *@return gibt die Servicebewertung zurück
     */
    public double getService() {
        return service;
    }
    /**
     *
     * Setter Methode für die Serbicebewertung
     */
    public void setService(double service) {
        this.service = service;
    }
    /**
     *
     *@return gibt die Preisbewertung zurück
     */
    public double getPrice() {
        return price;
    }
    /**
     *
     *Setter Methode für die Presibewertung
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     *
     *@return gibt die Lagebewertung zurück
     */
    public double getLocation() {
        return location;
    }
    /**
     *
     *Setter Methode für die Lagebewertung
     */
    public void setLocation(double location) {
        this.location = location;
    }
    /**
     *
     *@return gibt die eindeutige ID zurück
     */
    public UUID getRating_id() {
        return rating_id;
    }
    /**
     *
     *@return gibt das bewertete Studio zurück
     */
    public Studio getStudio() {
        return studio;
    }
    /**
     *
     *@return gibt den User zurück
     */
    public User getUser() {
        return user;
    }
}
