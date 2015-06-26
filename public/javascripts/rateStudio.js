/**
 * Created by Felix on 19.06.2015.
 */
function rateStudio(){
    var x = document.getElementById("rating").value;

    if(x > 10 || x<1){
        alert("Bitte geben Sie einen Wert zwischen 1 und 10 ein");
        return false;
    }else {
        return true;
    }

}