/**
 * Created by ArifC on 08.05.2015.
 */


$(document).ready(function (){
    $(".nav-tabs").children("li").removeClass("active");
    var url = window.location.pathname;
    if(url.search("uebungen")){
        url = url.substring(1,9);
    }
    else{
        url = url.substring(1);
    }
    $("#" + url).addClass("active");
});
