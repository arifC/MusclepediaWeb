function calcCal(){
    var gewicht = document.getElementById("gewichtK").value;
    var groesse = document.getElementById("groesseK").value;
    var alter = document.getElementById("alter").value;
    var selecter = document.getElementById("faktor");
    var faktor = selecter.options[selecter.selectedIndex].value;

    var bedarf = 66.47 + 13.7*gewicht + 500*groesse -6.8*alter;
    bedarf= bedarf*faktor;
    $("#kalerg").html("Du brauchst am Tag: " + bedarf.toPrecision(4) + " Kalorien.");
}
