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
    /**@param Studio, User, Integer
     * Constructor welcher das Studio, den User und den Wert benötigt
     *
     */
    public Rating(Studio studio, User user, int value){
        this.rating_id = UUID.randomUUID();
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
