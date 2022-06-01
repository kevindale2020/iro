function date_time(id)
{
        date = new Date;
        year = date.getFullYear();
        month = date.getMonth();
        months = new Array('January', 'February', 'March', 'April', 'May', 'June', 'Jully', 'August', 'September', 'October', 'November', 'December');
        d = date.getDate();
        day = date.getDay();
        days = new Array('Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday');

        result = days[day]+' - '+months[month]+' '+d+', '+year;
        document.getElementById(id).innerHTML = result;
        setTimeout('date_time("'+id+'");','1000');
        return true;
}

function updateclock()
{

    var time = new Date();
    var todisplay = '';

    if (time.getHours() < 10) todisplay += time.getHours();
    else todisplay += time.getHours();

    if (time.getMinutes() < 10) todisplay += ':0' + time.getMinutes();
    else todisplay += ':' + time.getMinutes();

    document.getElementById("clock").innerHTML = todisplay;
}