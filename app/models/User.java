package models;

import play.data.validation.*;

import javax.persistence.*;

import java.util.UUID;


@Entity
public class
        User {
    @Id
    private UUID benutzer_id;
    @ManyToMany
    public Exercise meineUebung;
    @Constraints.Required
    private String name;
    @Constraints.Required
    private String email;
    @Constraints.Required
    private String password;
    @ManyToOne
    private Studio studio;
    @Constraints.Required
    private String test;
    @OneToMany
    private Weight weight;
    public void addWeight(){

    }
    @OneToOne
    private Plan myPlan;

    /*public void addToPlan(Exercise u){
        myPlan.addUebung(u);
    }
    public void deleteFromPlan(Exercise u){
        myPlan.deleteUebung(u);
    }*/
    public String showPlan(){
        //wie sollen wir die hier ausgeben??
        return myPlan.getType();
    }
    public String getEmail(){
        return email;
    }

    public User(String name, String mail, String passwort){
        this.benutzer_id = UUID.randomUUID();
        this.name = name;
        this.email = mail;
        this.password = passwort;
        this.myPlan = new Plan("testPLan");
    }

    public String getPassword() {
        return password;
    }

    public void changePasswort(String oldPw, String newPw, String newRep){//beispielmethode
        if(oldPw== password){
            if(newPw==newRep){
                this.password =newPw;
            }else{
                System.out.println("Wiederholung stimmt nicht");
            }
        }else
            System.out.println("Passwort falsch");
    }

    public Plan getMyPlan() {
        return myPlan;
    }

    public String getName() {
        return name;
    }
}
