function bmirechnen(){
    var gewicht = document.getElementById("gewichtB").value;
    var groesse = document.getElementById("groesseB").value;
    var geschlecht = document.getElementById("gesch").options[document.getElementById("gesch").selectedIndex].text
    if(groesse > 2.5 || gewicht < 30 || gewicht > 300){
        alert("Falsche Eingabe")
    }
    var bmi = gewicht/(groesse*groesse);
    if(geschlecht == "männlich"){
        if(bmi<20){
            $("#bmierg").html("Untergewichtig der BMI liegt bei:" +bmi);
        }
        if (bmi>=20 && bmi<25){
            $("#bmierg").html("Normalgewichtig der BMI liegt bei:"+bmi);
        }
        if(bmi>=25 && bmi<20){
            $("#bmierg").html("Leichtes �bergewicht der BMI liegt bei:" +bmi);
        }
        if(bmi>=30 && bmi<40){
            $("#bmierg").html("�bergewichtig der BMI liegt bei:"+bmi);
        }
        if(bmi>=40){
            $("#bmierg").html("Sehr starkes �bergewicht der BMI liegt bei:"+bmi);
        }

    }else{
        if(bmi<19){
            $("#bmierg").html("Untergewichtig der BMI liegt bei:"+bmi);
        }
        if (bmi>=19 && bmi<24){
            $("#bmierg").html("Normalgewichtig der BMI liegt bei:"+bmi);
        }
        if(bmi>=24 && bmi<30){
            $("#bmierg").html("Leichtes �bergewicht der BMI liegt bei:"+bmi);
        }
        if(bmi>=30 && bmi<40){
            $("#bmierg").html("�bergewichtig der BMI liegt bei:"+bmi);
        }
        if(bmi>=40){
            $("#bmierg").html("Sehr starkes �bergewicht der BMI liegt bei:"+bmi);
        }

    }
}