package models;

import play.data.format.*;
import play.db.ebean.*;
import javax.persistence.*;
import play.data.validation.*;
import javax.persistence.Entity;
import javax.persistence.Id;

import java.util.UUID;


@Entity
public class Benutzer{
    @Id
    private UUID benutzer_id;
    @Constraints.Required
    private String name;
    @Constraints.Required
    private String email;
    @Constraints.Required
    private String passwort;

    private Trainingsplan myPlan= new Trainingsplan("Custom Plan");

    public void addToPlan(Uebungen u){
        myPlan.addUebung(u);
    }
    public void deleteFromPlan(Uebungen u){
        myPlan.deleteUebung(u);
    }
    public void showPlan(){
        //wie sollen wir die hier ausgeben??
        myPlan.showPlan();
    }

    public Benutzer(UUID id, String name,String mail,String passwort){
        this.benutzer_id = id;
        this.name = name;
        this.email = mail;
        this.passwort = passwort;
    }

    public void changePasswort(String altpw, String neupw, String neuwiederholung){//beispielmethode
        if(altpw==passwort){
            if(neupw==neuwiederholung){
                this.passwort=neupw;
            }else
                    System.out.println("Wiederholung stimmt nicht");

        }else
            System.out.println("Passwort falsch");
    }
}
