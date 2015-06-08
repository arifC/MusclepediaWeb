
function validateChanges(){
    var oldPasswort = document.forms["changeForm"]["oldPW"].value;
    var passwort = document.forms["changeForm"]["newRep"].value;
    var passwort2 = document.forms["changeForm"]["newOW"].value;

    if (oldPasswort==null||oldPasswort=="" ||passwort == null || passwort == "" || passwort2 == null || passwort2 == "") {
        alert("Es m�ssen alle Felder ausgef�llt sein");
        return false;
    }else {
        if (passwort != passwort2) {
            alert("Passw�rter sind ungleich");
            return false;
        }
    }
}
