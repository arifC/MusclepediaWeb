/**
 * Created by ArifC on 21.05.2015.
 */

function sendSearch(){
    var input = document.getElementById("searchStudio").value;
    $("#output").load("searchStudio?value=" + input);
}