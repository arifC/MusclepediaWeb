/**
 * Created by Felix on 13.05.15.
 */

function validateForm(){
    var benutzername = document.forms["regForm"]["benutzername"].value;
    var passwort = document.forms["regForm"]["passwort"].value;
    var passwort2 = document.forms["regForm"]["passwort2"].value;
    var email = document.forms["regForm"]["mail"].value;

    alert(benutzername);

    if (benutzername == null || benutzername == "" || passwort == null || passwort == "" || passwort2 == null || passwort2 == "" || email == null || email == "") {
        alert("Es müssen alle Felder ausgefüllt sein");
        return false;
    }else {
        if (passwort != passwort2) {
            alert("Passwörter sind ungleich");
            return false;
        } else {
            alert("HIER");
            if (benutzername.length <= 5) {
                alert("Benutzername muss mind. 6 Zeichen lang sein");
                return false;
            }
        }
    }
}