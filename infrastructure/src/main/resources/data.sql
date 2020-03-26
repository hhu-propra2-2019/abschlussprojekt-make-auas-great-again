insert into dozent(id, username, vorname, nachname, anrede)
values (1, "orga1", "Jens", "Bendisposto", "Herr"),
       (2, "orga2", "Christian", "Meter", "Herr"),
       (3, "orga3", "Chris", "Rutenkolk", "Herr");


insert into veranstaltung(id, name, semester)
values (1, "Professionelle Softwareentwicklung", "SoSe2020"),
       (2, "Softwareentwicklung im Team", "WS2019"),
       (3, "Programmierung", "WS2020"),
       (4, "Algorithmen & Datenstrukturen", "WS2020"),
       (5, "Rechnernetze", "SoSe2020"),
       (6, "Datenbanksysteme", "WS2019"),
       (7, "Rechnerarchitektur", "WS2018");

insert into dozentOrganisiertVeranstaltung(dozent, veranstaltung)
values (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7),
       (2, 1),
       (2, 3),
       (2, 5),
       (2, 7),
       (3, 2),
       (3, 3),
       (3, 4),
       (3, 6);

insert into student(id, username)
values (1, "studentin1"),
       (2, "studentin2"),
       (3, "studentin3"),
       (4, "studentin");

insert into studentBelegtVeranstaltung(student, veranstaltung)
values (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7),
       (2, 1),
       (2, 2),
       (2, 3),
       (2, 7),
       (3, 4),
       (3, 7),
       (3, 2),
       (3, 1),
       (4, 1);

insert into fragebogen(id, name, startzeit, endzeit, veranstaltung, einheit)
values (1, "Fragebogen zum Praktikum", "2020-01-01 12:00:00", "2020-05-01 12:00:00", 2, 'PRAKTIKUM');

insert into frage(id, oeffentlich, fragebogen, fragetext)
values (1, true, 1, "Was war gut?"),
       (2, true, 1, "Was war schlecht?"),
       (3, true, 1, "Warum?"),
       (4, true, 1, "Bitte ankreuzen wie gut es gefallen hat");

insert into antwort(id, frage, textantwort)
values (1, 1, "Vieles war gut"),
       (2, 1, "Manches"),
       (3, 1, "Gar nichts"),
       (4, 2, "Garnichts"),
       (5, 2, "Es hat mir alles gefallen"),
       (6, 3, "Weiß nicht"),
       (7, 3, "Warum nicht?");

insert into antwort(id, frage)
values (8, 4),
       (9, 4),
       (10, 4);

insert into auswahl(id, auswahltext, frage, antwort)
values (1, "Sehr gut", 4, 8),
       (2, "OK", 4, 9),
       (3, "Garnicht", 4, 10);

insert into studentBeantwortetFragebogen(student, fragebogen)
values (2, 1),
       (3, 1);

insert into fragebogentemplate(id, name, dozent)
    value (1, "Vorlage", 1);

insert into frage(id, oeffentlich, fragebogentemplate, fragetext)
values (5, false, 1, "Testfrage");





