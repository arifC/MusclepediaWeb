package models;

import com.avaje.ebean.Ebean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.swing.*;

import java.util.UUID;

@Entity
public class Studio {
    @Id
    private UUID studio_id;
    private int plz;
    private String ort;
    private String strasse;
    private String name;

    @OneToMany(cascade=CascadeType.ALL)
    private List<Rating> ratings;

    private double totalRating = 0;
    private double totalFacilities = 0;
    private double totalLocation = 0;
    private double totalPrice = 0;
    private double totalService = 0;

    public Studio(String name, String strasse, int plz, String ort){
        ratings = new ArrayList<Rating>();
        this.name = name;
        this.strasse = strasse;
        this.plz = plz;
        this.ort = ort;
    }

    public void calcAverage(){
        double counter = 0;
        for(Rating rating : ratings){
            this.totalFacilities += rating.getFacilities();
            this.totalLocation += rating.getLocation();
            this.totalPrice += rating.getPrice();
            this.totalService += rating.getService();
            counter ++;
        }
        this.totalFacilities /= counter;
        this.totalLocation /= counter;
        this.totalPrice /= counter;
        this.totalService /= counter;
        this.totalRating = (totalFacilities + totalLocation + totalPrice + totalService) / 4;
    }

    public double calcAverage2(){
        double counter = 0;
        double summe=0;
        for(Rating rating : ratings){
            summe += rating.getValue();
            counter++;
        }
        totalRating = summe;
        return summe/counter;
    }
    public void addBewertung(Rating bw){
        ratings.add(bw);
        Ebean.save(this);
        calcAverage2();
        JFrame frame = new JFrame("Nachricht");
        JOptionPane.showMessageDialog(frame,bw.getValue());
        JOptionPane.showMessageDialog(frame,this.getTotalRating());
    }

    public Rating getBewertung(int i){
        return ratings.get(i);
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public int getPlz() {
        return plz;
    }

    public void setPlz(int plz) {
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

    public double getTotalService() {
        return totalService;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double getTotalLocation() {
        return totalLocation;
    }

    public double getTotalFacilities() {
        return totalFacilities;
    }

    public double getTotalRating() {
        return totalRating;
    }
}
