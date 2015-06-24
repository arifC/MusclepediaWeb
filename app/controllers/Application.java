package controllers;

import com.avaje.ebean.Ebean;
import models.*;
import play.data.DynamicForm;
import play.mvc.*;
import views.html.*;
import play.data.Form;
//import it.innove.PdfGenerator;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;
//import play.libs.mailer.Email;
//import play.api.libs.mailer.MailerClient;
//import javax.inject.Inject;


public class Application extends Controller {

    /**
     * verweist auf den aktuellen User aus der Datenbank
     */
    public static User loggedInUser = null;

    /**
     * überprüft ob ein Username in der Session abgespeicher ist
     * falls nicht, wird der aktuelle user in der Session gespeichert
     * @return false falls kein user in der Session gespeichert ist, true falls doch
     */
    public static boolean checkLogin(){
        String username = session("username");
        if(username == null){
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

    /**
     * aufgerufen falls angegebener Pfad "/" ist
     * @return lädt die Defaultseite (Loginseite)
     */
    public static Result login(){return ok(login.render(" "));}

    /**
     * speichert initial Studio- und Übungsdaten in der Datenbank ab
     */
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

    /**
     * Sucht aus der Datenbank alle User und vergleicht mit den eingegebenen Logindaten.
     * Falls solch ein User exisitiert, wird die Homeseite aufgerufen und der User in der Session gespeichert
     * @return Leitet den Benutzer bei erfolgreichem Login auf die Home-Seite
     */
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

    /**
     * leitet den User auf die Hilfe-Seite weiter
     * @return Menupunkt Hilfe
     */
    public static Result help() {
        checkLogin();
        return ok(help.render(loggedInUser));
    }

    /**
     * leitet den User auf die Studios-Seite weiter
     * @return Menupunkt Studios
     */
    public static Result studios(){
        checkLogin();
        return ok(studios.render(loggedInUser));
    }

    /**
     * leitet einen auf die Studios-Konstan-Seite weiter wenn Konstanz als Stadt ausgewählt wurde
     * @return Menupunkt Studios in Konstanz
     */
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

    /**
     * leitet den User auf die Oberkörper-Seite
     * @return Menupunkt Übungen/Oberkörper
     */
    public static Result oberkoerper() {
        checkLogin();
        return ok(uebungen_oberkoerper.render(loggedInUser));
    }
    /**
     * leitet den User auf die Arme-Seite
     * @return Menupunkt Übungen/Arme
     */
    public static Result arme() {
        checkLogin();
        return ok(uebungen_arme.render(loggedInUser));
    }
    /**
     * leitet den User auf die Beine-Seite
     * @return Menupunkt Übungen/Beine
     */
    public static Result beine() {
        checkLogin();
        return ok(uebungen_beine.render(loggedInUser));
    }
    /**
     * leitet den User auf die Bauch-Seite
     * @return Menupunkt Übungen/Bauch
     */
    public static Result bauch() {
        checkLogin();
        return ok(uebungen_bauch.render(loggedInUser));
    }

    /**
     * Leitet den User auf die Impressum-Seite weiter
     * @return lädt Impressum-Seite
     */
    public static Result impressum() {
        checkLogin();
        return ok(impressum.render(loggedInUser));
    }

    /**
     * leitet den User auf die Kontaktseite weiter
     * @return lädt Kontakt-Seite
     */
    public static Result kontakt(){
        checkLogin();
        return ok(kontakt.render(loggedInUser));
    }

    /**
     * Leitet den User auf die Profil-Seite weiter
     * @return läd die Profil-Seite und übergibt alle Weights aus der Datenbank plus den user
     */
    public static Result profil(){
        if(checkLogin()){
            return ok(profil.render(getWeights(),loggedInUser));
        }
        else{
            return redirect("/");
        }

    }

    /**
     * Leitet den User auf die pläne/anfänger-Seite weiter
     * @return
     */
    public static Result plaene_anfaenger(){
        checkLogin();
        return ok(plaene_anfaenger.render(loggedInUser));
    }
    /**
     * Leitet den User auf die pläne/fortgeschritten-Seite weiter
     * @return
     */
    public static Result plaene_fortgesch(){
        checkLogin();
        return ok(plaene_fortgesch.render(loggedInUser));
    }
    /**
     * Leitet den User auf die pläne/profi-Seite weiter
     * @return
     */
    public static Result plaene_profi() {
        checkLogin();
        return ok(plaene_profi.render(loggedInUser));
    }

    /**
     * Beim Regisitrieren wird das passwort durch diese Methode verschlüsselt und in der Datenbank abgespeichert
     * @param eingabe passwort als String
     * @return mit MD5 verschlüsselten HashCode
     */
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

    /**
     * leitet den User zur Loginseite mit entsprechender Meldung ob Registrierung erfolgreich war oder nicht
     * @return render("username") falls name schon vergeben, render("mail") falls mail schon vergeben, render("success") falls erfolgreich
     */
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

    /**
     *
     * @param studio
     * @return
     */
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

    /**
     * Um zurück auf die Home-Seite zu gelangen
     * @return Menupunkt Home
     */
    public static Result home() {
        if(checkLogin()){
            return ok(home.render(loggedInUser));
        }
        else{
            return redirect("/");
        }
    }

    /**
     *
     * @return
     */
    public static Result addExercise(){
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
        return ok(profil.render(getWeights(),loggedInUser));
    }

    public static Result rateStudio(){
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        //schauen ob der User schon eine Bewertung für das gesuchte Studio abgegeben hat
        int rating = 0;
        List<Rating> ratings = Ebean.find(Rating.class).findList();
        String studioname = dynamicForm.get("studio");
        try {
            rating = Integer.parseInt(dynamicForm.get("value"));
        }catch(NumberFormatException e){
            checkLogin();
            return ok(studios.render(loggedInUser));
        }
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
            List<Studio> s = Ebean.find(Studio.class).where().eq("ort", "Konstanz").findList();
            return ok(studios_kn.render(s,loggedInUser));
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
            return ok(profil.render(getWeights(),loggedInUser));
        }
        else{
            return ok(profil.render(getWeights(),loggedInUser));
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
        return ok(profil.render(getWeights(),loggedInUser));
    }
    /*private final MailerClient mailer;

    @Inject
    public ApplicationJava(MailerClient mailer) {
        this.mailer = mailer;
    }*/

    public static Result sendMail(){
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        String firstName = dynamicForm.get("name");
        String lastName =dynamicForm.get("surname");
        String answerMail= dynamicForm.get("answerMail");
        String content=dynamicForm.get("mailContent");


        /*
            final Email email = new Email();
            email.setSubject("Kontakt Anfrage");
            email.setFrom("Server FROM <from@email.com>");
            email.addTo("Admin TO <to@email.com>");
             email.setBodyText("Anfrage von: " +firstName +" " + lastName +" "+ answerMail +" " +content);
             mailer.send(email);

         */


        return ok(home.render(loggedInUser));
    }
    public static Result checkName(String input){
        String result ="";
        if(input.equals("")){
            return ok(" ");
        }
        if(input.length()<=5){
            result="Benutzername muss 6 Zeichen haben";
            return ok(result);
        }
        return ok(result);
    }
    public static Result checkPassword(String input){
        String result =" ";
        if(input.equals("")){
            return ok(" ");
        }
        if(input.length()<=4){
            result="Kurze Passwörter sind unsicher!";
            return ok(result);
        }
        return ok(result);
    }

    public static Result addWeight() {
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        double weight = Double.parseDouble(dynamicForm.get("weight"));
        loggedInUser.addWeight(weight, loggedInUser);

        List<Weight> weights = Ebean.find(Weight.class).findList();
        return ok(profil.render(getWeights() , loggedInUser));
    }

    public static List<Weight> getWeights(){
        return Ebean.find(Weight.class).findList();
    }

    /*public static Result generatePDF() {
            return PdfGenerator.ok(profil.render(loggedInUser), "http://localhost:9000");
    }*/
}
