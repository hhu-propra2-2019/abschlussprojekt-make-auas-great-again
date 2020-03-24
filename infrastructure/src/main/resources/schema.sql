create table if not exists dozent
(
    id       bigint unsigned not null auto_increment,
    vorname  varchar(50)     not null,
    nachname varchar(50)     not null,
    anrede   varchar(10)     not null,
    primary key (id)
);

create table if not exists veranstaltung
(
    id       bigint unsigned not null auto_increment,
    name     varchar(50)     not null,
    semester integer,
    primary key (id)
);

create table if not exists student
(
    id       bigint unsigned not null auto_increment,
    username varchar(50)     not null,
    vorname  varchar(50),
    nachname varchar(50),
    primary key (username)
);

create table if not exists fragebogen
(
    id            bigint unsigned not null auto_increment,
    name          varchar(100)    not null,
    status        integer         not null,
    startzeit     datetime,
    endzeit       datetime        not null,
    veranstaltung bigint unsigned not null,
    einheit       enum ('VORLESUNG','UEBUNG','AUFGABE','PRAKTIKUM','DOZENT','BERATUNG','GRUPPE'),
    primary key (id),
    foreign key (veranstaltung)
        references veranstaltung (id)
);

create table if not exists frage
(
    id         bigint unsigned not null auto_increment,
    fragebogen bigint unsigned not null,
    fragetext  varchar(100)    not null,
    primary key (id),
    foreign key (fragebogen)
        references fragebogen (id)
);

create table if not exists antwort
(
    id          bigint unsigned not null auto_increment,
    frage       bigint unsigned not null,
    textantwort text,
    primary key (id),
    foreign key (frage)
        references frage (id)
);

create table if not exists auswahl
(
    id          bigint unsigned not null auto_increment,
    auswahltext varchar(100)    not null,
    frage       bigint unsigned not null,
    antwort     bigint unsigned,
    primary key (id),
    foreign key (frage)
        references frage (id),
    foreign key (antwort)
        references antwort (id)
);

create table if not exists studentBeantwortetFragebogen
(
    student    varchar(50)     not null,
    fragebogen bigint unsigned not null,
    primary key (student, fragebogen),
    foreign key (student)
        references student (id),
    foreign key (fragebogen)
        references fragebogen (id)
);

create table if not exists studentBelegtVeranstaltung
(
    student       varchar(50)     not null,
    veranstaltung bigint unsigned not null,
    primary key (student, veranstaltung),
    foreign key (student)
        references student (id),
    foreign key (veranstaltung)
        references veranstaltung (id)
);

create table if not exists dozentOrganisiertVeranstaltung
(
    dozent        bigint unsigned not null,
    veranstaltung bigint unsigned not null,
    primary key (dozent, veranstaltung),
    foreign key (dozent)
        references dozent (id),
    foreign key (veranstaltung)
        references veranstaltung (id)
);
