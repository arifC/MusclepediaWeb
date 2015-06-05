package controllers;

import com.avaje.ebean.Ebean;
import models.Exercise;
import models.User;
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

    public static User loggedInUser = null;

    public static Result login(){return ok(login.render());
    }

    public static Result start() {
        //sobald der passende User gefunden ist, ist foundUser true
        boolean foundUser = false;
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        
        //Kurze Vereinfachung für debug
        if(dynamicForm.get("username").matches("admin")){
            loggedInUser = new User("admin", "nomail", "admin");
            Ebean.save(loggedInUser);
            return ok(home.render(loggedInUser));
        }
        //liste aller User
        List<User> allUsers = Ebean.find(User.class).findList();
        for(User user : allUsers){
            //User aus db
            String dbUserPassword = user.getPassword();
            String dbUserName = user.getName();
            //User aus Anfrage
            String logInPassword = verschluesseln(dynamicForm.get("passwort"));
            String logInUserName = dynamicForm.get("username");

            if(dbUserPassword.matches(logInPassword) && (dbUserName.matches(logInUserName))){
                loggedInUser = user;
                foundUser = true;
            }
        }
        if (foundUser){
            return ok(home.render(loggedInUser));
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
    public static Result impressum() {return ok(impressum.render());
    }
    public static Result kontakt(){return ok(kontakt.render());
    }
    public static Result profil(){
        return ok(profil.render(loggedInUser));
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
    public static Result createUser(){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            DynamicForm dynamicForm = Form.form().bindFromRequest();
            UUID id = UUID.randomUUID();
            User user = new User(dynamicForm.get("benutzername"),dynamicForm.get("mail"), verschluesseln(dynamicForm.get("passwort2")));
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

    public static Result home() {
        return ok(home.render(loggedInUser));
    }

    public static Result addExercise(){
        Exercise beispiel = new Exercise("szcurls");
        Ebean.save(beispiel);
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        List<Exercise> uebungen = Ebean.find(Exercise.class).findList();
        String auswahl = dynamicForm.get("auswahl");
        Exercise uebungsauswahl = null;
        for(Exercise u : uebungen){
            System.out.println(u.getName());
            if(u.getName().equals(auswahl)){
                uebungsauswahl = u;
                System.out.print("JAAA");
                System.out.print(loggedInUser);
            }
        }

        loggedInUser.addToPlan(uebungsauswahl);
        return ok(home.render(loggedInUser));
    }
}
