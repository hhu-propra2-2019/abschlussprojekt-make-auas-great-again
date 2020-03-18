function pad(num) {
    var s = "00" + num;
    return s.substr(s.length - 2);
}

function formatDate(date) {
    var enddate = date.innerHTML;
    enddate = enddate.slice(0, 10);
    enddate = new Date(Date.parse(enddate));
    enddate = enddate.toString().slice(0, 10);
    date.innerHTML = enddate;

}

function clocktick(clock) {
    var startdatum = clock.getElementsByClassName("startdatum")[0].innerHTML;
    var enddatum = clock.getElementsByClassName("enddatum")[0].innerHTML;
    startdatum = new Date(Date.parse(startdatum));
    enddatum = new Date(Date.parse(enddatum));
    var now = new Date();
    now = now.getTime();
    var distance = enddatum.getTime() - now;
    if (distance < 0) {
        distance = startdatum.getTime() - now;
    }
    if (distance < 0) {
        var button = clock.getElementsByClassName('feedback-button')[0];
        button.classList.add('disabled');
        button.setAttribute('aria-disabled', true);
        button.innerText = 'Zeit abgelaufen!';

    } else {
        var days = Math.floor(distance / (1000 * 60 * 60 * 24));
        var hours = pad(Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)));
        var minutes = pad(Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60)));
        var seconds = pad(Math.floor((distance % (1000 * 60)) / 1000));
        clock.getElementsByClassName("clock_tage")[0].innerHTML = days;
        clock.getElementsByClassName("clock_stunden")[0].innerHTML = hours;
        clock.getElementsByClassName("clock_minuten")[0].innerHTML = minutes;
        clock.getElementsByClassName("clock_sekunden")[0].innerHTML = seconds;


        if (days == 0) {
            var dayspan = clock.getElementsByClassName('dayspan')[0];
            dayspan.style.visibility = "hidden";
            var daycount = clock.getElementsByClassName('clock_tage')[0];
            daycount.style.visibility = "hidden";
        }
        if (hours < 1) {
            var cardspan = clock.getElementsByClassName('card-body')[0];
            cardspan.style.backgroundColor = "#F08080";
        }

    }
}

var clocks = [...document.getElementsByClassName('card')];
var x = setInterval(function () {
    clocks.forEach(clocktick);
}, 1000);

var enddates = [...document.getElementsByClassName('enddate')];
enddates.forEach(formatDate);
