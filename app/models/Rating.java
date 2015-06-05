package models;

import play.data.validation.*;

import javax.persistence.*;

import java.util.UUID;

@Entity
public class Rating {
    @Id
    private UUID rating_id;
    @Constraints.Required
    private double facilities;
    @Constraints.Required
    private double service;
    @Constraints.Required
    private double price;
    @Constraints.Required
    private double location;

    public Rating(double facilities, double service, double price, double location){
        this.facilities = facilities;
        this.service = service;
        this.price = price;
        this.location = location;
    }

    public double getFacilities() {
        return facilities;
    }

    public void setFacilities(double facilities) {
        this.facilities = facilities;
    }

    public double getService() {
        return service;
    }

    public void setService(double service) {
        this.service = service;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getLocation() {
        return location;
    }

    public void setLocation(double location) {
        this.location = location;
    }

}
