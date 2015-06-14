package controllers;

import com.avaje.ebean.Ebean;
import models.*;
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

public class Application extends Controller {

    public static User loggedInUser = null;


    public static boolean checkLogin(){
        String username = session("username");
        System.out.print("SESSIONNAME:" + username);
        if(username == null){
            System.out.print("REDIRECT");
            return false;
        }
        else{
            List<User> users = Ebean.find(User.class).findList();
            for (User user : users) {
                if(user.getName().equals(username)) {
                    loggedInUser = user;
                    session("username", loggedInUser.getName());
                }
            }
            return true;
        }

    }

    public static Result login(){return ok(login.render(" "));}

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
            session("username", loggedInUser.getName());
            return ok(home.render(loggedInUser));
        }

        //liste aller User
        List<User> allUsers = Ebean.find(User.class).findList();
        for(User user : allUsers){
            //User aus db
            String dbUserPassword = user.getPassword();
            String dbUserName = user.getName();
            //User aus Anfrage
            String logInPassword = verschluesseln(dynamicForm.get("password"));
            String logInUserName = dynamicForm.get("username");

            if(dbUserPassword.matches(logInPassword) && (dbUserName.matches(logInUserName))){
                loggedInUser = user;
                session("username", loggedInUser.getName());
                return ok(home.render(loggedInUser));
            }
        }
        return ok(login.render("loginFail"));
    }

    public static Result help() {
        checkLogin();
        return ok(help.render(loggedInUser));
    }
    public static Result oberkoerper() {
        checkLogin();
        return ok(uebungen_oberkoerper.render(loggedInUser));
    }
    public static Result studios(){
        checkLogin();
        return ok(studios.render(loggedInUser));
    }
    public static Result knStudio(){

        DynamicForm dynamicForm = Form.form().bindFromRequest();
        String studio = dynamicForm.get("searchStudio");
        List<Studio> studios = Ebean.find(Studio.class).where().eq("ort", studio).findList();
        if(studio==null) {
            studios= Ebean.find(Studio.class).findList();
        }
        checkLogin();
        return ok(studios_kn.render(studios,loggedInUser));
    }
    public static Result arme() {
        checkLogin();
        return ok(uebungen_arme.render(loggedInUser));
    }
    public static Result beine() {
        checkLogin();
        return ok(uebungen_beine.render(loggedInUser));
    }
    public static Result bauch() {
        checkLogin();
        return ok(uebungen_bauch.render(loggedInUser));
    }
    public static Result impressum() {
        checkLogin();
        return ok(impressum.render(loggedInUser));
    }
    public static Result kontakt(){
        checkLogin();
        return ok(kontakt.render(loggedInUser));
    }
    public static Result profil(){
        if(checkLogin()){
            return ok(profil.render(loggedInUser));
        }
        else{
            return redirect("/");
        }

    }
    public static Result plaene_anfaenger(){
        checkLogin();
        return ok(plaene_anfaenger.render(loggedInUser));
    }
    public static Result plaene_fortgesch(){
        checkLogin();
        return ok(plaene_fortgesch.render(loggedInUser));
    }
    public static Result plaene_profi() {
        checkLogin();
        return ok(plaene_profi.render(loggedInUser));
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
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        List<User> user2 = Ebean.find(User.class).findList();
        String username = dynamicForm.get("benutzername");
        String email = dynamicForm.get("mail");
        for(User u : user2) {
            if (u.getName().equals(username)) {
                return ok(login.render("username"));

            }
            if (u.getEmail().equals(email)) {
               return ok(login.render("mail"));
            }
        }
        User user = new User(dynamicForm.get("benutzername"), dynamicForm.get("mail"), verschluesseln(dynamicForm.get("password2")));
        Ebean.save(user);
        return ok(login.render("success"));
    }

    public static Result searchStudio(String studio) {
        String result=" ";
        List<Studio> studios = Ebean.find(Studio.class).findList();
        for (Studio s: studios) {
            if (s.getOrt().toUpperCase().startsWith(studio.toUpperCase())) {
                result=s.getOrt();
                return ok(result);
            }

        }
        return ok(result);
    }

    public static Result home() {
        if(checkLogin()){
            return ok(home.render(loggedInUser));
        }
        else{
            return redirect("/");
        }
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
        checkLogin();
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
        checkLogin();
        loggedInUser.deleteFromPlan(uebungsauswahl);
        return ok(profil.render(loggedInUser));
    }

    public static Result rateStudio(){
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        //schauen ob der User schon eine Bewertung für das gesuchte Studio abgegeben hat
        List<Rating> ratings = Ebean.find(Rating.class).findList();
        String studioname = dynamicForm.get("studio");
        int rating = Integer.parseInt(dynamicForm.get("value"));
        boolean ratingAlreadyExists = false;
        boolean valueNotInRange = false;
        if (ratings != null) {
            for (Rating r : ratings) {
                if (r.getStudio().getName().equals(studioname) && r.getUser().getName().equals(loggedInUser.getName())) {
                    ratingAlreadyExists = true;
                }
            }
            if (rating > 10 || rating <= 0) {
                valueNotInRange = true;
            }
        }
        if(!ratingAlreadyExists && !valueNotInRange) {
            List<Studio> studios = Ebean.find(Studio.class).findList();
            Studio chosenStudio = null;
            for (Studio s : studios) {
                if (s.getName().equals(studioname)) {
                    chosenStudio = s;
                }
            }
            checkLogin();
            Rating rating2 = new Rating(chosenStudio, loggedInUser, rating);
            Ebean.save(rating2);
            loggedInUser.rateStudio(chosenStudio, rating2);
            return ok(home.render(loggedInUser));
        }else{
            // Hier muss dann noch eine Ausgabe hin: "also Bewertung bereits abgegeben"
            return ok(studios.render(loggedInUser));
        }
    }
    public static Result changePassword(){
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        String oldPW = verschluesseln(dynamicForm.get("oldPassword"));
        checkLogin();
        if(oldPW.equals(loggedInUser.getPassword())){
            String newPW = verschluesseln(dynamicForm.get("newPassword"));
            loggedInUser.setPassword(newPW);
            return ok(profil.render(loggedInUser));
        }
        else{
            return ok(profil.render(loggedInUser));
        }

    }

    public static Result logout() {
        session().clear();
        return redirect("/");
    }

    public static Result myStudio() {
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        String studioName = dynamicForm.get("studio");
        checkLogin();
        for(Studio studio : Ebean.find(models.Studio.class).findList()){
            if(studio.getName().equals(studioName)){
                loggedInUser.setStudio(studio);
            }
        }
        return ok(profil.render(loggedInUser));
    }
}
