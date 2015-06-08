/**
 * Created by Felix on 13.05.15.
 */

function validateRegistryForm(){
    var benutzername = document.forms["regForm"]["benutzername"].value;
    var passwort = document.forms["regForm"]["password"].value;
    var passwort2 = document.forms["regForm"]["password2"].value;
    var email = document.forms["regForm"]["mail"].value;

    if (benutzername == null || benutzername == "" || password == null || password == "" || password2 == null || password2 == "" || email == null || email == "") {
        alert("Es m�ssen alle Felder ausgef�llt sein");
        return false;
    }else {
        if (password != password2) {
            alert("Passw�rter sind ungleich");
            return false;
        } else {
            if (benutzername.length <= 5) {
                alert("Benutzername muss mind. 6 Zeichen lang sein");
                return false;
            }
        }
    }
}
