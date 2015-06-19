/**
 * Created by Felix on 19.06.2015.
 */
function rateStudio(){
    var x = document.getElementById("rating").value;

    if(x > 10 || x<1){
        alert("hallo");
        return false;
    }else {
        return true;
    }

}