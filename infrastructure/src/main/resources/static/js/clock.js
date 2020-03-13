function pad(num) {
    var s = "00" + num;
    return s.substr(s.length - 2);
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
        console.log("Clear interval??");
    } else {
        var days = Math.floor(distance / (1000 * 60 * 60 * 24));
        var hours = pad(Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)));
        var minutes = pad(Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60)));
        var seconds = pad(Math.floor((distance % (1000 * 60)) / 1000));
        clock.getElementsByClassName("clock_tage")[0].innerHTML = days;
        clock.getElementsByClassName("clock_stunden")[0].innerHTML = hours;
        clock.getElementsByClassName("clock_minuten")[0].innerHTML = minutes;
        clock.getElementsByClassName("clock_sekunden")[0].innerHTML = seconds;
    }
}

var clocks = [...document.getElementsByClassName("clock_times")
    ]
;
var x = setInterval(function () {
    clocks.forEach(clocktick);
}, 1000);
