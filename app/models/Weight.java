package models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.avaje.ebean.Ebean;
import play.data.validation.*;

import javax.persistence.*;

/**
 * Created by ArifC on 03.05.2015.
 */
@Entity
public class Weight {

    @Id
    private UUID weight_id;
    @Constraints.Required
    private double weight;
    private Date date;
    private String dateText;
    @ManyToOne
    private User user;

    public Weight(double weight, User user){
        this.weight_id = UUID.randomUUID();
        this.weight = weight;
        this.date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd. MMMM YYYY HH:mm");
        this.dateText = sdf.format(date);
        this.user = user;
        Ebean.save(this);
    }

    public Weight(double weight, Date date){
        this.weight = weight;
        this.date = date;
        SimpleDateFormat sdf = new SimpleDateFormat("dd. MMMM YYYY HH:mm");
        this.dateText = sdf.format(date);
    }

    public Weight(double weight, String dateText){
        this.weight = weight;
        this.dateText = dateText;
    }

    public double getWeight() {
        return weight;
    }

    public Date getDate() {
        return date;
    }

    public String getDateText() {
        return dateText;
    }

    public void setWeight(double weight) {
        this.weight = weight;

        //Neues Datum zum Weight
        this.date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-YYYY");
        this.dateText = sdf.format(date);
    }

    public User getUser() {
        return user;
    }
}
