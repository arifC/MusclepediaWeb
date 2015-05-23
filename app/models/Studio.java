package models;

import java.util.ArrayList;
import java.util.List;

import play.data.format.*;
import play.data.validation.*;
import org.springframework.context.annotation.Conditional;
import play.data.validation.*;

import javax.persistence.*;
import javax.validation.Constraint;

import java.util.UUID;

@Entity
public class Studio {
    @Id
    private int id;
    private String plz;
    private String ort;
    private String strasse;
    private String name;

    private List<Bewertung> bewertungen = new ArrayList<Bewertung>();
    private double gesamtBewertung;
    private double gesamtAusstattung = 0;
    private double gesamtLage = 0;
    private double gesamtPreis = 0;
    private double gesamtService = 0;

    public Studio(String name, String strasse, String plz, String ort){
        this.name = name;
        this.strasse = strasse;
        this.plz = plz;
        this.ort = ort;
    }

    public void berechneDurchschnitt(){
        double counter = 0;
        for(Bewertung bw : bewertungen){
            this.gesamtAusstattung += bw.getAusstattung();
            this.gesamtLage += bw.getLage();
            this.gesamtPreis += bw.getPreis();
            this.gesamtService += bw.getService();
            counter ++;
        }
        this.gesamtAusstattung /= counter;
        this.gesamtLage /= counter;
        this.gesamtPreis /= counter;
        this.gesamtService /= counter;
        this.gesamtBewertung = (gesamtAusstattung + gesamtLage + gesamtPreis + gesamtService) / 4;
    }

    public void addBewertung(Bewertung bw){
        bewertungen.add(bw);
        berechneDurchschnitt();
    }

    public Bewertung getBewertung(int i){
        return bewertungen.get(i);
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGesamtService() {
        return gesamtService;
    }

    public double getGesamtPreis() {
        return gesamtPreis;
    }

    public double getGesamtLage() {
        return gesamtLage;
    }

    public double getGesamtAusstattung() {
        return gesamtAusstattung;
    }

    public double getGesamtBewertung() {
        return gesamtBewertung;
    }
}
