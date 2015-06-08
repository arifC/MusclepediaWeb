package models;

import com.avaje.ebean.Ebean;
import play.data.validation.*;

import javax.persistence.*;
import javax.swing.*;

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
    @OneToMany
    private Weight weight;
    @OneToOne
    private Plan myPlan;

    public void addToPlan(Exercise u){
        myPlan.addUebung(u);
    }
    public void deleteFromPlan(Exercise u){
        myPlan.deleteUebung(u);
    }
    public List<Exercise> showPlan(){
        return myPlan.showPlan();
    }
    public String getEmail(){
        return email;
    }

    public User(String name, String mail, String passwort){
        this.benutzer_id = UUID.randomUUID();
        this.name = name;
        this.email = mail;
        this.password = passwort;
        this.myPlan = new Plan("testPlan");
        Ebean.save(this.myPlan);
    }

    public String getPassword() {
        return password;
    }

    public void changePasswort(String oldPw, String newPw, String newRep){//beispielmethode
        if(oldPw== password){
            if(newPw==newRep){
                this.password =newPw;
                Ebean.save(this);
            }else{
                System.out.println("Wiederholung stimmt nicht");
            }
        }
        System.out.print("BIN HIER");
    }

    public Plan getMyPlan() {
        return myPlan;
    }

    public String getName() {
        return name;
    }

    public void rateStudio(Studio studio,Rating rating){
        studio.addBewertung(rating);
    }
}