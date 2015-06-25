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
    /**
     * Das Datum an dem das Gewicht erstellt wurde als Objekt der Klasse Date
     */
    private Date date;
    /**
     * Das Datum an dem das Gewicht erstellt wurde als String
     */
    private String dateText;
    /**
     * Jedes Gewicht ist einem bestimmten User zugeordnet. Hierzu ist in jedem Gewichtsobjekt der User gespeichert.
     */
    @ManyToOne
    private User user;

    /**
     * Erstellt und speichert ein Gewicht, nach Angabe eines Gewichts und eines Users, der hier dazugehört.
     * @param weight
     * @param user
     */
    public Weight(double weight, User user){
        this.weight_id = UUID.randomUUID();
        this.weight = weight;
        this.date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd. MMMM YYYY HH:mm");
        this.dateText = sdf.format(date);
        this.user = user;
        Ebean.save(this);
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

    public User getUser() {
        return user;
    }
}
