drop table if exists dozent;
create table if not exists dozent
(
    username varchar(50) not null UNIQUE,
    vorname varchar(50) not null,
    nachname varchar(50) not null,
    primary key (username)
);

drop table if exists veranstaltung;
create table if not exists veranstaltung
(
    id bigint not null UNIQUE,
    name varchar(50) not null,
    semester varchar(50) not null,
    primary key (id)
);

drop table if exists student;
create table if not exists student
(
    username varchar(50) not null UNIQUE,
    vorname varchar(50),
    nachname varchar(50),
    primary key (username)
);

drop table if exists fragebogentemplate;
create table if not exists fragebogentemplate
(
    id bigint not null UNIQUE,
    name varchar(50) not null,
    dozent varchar(50) not null,
    primary key (id),
    foreign key (dozent) references dozent (username)
);

drop table if exists fragebogen;
create table if not exists fragebogen
(
    id bigint not null UNIQUE,
    name varchar(100) not null,
    startzeit varchar(100) not null,
    endzeit varchar(100) not null,
    veranstaltung bigint not null,
    einheit enum('VORLESUNG','UEBUNG','AUFGABE','PRAKTIKUM','DOZENT','BERATUNG','GRUPPE'),
    primary key (id),
    foreign key (veranstaltung) references veranstaltung (id)
);

drop table if exists frage;
create table if not exists frage
(
    id bigint not null UNIQUE,
    oeffentlich boolean not null,
    fragetyp bigint not null,
    fragebogen bigint,
    fragebogentemplate bigint,
    fragetext varchar(100) not null,
    primary key (id),
    foreign key (fragebogen) references fragebogen (id),
    foreign key (fragebogentemplate) references fragebogentemplate (id)
);

drop table if exists antwort;
create table if not exists antwort
(
    id bigint not null,
    frage bigint not null,
    textantwort text,
    primary key (id),
    foreign key (frage) references frage (id)
);

drop table if exists auswahl;
create table if not exists auswahl
(
    id bigint not null,
    auswahltext varchar(100) not null,
    frage bigint,
    antwort bigint,
    primary key (id),
    foreign key (frage) references frage (id),
    foreign key (antwort) references antwort (id)
);

drop table if exists studentBeantwortetFragebogen;
create table if not exists studentBeantwortetFragebogen
(
    student varchar(50) not null,
    fragebogen bigint not null,
    primary key (student, fragebogen),
    foreign key (student) references student (username),
    foreign key (fragebogen) references fragebogen (id)
);

drop table if exists studentBelegtVeranstaltung;
create table if not exists studentBelegtVeranstaltung
(
    student varchar(50) not null,
    veranstaltung bigint not null,
    primary key (student, veranstaltung),
    foreign key (student) references student (username),
    foreign key (veranstaltung) references veranstaltung (id)
);

drop table if exists dozentOrganisiertVeranstaltung;
create table if not exists dozentOrganisiertVeranstaltung
(
    dozent varchar(50) not null,
    veranstaltung bigint not null,
    primary key (dozent, veranstaltung),
    foreign key (dozent) references dozent (username),
    foreign key (veranstaltung) references veranstaltung (id)
);
