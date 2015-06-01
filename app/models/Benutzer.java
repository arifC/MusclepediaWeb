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
    private Trainingsplan myPlan= new Trainingsplan("Custom Plan");

    public void addToPlan(Uebung u){
        myPlan.addUebung(u);
    }
    public void deleteFromPlan(Uebung u){
        myPlan.deleteUebung(u);
    }
    public void showPlan(){
        //wie sollen wir die hier ausgeben??
        myPlan.showPlan();
    }

    public Benutzer(String name,String mail,String passwort){
        this.benutzer_id = UUID.randomUUID();
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

    public String getName() {
        return name;
    }
}
