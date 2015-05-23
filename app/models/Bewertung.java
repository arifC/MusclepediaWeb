package models;

import org.springframework.context.annotation.Conditional;
import play.data.validation.*;

import javax.persistence.*;
import javax.validation.Constraint;

import java.util.UUID;

@Entity
public class Bewertung {
    @Id
    private UUID Bewertung_ID;
    @Constraints.Required
    private double ausstattung;
    @Constraints.Required
    private double service;
    @Constraints.Required
    private double preis;
    @Constraints.Required
    private double lage;

    public Bewertung(double ausstattung, double service, double preis, double lage){
        this.ausstattung = ausstattung;
        this.service = service;
        this.preis = preis;
        this.lage = lage;
    }

    public double getAusstattung() {
        return ausstattung;
    }

    public void setAusstattung(double ausstattung) {
        this.ausstattung = ausstattung;
    }

    public double getService() {
        return service;
    }

    public void setService(double service) {
        this.service = service;
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public double getLage() {
        return lage;
    }

    public void setLage(double lage) {
        this.lage = lage;
    }

}
