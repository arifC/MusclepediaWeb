package controllers;

import com.avaje.ebean.Ebean;
import models.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import play.data.DynamicForm;
import play.mvc.*;
import views.html.*;
import play.data.Form;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.util.List;
import java.util.Locale;

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
        Exercise sz_arme = new Exercise("SZ-Curls", Muscle.Arme, "Eine Bizepsübung im Stehen, die sich auf den inneren Muskelkopf konzentriert.", "Fortgeschritten");
        Exercise bankdruecken = new Exercise("bankdruecken", Muscle.Oberkoerper, "Eine Brustübung im Liegen.", "Fortgeschritten");
        Exercise kreuzheben = new Exercise("kreuzheben", Muscle.Oberkoerper, "Eine Rückenübung im Stehen.", "Fortgeschritten");
        Exercise lhrudern = new Exercise("lhrudern", Muscle.Oberkoerper, "Eine Rückenübung im Stehen.", "Fortgeschritten");
        Exercise crunches = new Exercise("crunches", Muscle.Bauch, "Eine Bauchübung im Liegen.", "Fortgeschritten");
        Exercise legraises = new Exercise("legraises", Muscle.Bauch, "Eine Bauchübung.", "Fortgeschritten");
        Exercise situps = new Exercise("situps", Muscle.Bauch, "Eine Bauchübung im Liegen.", "Fortgeschritten");
        Exercise kniebuegen = new Exercise("kniebeugen", Muscle.Oberkoerper, "Eine Beinübung im Stehen.", "Fortgeschritten");
        Exercise beinstrecker = new Exercise("beinstrecker", Muscle.Oberkoerper, "Eine Beinübung im Sitzen.", "Fortgeschritten");
        Exercise beinbeuger = new Exercise("beinbeuger", Muscle.Oberkoerper, "Eine Beinübung im Sitzen.", "Fortgeschritten");
        Ebean.save(french_arme);
        Ebean.save(hammer_arme);
        Ebean.save(sz_arme);
        Ebean.save(bankdruecken);
        Ebean.save(kreuzheben);
        Ebean.save(lhrudern);
        Ebean.save(crunches);
        Ebean.save(legraises);
        Ebean.save(situps);
        Ebean.save(kniebuegen);
        Ebean.save(beinstrecker);
        Ebean.save(beinbeuger);

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

    public static Result sendMail2() throws Exception{
        File file = new File("mydata.xlsx");
        FileOutputStream fileOut = new FileOutputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook();
        //Workbook wb = new XSSFWorkbook(); Doesn't work either
        Sheet sheet = wb.createSheet("Sheet1");
        int rNum = 0;
        Row row = sheet.createRow(rNum);
        int cNum = 0;
        Cell cell = row.createCell(cNum);
        cell.setCellValue("My Cell Value");
        wb.write(fileOut);
        fileOut.close();
        System.out.print("IN DER METHODE");
        return ok(kontakt.render(loggedInUser));
    }

    public static Result upload() throws Exception{
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart picture = body.getFile("picture");
        if (picture != null) {
            String fileName = picture.getFilename();
            String contentType = picture.getContentType();
            System.out.print(contentType);
            File file = picture.getFile();
            File test = new File(new File("C:\\test.jpg"),"test.jpg");
            return ok("File uploaded");
        } else {
            flash("error", "Missing file");
            return badRequest();
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
     * Beim Regisitrieren wird das passwort durch diese Methode mit MD5 verschlüsselt und in der Datenbank abgespeichert
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
     * leitet den User zur Loginseite mit entsprechender Meldung ob Registrierung erfolgreich war oder nicht.
     * Falls die Registireirung erfolgreich war wird der User mit seinen angegebenen Daten in der Datenbank gespeichert.
     * Zuerst wird geprüft, ob der gewünschte Username oder Mail schon vergeben ist
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
     * Wenn auf den Button "Hinzufügen" bei einer Übung geklickt wird, wird hier die richtige Übung aus der Datenbank rausgesucht und unter dem User abgespeichert, sprich in seinem Trainingsplan abgespeichert
     * @return Menupunkt Home
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

    /**
     * Wenn neben einer Übung beim Trainingsplan des Users auf "Löschen" geklickt wird, wird hier diese Übung aus dem plan des Users gelöscht
     * @return Menupunkt Profil
     */
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

    /**
     * Wenn ein User ein Studio bewerten will wird diese Methode aufgerufen. Zuerst Prüfung (serverseitig) ob die Bewertung im zulässigen Rahmen ist
     * und ob der User schon eine Bewertung für das jeweilige Studio abgegeben hat oder nicht.
     * Falls nicht, wird die entsprehende Bewertung in der Datenbank gespeichert
     * @return Bewertung abgeben erfolgreich: render("studios_kn"), nicht erfolgreich: render("stuidos")
     */
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
            checkLogin();
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

    /**
     * Wenn der User das Formular richtig ausgefüllt hat, sprich sein altes Passwort korrekt eingegeben hat (Vergleich mit Daten aus der Datenbank),
     * dann wird sein Passwort neu gesetzt
     * @return Menupunkt Profil
     */
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

    /**
     * Session wird geleert
     * @return zurück zur Defaultseite (Startseite)
     */
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

        return ok(home.render(loggedInUser));
    }

    /**
     * Serverseitige Überprüfung des gewünschten Nutzernames auf gewählte Regeln
     * @param input Username der bei der Registrierung eingegeben wird
     * @return "Nachricht" ob Regeln korrekt eingehalten wurden oder nicht
     */
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

    /**
     * Serverseitige Überprüfung des gewünschten Passworts auf gewählte Regeln
     * @param input Passwort das bei der Registrierung eingegeben wird
     * @return "Nachricht" ob das Passwort sicher ist oder nicht
     */
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

    /**
     * Das gewählte Gewicht vom User wird ausgelesen und ein neues Weight erzeugt. Dieses wird dann dem user hinzugefügt
     * @return Menupunkt Profil
     */
    public static Result addWeight() {
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        double weight = Double.parseDouble(dynamicForm.get("weight"));
        loggedInUser.addWeight(weight, loggedInUser);
        return ok(profil.render(getWeights() , loggedInUser));
    }

    /**
     * Holt alle Weights als aus der Datenbank und speichert sie in einer Liste
     * @return Eine List mit allen Weights
     */
    public static List<Weight> getWeights(){
        return Ebean.find(Weight.class).findList();
    }

}
