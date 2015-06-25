package models;

import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.File;
import java.nio.file.Files;
import java.sql.Blob;
import java.util.UUID;

/**
 * Created by ArifC on 26.06.2015.
 */
@Entity
public class Picture extends Model {
    public Blob image;

    @Id
    public UUID id;

    public byte[] img;

    public Picture(){};
    public Picture(File file){
        try{
            img = Files.readAllBytes(file.toPath());
        }
        catch(Exception e){

        }

    }
}
