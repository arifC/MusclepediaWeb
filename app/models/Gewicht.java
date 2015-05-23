package models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.context.annotation.Conditional;
import play.data.validation.*;

import javax.persistence.*;
import javax.validation.Constraint;
/**
 * Created by ArifC on 03.05.2015.
 */
@Entity
public class Gewicht {

    @Id
    private UUID Gewicht;
    @Constraints.Required
    private double gewicht;
    private Date datum;
    private String datumText;

    public Gewicht(double gewicht){
        this.gewicht = gewicht;
        this.datum = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-YYYY");
        this.datumText = sdf.format(datum);
    }

    public Gewicht(double gewicht, Date datum){
        this.gewicht = gewicht;
        this.datum = datum;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-YYYY");
        this.datumText = sdf.format(datum);
    }

    public Gewicht(double gewicht, String datumText){
        this.gewicht = gewicht;
        this.datumText = datumText;
    }

    public double getGewicht() {
        return gewicht;
    }

    public Date getDatum() {
        return datum;
    }

    public String getDatumText() {
        return datumText;
    }

    public void setGewicht(double gewicht) {
        this.gewicht = gewicht;

        //Neues Datum zum Gewicht
        this.datum = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-YYYY");
        this.datumText = sdf.format(datum);
    }
}
