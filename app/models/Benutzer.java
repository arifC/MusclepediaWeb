package models;

import org.springframework.context.annotation.Conditional;
import play.data.validation.*;

import javax.persistence.*;
import javax.validation.Constraint;

import java.util.UUID;


@Entity
public class
        Benutzer{
    @Id
    private UUID benutzer_id;
    @ManyToMany
    public Uebung meineUebung;
    @Constraints.Required
    private String name;
    @Constraints.Required
    private String email;
    @Constraints.Required
    private String passwort;
    @ManyToOne
    private Studio studio;
    @Constraints.Required
    private String test;
    @OneToMany
    private Gewicht gewicht;
    public void addGewicht(){

    }
    @OneToOne
    private Trainingsplan myPlan;

    /*public void addToPlan(Uebung u){
        myPlan.addUebung(u);
    }
    public void deleteFromPlan(Uebung u){
        myPlan.deleteUebung(u);
    }*/
    public String showPlan(){
        //wie sollen wir die hier ausgeben??
        return myPlan.getTyp();
    }
    public String getEmail(){
        return email;
    }

    public Benutzer(String name,String mail,String passwort){
        this.benutzer_id = UUID.randomUUID();
        this.name = name;
        this.email = mail;
        this.passwort = passwort;
        this.myPlan = new Trainingsplan("testPLan");
    }

    public String getPasswort() {
        return passwort;
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

    public Trainingsplan getMyPlan() {
        return myPlan;
    }

    public String getName() {
        return name;
    }
}
