package controllers;

import com.avaje.ebean.Ebean;
import models.Benutzer;
import play.data.DynamicForm;
import play.mvc.*;
import views.html.*;
import play.data.Form;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class Application extends Controller {

    public static Result login(){return ok(login.render());
    }

    public static Result start() {
        Benutzer loggedIn = null;
        boolean treffer = false;
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        List<Benutzer> users = Ebean.find(models.Benutzer.class).findList();
        for(Benutzer user : users){
            System.out.println(user.getName());
            System.out.println(dynamicForm.get("username"));
            String db_name = user.getName();
            String logInName = dynamicForm.get("username");
            if(db_name.matches(logInName)){
                loggedIn = user;
                treffer = true;
            }
        }
        if (treffer){
            return ok(home.render(loggedIn));
        }
        else{
            return redirect("/");
        }
    }

    public static Result help() {return ok(help.render());
    }
    public static Result oberkoerper() {return ok(uebungen_oberkoerper.render());
    }
    public static Result studios(){return ok(studios.render());
    }
    public static Result knStudio(){return ok(studios_kn.render());
    }
    public static Result arme() {return ok(uebungen_arme.render());
    }
    public static Result beine() {return ok(uebungen_beine.render());
    }
    public static Result bauch() {return ok(uebungen_bauch.render());
    }
    public static Result impress() {return ok(impressum.render());
    }
    public static Result kontakt(){return ok(kontakt.render());
    }
    public static Result profil(){

        Benutzer user = new Benutzer("Zink", "test@mail.com", "passwort");
        return ok(profil.render(user));
    }
    public static Result plaene_anfaenger(){return ok(plaene_anfaenger.render());
    }
    public static Result plaene_fortgesch(){return ok(plaene_fortgesch.render());
    }
    public static Result plaene_profi() {return ok(plaene_profi.render());
    }

    public static String verschluesseln(String eingabe){
        String result = null;
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(eingabe.getBytes(Charset.forName("UTF-8")));
            result = String.format(Locale.ROOT, "%032x", new BigInteger(1, md.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
        return result;

    }
    public static Result benutzer_anlegen(){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            DynamicForm dynamicForm = Form.form().bindFromRequest();
            UUID id = UUID.randomUUID();
            Benutzer user = new Benutzer(dynamicForm.get("benutzername"),dynamicForm.get("mail"), verschluesseln(dynamicForm.get("passwort2")));
            Ebean.save(user);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
    }
        return ok(login.render());
    }

    public static Result searchStudio(String studio) {
        String result = "";
        switch (studio){
            case "k":
                result ="konstanz";
        }
        return ok(result);
    }
}
