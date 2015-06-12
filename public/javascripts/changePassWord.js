function changePasswordForm(){
    $("#changeForm").html("<form method='post' action='/changePassword' name='changeForm'  onsubmit='return validateChanges()'> <label> Altes Passwort </label>" +
    "<input class='form-control' type='password' name='oldPassword' placeholder='Altes Passwort'> <label> Neues Passwort </label>"+
    "<input class='form-control' type='password' name='newPassword' placeholder='Neues Passwort'>"+
       "<label> Neues Passwort wiederholen </label>"+
    "<input class='form-control' type='password' name='newPassword2' placeholder='Wiederhole neues Passwort'>"+"<br/>"+
        "<input type='submit' class='btn btn-success' name='change' value='Passwort ändern'/>"+
        "</form>");
}

function validateChanges(){
    var oldPassword = document.forms["changeForm"]["oldPassword"].value;
    var password = document.forms["changeForm"]["newPassword"].value;
    var password2 = document.forms["changeForm"]["newPassword2"].value;

    if (oldPassword==null||oldPassword=="" ||password == null || password == "" || password2 == null || password2 == "") {
        alert("Es müssen alle Felder ausgefüllt sein");
        return false;
    }else {
        if (passwort != passwort2) {
            alert("Passwörter sind ungleich");
            return false;
        }
    }
}
