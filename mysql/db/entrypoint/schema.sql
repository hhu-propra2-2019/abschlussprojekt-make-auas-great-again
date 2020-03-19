create table if not exists dozent
(
    id      bigint unsigned not null auto_increment,
    vorname varchar(50)     not null,
    name    varchar(50)     not null,
    anrede  varchar(10)     not null,
    primary key (id)
);

create table if not exists veranstaltung
(
    id         bigint unsigned not null auto_increment,
    name        varchar(50)     not null,
    sememster   integer,
    primary key (id),
    foreign key (dozent)
    references  dozent (id)
);

create table if not exists student
(
    username    varchar(50) not null auto_increment,
    vorname     varchar(50),
    nachname    varchar(50),
    primary key (id)
);

create table if not exists fragebogen
(
    id              bigint unsigned not null auto_increment,
    name            varchar(100)    not null,
    status          integer not null,
    startzeit       datetime,
    endzeit         datetime not null,
    veranstaltung   bigint unsigned not null,
    einheit         enum('VORLESUNG','UEBUNG','AUFGABE','PRAKTIKUM','DOZENT','BERATUNG','GRUPPE')
    primary key (id),
    foreign key(dozent)
    references dozent(id),
    foreign key (veranstaltung)
    references veranstaltung(id)
);

create table if not exists frage
(
    id         bigint unsigned not null auto_increment,
    titel      text            not null,
    fragebogen bigint unsigned not null,
    primary key (id),
    foreign key (fragebogen)
        references fragebogen (id)
);

create table if not exists textFrage
(
    id bigint unsigned not null auto_increment,
    primary key (id),
    foreign key (id)
        references frage (id)
);

create table if not exists multipleResponseFrage
(
    id bigint unsigned not null auto_increment,
    primary key (id),
    foreign key (id)
        references frage (id)
);

create table if not exists singleResponseFrage
(
    id bigint unsigned not null auto_increment,
    primary key (id),
    foreign key (id)
        references frage (id)
);

create table if not exists antwort
(
    id      bigint unsigned not null auto_increment,
    primary key (id)
);

create table if not exists textAntwort
(
    id      bigint unsigned not null auto_increment,
    antwort varchar(100),
    frage   bigint unsigned not null,
    primary key (id),
    foreign key (id)
    references antwort(id),
    foreign key (frage)
    references textFrage (id)
);

create table if not exists singleResponseAntwort
(
    id      bigint unsigned not null auto_increment,
    antwort bool,
    frage   bigint unsigned not null,
    primary key (id),
    foreign key (id)
    references antwort(id),
    foreign key (frage)
        references boolscheFrage (id)
);

create table if not exists multipleResponseAntwort
(
    id      bigint unsigned not null auto_increment,
    antwort integer,
    frage   bigint unsigned not null,
    primary key (id),
    foreign key (id)
    references antwort(id),
    foreign key (frage)
        references multipleFrage (id)
);

create table if not exists studentBeantwortetFragebogen
(
    student    bigint unsigned not null,
    fragebogen bigint unsigned not null,
    primary key (student, fragebogen),
    foreign key (student)
        references student (id),
    foreign key (fragebogen)
        references fragebogen (id)
);

create table if not exists studentBelegtVeranstaltung
(
    student       bigint unsigned not null,
    veranstaltung bigint unsigned not null,
    primary key (student, veranstaltung),
    foreign key (student)
        references student (id),
    foreign key (veranstaltung)
        references veranstaltung (id)
);

create table if not exists dozentOrganisiertVeranstaltung
(
    dozent bigint unsigned not null,
    veranstaltung bigint unsigned not null,
    primary key (dozent, veranstaltung),
    foreign key (dozent)
        references dozent (id),
    foreign key (veranstaltung)
        references veranstaltung (id)
);