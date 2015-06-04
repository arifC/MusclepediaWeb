package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import java.util.UUID;

@Entity
public class Studio {
    @Id
    private UUID studio_id;
    private String plz;
    private String ort;
    private String strasse;
    private String name;

    @OneToMany(cascade=CascadeType.ALL)
    private List<Rating> ratings = new ArrayList<Rating>();

    private double totalRating;
    private double totalFacilities = 0;
    private double totalLocation = 0;
    private double totalPrice = 0;
    private double totalService = 0;

    public Studio(String name, String strasse, String plz, String ort){
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

    public void addBewertung(Rating bw){
        ratings.add(bw);
        calcAverage();
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
