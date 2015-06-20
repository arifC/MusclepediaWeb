
function nameCheck(){
    var input = document.getElementById("regName").value;
    $("#nameOutput").load("checkName?value=" + input);
}
function passwordCheck(){
    var input = document.getElementById("regPass").value;
    $("#passwordOutput").load("checkPassword?value=" +input);
}