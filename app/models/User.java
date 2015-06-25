package models;

import com.avaje.ebean.Ebean;
import play.data.validation.*;

import javax.persistence.*;
import javax.swing.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


@Entity
public class User {
    @Id
    private UUID benutzer_id;
    @Constraints.Required
    private String name;
    @Constraints.Required
    private String email;
    @Constraints.Required
    private String password;
    @ManyToOne
    private Studio studio;
    /**
     * Jeder User besitzt eine Liste von Objekten der Klasse Weight. Dies dient der Sicherung von Gewichten für einen
     * jeweiligen Benutzer. Ein Benutzer kann hierbei n Gewichte zugeordnet haben.
     */
    @OneToMany(cascade=CascadeType.ALL)
    private List<Weight> weights;
    /**
     * Jeder User hat einen Trainingsplan gespeichert, der seinen personalisierten Trainingsplan darstellt. Diesem Plan
     * kann er Übungen hinzufügen oder sich alle Übungen des Plans ausgeben lassen.
     */
    @OneToOne
    private Plan myPlan;

    /**
     * Fügt die übergebene Übung dem persönlichen Plan des Users hinzu.
     * @param u
     */
    public void addToPlan(Exercise u){
        myPlan.addUebung(u);
    }

    /**
     * Löscht die übergebene Übung aus dem persönlichen Plan des Users.
     * @param u
     */
    public void deleteFromPlan(Exercise u){
        myPlan.deleteUebung(u);
    }

    /**
     * @return Eine Liste von Übungen wird zurückgegeben.
     */
    public List<Exercise> showPlan(){
        return myPlan.showPlan();
    }
    public String getEmail(){
        return email;
    }

    /**
     * Fügt dem User ein Gewicht hinzu. Hierzu wird der Methode der Parameter w als Gewicht als Double übergeben und ein
     * User, dem dieses Gewicht zugeordnet werden soll. Die Methode erstellt dann ein Weight und fügt es der Liste
     * von Gewichten hinzu.
     * @param w
     * @param user
     */
    public void addWeight(double w, User user){
        Weight weight = new Weight(w, user);
        this.weights.add(weight);

    }

    /**
     * @return Gibt die Liste von Gewichten zurück.
     */
    public List<Weight> showWeights() {
        return weights;
    }

    /**
     * Der Konstruktor für das Erstellen eines Users verlangt einen eindeutigen Benutzernamen, eine Mail und ein Passwort.
     * Diese Parameter werden der Methode beispielsweise beim Registrieren übergeben.
     * @param name
     * @param mail
     * @param password
     */
    public User(String name, String mail, String password){
        this.benutzer_id = UUID.randomUUID();
        this.weights=new ArrayList<Weight>();
        this.name = name;
        this.email = mail;
        this.password = password;
        this.myPlan = new Plan("testPlan");
        Ebean.save(this.myPlan);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password){
        this.password = password;
        Ebean.save(this);
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
        Ebean.save(this);
    }

    public Studio getStudio() {
        return studio;
    }

    public String getName() {
        return name;
    }

    public void rateStudio(Studio studio,Rating rating){
        studio.addBewertung(rating);
    }
}
