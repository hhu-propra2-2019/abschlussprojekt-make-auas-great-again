insert into dozent(username, vorname, nachname)
values ("orga1", "Jens", "Bendisposto"),
       ("orga2", "Christian", "Meter"),
       ("orga3", "Chris", "Rutenkolk");


insert into veranstaltung(id, name, semester)
values (1, "Professionelle Softwareentwicklung", "SoSe2020"),
       (2, "Softwareentwicklung im Team", "WS2019"),
       (3, "Programmierung", "WS2020"),
       (4, "Algorithmen & Datenstrukturen", "WS2020"),
       (5, "Rechnernetze", "SoSe2020"),
       (6, "Datenbanksysteme", "WS2019"),
       (7, "Rechnerarchitektur", "WS2018");

insert into dozentOrganisiertVeranstaltung(dozent, veranstaltung)
values ("orga1", 1),
       ("orga1", 2),
       ("orga1", 3),
       ("orga1", 4),
       ("orga1", 5),
       ("orga1", 6),
       ("orga1", 7),
       ("orga2", 1),
       ("orga2", 3),
       ("orga2", 5),
       ("orga2", 7),
       ("orga3", 2),
       ("orga3", 3),
       ("orga3", 4),
       ("orga3", 6);

insert into student(username)
values ("studentin"),
       ("studentin2"),
       ("studentin3"),
       ("studentin1");

insert into studentBelegtVeranstaltung(student, veranstaltung)
values ("studentin", 1),
       ("studentin", 2),
       ("studentin", 3),
       ("studentin", 4),
       ("studentin", 5),
       ("studentin", 6),
       ("studentin", 7),
       ("studentin2", 1),
       ("studentin2", 2),
       ("studentin2", 3),
       ("studentin2", 7),
       ("studentin3", 4),
       ("studentin3", 7),
       ("studentin3", 2),
       ("studentin3", 1),
       ("studentin1", 1);

insert into fragebogen(id, name, startzeit, endzeit, veranstaltung, einheit)
values (1, "Fragebogen zum Praktikum", "2020-01-01 12:00:00.000", "2020-05-01 12:00:00.000", 2, 'PRAKTIKUM');

insert into frage(id, oeffentlich, fragetyp, fragebogen, fragetext)
values (1, true, 1, 1, "Was war gut?"),
       (2, true, 1, 1, "Was war schlecht?"),
       (3, true, 1, 1, "Warum?"),
       (4, true, 1, 1, "Bitte ankreuzen wie gut es gefallen hat");

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
values ("studentin2", 1),
       ("studentin3", 1);

insert into fragebogentemplate(id, name, dozent)
    value (1, "Vorlage", "orga1");

insert into frage(id, oeffentlich, fragetyp, fragebogentemplate, fragetext)
values (5, false, 1, 1, "Testfrage");
