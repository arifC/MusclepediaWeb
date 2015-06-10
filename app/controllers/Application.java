package controllers;

import com.avaje.ebean.Ebean;
import models.*;
import play.data.DynamicForm;
import play.mvc.*;
import views.html.*;
import play.data.Form;

import javax.swing.*;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class Application extends Controller {

    public static User loggedInUser = null;
    public static Studio konstanz = null;

    public static Result login(){return ok(login.render());
    }

    public static void buildDatabase(){
        //Studios
        Studio clever_konstanz = new Studio("Clever Fit", "Rudolph-Diesel-Str.", 78462, "Konstanz");
        Studio happy_konstanz = new Studio("Happy Fit", "Bruder-Klaus-Str.", 78467, "Konstanz");
        Studio speedy_konstanz = new Studio("Speedy Fit", "Joseph-Belli-Weg", 78467, "Konstanz");
        Ebean.save(clever_konstanz);
        Ebean.save(happy_konstanz);
        Ebean.save(speedy_konstanz);

        //Übungen
        Exercise french_arme = new Exercise("Frenchpress", Muscle.Arme, "Eine Trizepsübung im Liegen, die alle Muskelköpfe belastet.", "Fortgeschritten");
        Exercise hammer_arme = new Exercise("Hammer-Curls", Muscle.Arme, "Eine Bizepsübung im Stehen, die den Bizeps und den Unterarm trainiert.", "Fortgeschritten");
        Exercise konz_arme = new Exercise("Konzentration-Curls", Muscle.Arme, "Eine Bizepsübung im Sitzen, die sich auf den inneren Muskelkopf konzentriert.", "Fortgeschritten");
        Ebean.save(french_arme);
        Ebean.save(hammer_arme);
        Ebean.save(konz_arme);

    }

    public static Result start() {
        //konstanz = new Studio("clever","rudolph-diesel",78462,"konstanz");
        //Ebean.save(konstanz);
        //sobald der passende User gefunden ist, ist foundUser true
        boolean foundUser = false;
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        
        //Kurze Vereinfachung für debug
        if(dynamicForm.get("username").matches("admin")){
            loggedInUser = new User("admin", "nomail", "admin");
            Ebean.save(loggedInUser);
            if(Ebean.find(Studio.class).findList().size() == 0){
                buildDatabase();
                System.out.println("######DATENBANK NEU AUFBAUEN######");
            }
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
            JFrame frame = new JFrame("Nachricht");
            JOptionPane.showMessageDialog(frame,"Username oder Passwort falsch");
            return redirect("/");
        }
    }

    public static Result help() {return ok(help.render());
    }
    public static Result oberkoerper() {return ok(uebungen_oberkoerper.render());
    }
    public static Result studios(){return ok(studios.render());
    }
    public static Result knStudio(){
        //Form<Studio> studios = Form.form(Studio.class);
        List<Studio> studios = Ebean.find(Studio.class).findList();
        Studio studio = null;
        for(Studio s : studios){
            if(s.getName().equals("clever")){
                studio = s;
            }
        }
        return ok(studios_kn.render(studio));
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
        boolean usernameInUse = false;
        boolean emailInUse  = false;
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        List<User> user2 = Ebean.find(User.class).findList();
        String username = dynamicForm.get("benutzername");
        String email = dynamicForm.get("mail");
        for(User u : user2) {
            if (u.getName().equals(username)) {
                usernameInUse = true;
            }
            if (u.getEmail().equals(email)) {
                emailInUse = true;
            }
        }

        if(!usernameInUse && !emailInUse) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                UUID id = UUID.randomUUID();
                User user = new User(dynamicForm.get("benutzername"), dynamicForm.get("mail"), verschluesseln(dynamicForm.get("passwort2")));
                Ebean.save(user);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return ok(login.render());
        }else{
            JFrame frame = new JFrame("Nachricht");
            if(usernameInUse){
                JOptionPane.showMessageDialog(frame,"Username ist schon vergeben");
            }
            if(emailInUse){
                JOptionPane.showMessageDialog(frame,"Diese Email-Adresse ist schon registriert");
            }
            return ok(login.render());
        }
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
            if(u.getName().equals(auswahl)){
                uebungsauswahl = u;
            }
        }

        loggedInUser.addToPlan(uebungsauswahl);
        return ok(home.render(loggedInUser));
    }

    public static Result deleteExercise(){
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        List<Exercise> uebungen = Ebean.find(Exercise.class).findList();
        String auswahl = dynamicForm.get("auswahl");
        Exercise uebungsauswahl = null;
        for(Exercise u : uebungen) {
            if (u.getName().equals(auswahl)) {
                uebungsauswahl = u;
            }
        }
        loggedInUser.deleteFromPlan(uebungsauswahl);
        return ok(profil.render(loggedInUser));
    }

    public static Result rateStudio(){
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        String studioname = dynamicForm.get("studio");
        int rating = Integer.parseInt(dynamicForm.get("value"));
        System.out.print(" AAAALATER" +rating);
        List<Studio> studios = Ebean.find(Studio.class).findList();
        Studio chosenStudio = null;
        for(Studio s : studios){
            if(s.getName().equals(studioname)){
                chosenStudio=s;
            }
        }
        Rating rating2 = new Rating(chosenStudio,loggedInUser,rating);
        Ebean.save(rating2);
        loggedInUser.rateStudio(chosenStudio,rating2);
        return ok(home.render(loggedInUser));
    }
    public static Result changePassword(){
        System.out.print("HEY");
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            DynamicForm dynamicForm = Form.form().bindFromRequest();
            String oldPW = verschluesseln(dynamicForm.get("oldPW"));
            String newPW = verschluesseln(dynamicForm.get("newPW"));
            String newRep= verschluesseln(dynamicForm.get("newRep"));
            System.out.print(oldPW);
            loggedInUser.changePassword(oldPW, newPW, newRep);
            Ebean.save(loggedInUser);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return ok(profil.render(loggedInUser));
    }
}
