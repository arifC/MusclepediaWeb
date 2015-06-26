/**
 * Created by Felix on 19.06.2015.
 */
function rateStudio(){
    alert("hallo");
    var value = document.forms["ratingForm"]["value"].value;
    var x = document.getElementById("rating").value;

    if(value > 10){
        alert("Bitte geben Sie einen Wert zwischen 1 und 10 ein");
        return false;
    }else {
        return true;
    }

}