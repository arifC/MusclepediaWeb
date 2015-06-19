/**
 * Created by ArifC on 19.06.2015.
 */
function test(){
    alert('mailto:bla?subject=' + $('#surname').val() + '&body=' + $('#contact').val());
    $('#mailform').attr('action', 'mailto:cerit_arif@gmx.de' + '?subject=' + $('#surname').val() + '&body=' + $('#contact').val());
    $('#mailform').submit();
}